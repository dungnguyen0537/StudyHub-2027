package com.studyhub.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.studyhub.activity.SplashActivity;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.FragmentProfileBinding;
import com.studyhub.utils.PreferenceManager;
import com.studyhub.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel profileViewModel;
    private PreferenceManager preferenceManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        preferenceManager = new PreferenceManager(requireContext());

        setupDarkModeSwitch();
        setupClickListeners();
        observeViewModel();
    }

    private void setupDarkModeSwitch() {
        boolean isDarkMode = preferenceManager.getBoolean(AppConstants.KEY_DARK_MODE, false);
        binding.switchDarkMode.setChecked(isDarkMode);

        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferenceManager.putBoolean(AppConstants.KEY_DARK_MODE, isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    private void setupClickListeners() {
        binding.llLogout.setOnClickListener(v -> showLogoutDialog());
        
        binding.llEditProfile.setOnClickListener(v -> {
            androidx.navigation.NavController navController = androidx.navigation.Navigation.findNavController(v);
            navController.navigate(com.studyhub.R.id.action_profileFragment_to_editProfileFragment);
        });
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Đăng xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất khỏi tài khoản?")
                .setPositiveButton("Đăng xuất", (dialog, which) -> {
                    profileViewModel.logout();
                    
                    // Restart app via SplashActivity to route to Login
                    Intent intent = new Intent(requireContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void observeViewModel() {
        profileViewModel.getUserProfile().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.tvFullName.setText(user.getFullName());
                binding.tvEmail.setText(user.getEmail());
                
                // If you had Glide, you would load the avatar here
                // Glide.with(this).load(user.getAvatarUrl()).into(binding.ivAvatar);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
