package com.studyhub.fragment.subject;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.studyhub.R;
import com.studyhub.constant.AppConstants;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.FragmentAddSubjectBinding;
import com.studyhub.utils.ValidationUtils;
import com.studyhub.viewmodel.SubjectViewModel;

public class AddSubjectFragment extends Fragment {

    private FragmentAddSubjectBinding binding;
    private SubjectViewModel subjectViewModel;
    private NavController navController;
    
    private boolean isEditMode = false;
    private String subjectId;
    private SubjectEntity currentSubject;
    private boolean isDataLoaded = false;
    private String selectedColorHex = "#2196F3"; // Default blue
    
    // Sample material colors for subjects
    private final String[] COLOR_PALETTE = {
        "#4CAF50", "#2196F3", "#9C27B0", "#FF9800", "#E91E63", 
        "#00BCD4", "#795548", "#607D8B", "#FF5722", "#3F51B5"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddSubjectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        checkEditMode();
        setupColorPicker();
        setupClickListeners();
    }

    private void checkEditMode() {
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
            subjectId = getArguments().getString(AppConstants.KEY_SUBJECT_ID);
            
            if (isEditMode && subjectId != null) {
                binding.toolbar.setTitle(R.string.edit_subject);
                binding.btnDelete.setVisibility(View.VISIBLE);
                
                // Load subject data
                subjectViewModel.getSubjectById(subjectId).observe(getViewLifecycleOwner(), subject -> {
                    if (subject != null) {
                        currentSubject = subject;
                        if (!isDataLoaded) {
                            populateData(subject);
                            isDataLoaded = true;
                        }
                    }
                });
            }
        }
    }

    private void populateData(SubjectEntity subject) {
        binding.etSubjectName.setText(subject.getName());
        binding.etSubjectCode.setText(subject.getCode());
        binding.etCredits.setText(String.valueOf(subject.getCredits()));
        binding.etRoom.setText(subject.getRoom());
        binding.etTeacher.setText(subject.getTeacher());
        binding.etNote.setText(subject.getNote());
        selectedColorHex = subject.getColorHex();
        // Redraw color picker to show selection
        setupColorPicker();
    }

    private void setupColorPicker() {
        binding.llColorPicker.removeAllViews();
        int size = getResources().getDimensionPixelSize(R.dimen.avatar_sm);
        int margin = getResources().getDimensionPixelSize(R.dimen.spacing_xs);

        for (String colorHex : COLOR_PALETTE) {
            CardView cardView = new CardView(requireContext());
            
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(size, size);
            params.setMargins(margin, margin, margin, margin);
            cardView.setLayoutParams(params);
            
            cardView.setRadius(size / 2f);
            cardView.setCardBackgroundColor(Color.parseColor(colorHex));
            cardView.setCardElevation(colorHex.equals(selectedColorHex) ? 8f : 2f);
            
            // Add a border or scale effect for selected color
            if (colorHex.equals(selectedColorHex)) {
                cardView.setScaleX(1.1f);
                cardView.setScaleY(1.1f);
            }

            cardView.setOnClickListener(v -> {
                selectedColorHex = colorHex;
                setupColorPicker(); // Refresh view
            });

            binding.llColorPicker.addView(cardView);
        }
    }

    private void setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener(v -> navController.navigateUp());

        binding.btnSave.setOnClickListener(v -> saveSubject());

        binding.btnDelete.setOnClickListener(v -> showDeleteConfirmDialog());
    }

    private void saveSubject() {
        String name = binding.etSubjectName.getText() != null ? binding.etSubjectName.getText().toString().trim() : "";
        String code = binding.etSubjectCode.getText() != null ? binding.etSubjectCode.getText().toString().trim() : "";
        String creditsStr = binding.etCredits.getText() != null ? binding.etCredits.getText().toString().trim() : "0";
        String room = binding.etRoom.getText() != null ? binding.etRoom.getText().toString().trim() : "";
        String teacher = binding.etTeacher.getText() != null ? binding.etTeacher.getText().toString().trim() : "";
        String note = binding.etNote.getText() != null ? binding.etNote.getText().toString().trim() : "";

        if (!ValidationUtils.isNotEmpty(name)) {
            binding.tilSubjectName.setError(getString(R.string.error_empty_subject_name));
            return;
        }
        binding.tilSubjectName.setError(null);

        int credits = 0;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (NumberFormatException ignored) {}

        if (isEditMode && currentSubject != null) {
            currentSubject.setName(name);
            currentSubject.setCode(code);
            currentSubject.setCredits(credits);
            currentSubject.setRoom(room);
            currentSubject.setTeacher(teacher);
            currentSubject.setNote(note);
            currentSubject.setColorHex(selectedColorHex);
            subjectViewModel.update(currentSubject);
        } else {
            SubjectEntity newSubject = new SubjectEntity();
            newSubject.setName(name);
            newSubject.setCode(code);
            newSubject.setCredits(credits);
            newSubject.setRoom(room);
            newSubject.setTeacher(teacher);
            newSubject.setNote(note);
            newSubject.setColorHex(selectedColorHex);
            newSubject.setFavorite(false);
            subjectViewModel.insert(newSubject);
        }

        Toast.makeText(requireContext(), R.string.saved_success, Toast.LENGTH_SHORT).show();
        navController.navigateUp();
    }

    private void showDeleteConfirmDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_message)
                .setPositiveButton(R.string.delete, (dialog, which) -> {
                    if (currentSubject != null) {
                        subjectViewModel.delete(currentSubject);
                        Toast.makeText(requireContext(), R.string.item_deleted, Toast.LENGTH_SHORT).show();
                        navController.navigateUp();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
