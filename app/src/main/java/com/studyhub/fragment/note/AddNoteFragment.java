package com.studyhub.fragment.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.studyhub.R;
import com.studyhub.database.entity.NoteEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.FragmentAddNoteBinding;
import com.studyhub.viewmodel.NoteViewModel;
import com.studyhub.viewmodel.SubjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddNoteFragment extends Fragment {

    private FragmentAddNoteBinding binding;
    private NoteViewModel noteViewModel;
    private SubjectViewModel subjectViewModel;
    private NavController navController;

    private boolean isEditMode = false;
    private String noteId = null;
    private NoteEntity currentNote = null;

    private List<SubjectEntity> subjectList = new ArrayList<>();
    private ArrayAdapter<String> subjectAdapter;
    private List<String> subjectNames = new ArrayList<>();

    private String[] noteTypes = {"TEXT", "CHECKLIST", "IMAGE"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean("is_edit_mode", false);
            noteId = getArguments().getString("note_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            navController = Navigation.findNavController(view);
        } catch (Exception e) {
            navController = null;
        }

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        setupToolbar();
        setupSpinners();
        observeViewModel();
        setupClickListeners();

        if (isEditMode && noteId != null) {
            loadNoteData();
        }
    }

    private void setupToolbar() {
        binding.toolbar.setTitle(isEditMode ? "Chỉnh sửa Ghi chú" : "Thêm Ghi chú");
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (navController != null) navController.navigateUp();
            else requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
    }

    private void setupSpinners() {
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, noteTypes);
        binding.spinnerType.setAdapter(typeAdapter);

        subjectAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, subjectNames);
        binding.spinnerSubject.setAdapter(subjectAdapter);
    }

    private void observeViewModel() {
        subjectViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            subjectList.clear();
            subjectNames.clear();
            
            // Add a default "No Subject" option
            subjectNames.add("Không chọn môn");
            
            if (subjects != null) {
                subjectList.addAll(subjects);
                for (SubjectEntity s : subjects) {
                    subjectNames.add(s.getName());
                }
            }
            subjectAdapter.notifyDataSetChanged();

            if (isEditMode && currentNote != null) {
                setSubjectSelection(currentNote.getSubjectId());
            }
        });
    }

    private void loadNoteData() {
        noteViewModel.getNoteById(noteId).observe(getViewLifecycleOwner(), noteEntity -> {
            if (noteEntity != null && currentNote == null) {
                currentNote = noteEntity;
                binding.etTitle.setText(noteEntity.getTitle());
                binding.etContent.setText(noteEntity.getContent());
                binding.switchFavorite.setChecked(noteEntity.isFavorite());

                for (int i = 0; i < noteTypes.length; i++) {
                    if (noteTypes[i].equals(noteEntity.getType())) {
                        binding.spinnerType.setSelection(i);
                        break;
                    }
                }

                setSubjectSelection(noteEntity.getSubjectId());
            }
        });
    }

    private void setSubjectSelection(String subjectId) {
        if (subjectId != null && !subjectList.isEmpty()) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectId.equals(subjectList.get(i).getId())) {
                    binding.spinnerSubject.setSelection(i + 1); // +1 because index 0 is "No Subject"
                    break;
                }
            }
        }
    }

    private void setupClickListeners() {
        binding.btnSave.setOnClickListener(v -> saveNote());
    }

    private void saveNote() {
        String title = binding.etTitle.getText().toString().trim();
        String content = binding.etContent.getText().toString().trim();
        String type = noteTypes[binding.spinnerType.getSelectedItemPosition()];
        boolean isFavorite = binding.switchFavorite.isChecked();

        String subjectId = null;
        int subjectPos = binding.spinnerSubject.getSelectedItemPosition();
        if (subjectPos > 0 && subjectPos - 1 < subjectList.size()) {
            subjectId = subjectList.get(subjectPos - 1).getId();
        }

        if (title.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditMode && currentNote != null) {
            currentNote.setTitle(title);
            currentNote.setContent(content);
            currentNote.setType(type);
            currentNote.setSubjectId(subjectId);
            currentNote.setFavorite(isFavorite);
            noteViewModel.update(currentNote);
        } else {
            NoteEntity newNote = new NoteEntity();
            newNote.setTitle(title);
            newNote.setContent(content);
            newNote.setType(type);
            newNote.setSubjectId(subjectId);
            newNote.setFavorite(isFavorite);
            noteViewModel.insert(newNote);
        }

        Toast.makeText(requireContext(), "Đã lưu Ghi chú", Toast.LENGTH_SHORT).show();
        if (navController != null) {
            navController.navigateUp();
        } else {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
