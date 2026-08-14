package com.studyhub.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.studyhub.R;
import com.studyhub.database.entity.UserEntity;
import com.studyhub.viewmodel.ProfileViewModel;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private NavController navController;

    private CircleImageView ivAvatar;
    private TextInputEditText etFullName;
    private TextInputEditText etEmail;
    private TextInputEditText etStudentId;
    private TextInputEditText etPhone;
    private TextInputEditText etAddress;
    private MaterialButton btnSave;
    
    private Uri selectedImageUri = null;
    private UserEntity currentUser = null;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        Glide.with(this).load(selectedImageUri).into(ivAvatar);
                    }
                }
            }
    );

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        navController = Navigation.findNavController(view);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> navController.navigateUp());

        ivAvatar = view.findViewById(R.id.ivAvatar);
        etFullName = view.findViewById(R.id.etFullName);
        etEmail = view.findViewById(R.id.etEmail);
        etStudentId = view.findViewById(R.id.etStudentId);
        etPhone = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        btnSave = view.findViewById(R.id.btnSave);

        ivAvatar.setOnClickListener(v -> openImagePicker());

        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                currentUser = user;
                etFullName.setText(user.getFullName());
                etEmail.setText(user.getEmail());
                etStudentId.setText(user.getStudentId());
                etPhone.setText(user.getPhone());
                etAddress.setText(user.getAddress());
                
                if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                    Glide.with(this).load(user.getAvatarUrl()).into(ivAvatar);
                }
            }
        });

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void saveProfile() {
        if (currentUser == null) return;
        
        String newFullName = etFullName.getText() != null ? etFullName.getText().toString().trim() : "";
        String newStudentId = etStudentId.getText() != null ? etStudentId.getText().toString().trim() : "";
        String newPhone = etPhone.getText() != null ? etPhone.getText().toString().trim() : "";
        String newAddress = etAddress.getText() != null ? etAddress.getText().toString().trim() : "";

        if (newFullName.isEmpty()) {
            etFullName.setError("Vui lòng nhập họ tên");
            return;
        }

        btnSave.setEnabled(false);
        btnSave.setText("Đang lưu...");

        if (selectedImageUri != null) {
            uploadImageToFirebase(newFullName, newStudentId, newPhone, newAddress);
        } else {
            updateUserDatabase(newFullName, newStudentId, newPhone, newAddress, currentUser.getAvatarUrl());
        }
    }

    private void uploadImageToFirebase(String fullName, String studentId, String phone, String address) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("avatars/" + fileName);
        
        storageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    updateUserDatabase(fullName, studentId, phone, address, uri.toString());
                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Lỗi tải ảnh lên: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                    btnSave.setText("Lưu thay đổi");
                });
    }

    private void updateUserDatabase(String fullName, String studentId, String phone, String address, String avatarUrl) {
        currentUser.setFullName(fullName);
        currentUser.setStudentId(studentId);
        currentUser.setPhone(phone);
        currentUser.setAddress(address);
        if (avatarUrl != null) {
            currentUser.setAvatarUrl(avatarUrl);
        }
        
        profileViewModel.updateUserProfile(currentUser);
        Toast.makeText(requireContext(), "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT).show();
        navController.navigateUp();
    }
}
