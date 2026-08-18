package com.studyhub.fragment.deadline;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.studyhub.constant.AppConstants;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.FragmentAddDeadlineBinding;
import com.studyhub.viewmodel.DeadlineViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddDeadlineFragment extends Fragment {

    private FragmentAddDeadlineBinding binding;
    private DeadlineViewModel viewModel;
    private NavController navController;

    private boolean isEditMode = false;
    private String deadlineId = null;

    private DeadlineEntity currentDeadline;

    private List<SubjectEntity> subjectList = new ArrayList<>();
    private String selectedSubjectId = null;

    private Calendar dueCalendar = Calendar.getInstance();

    private final String[] priorities = {"Thấp", "Trung bình", "Cao"};
    private final String[] statuses = {"Đang chờ", "Hoàn thành", "Quá hạn"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
            deadlineId = getArguments().getString(AppConstants.KEY_DEADLINE_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddDeadlineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        viewModel = new ViewModelProvider(this).get(DeadlineViewModel.class);

        setupSpinners();
        setupClickListeners();

        if (isEditMode && deadlineId != null) {
            binding.toolbar.setTitle("Chỉnh sửa Deadline");
            binding.btnDelete.setVisibility(View.VISIBLE);
            loadDeadlineData();
        } else {
            binding.toolbar.setTitle("Thêm Deadline");
            binding.btnDelete.setVisibility(View.GONE);
            updateDateTimeText();
        }

        viewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null) {
                subjectList = subjects;
                List<String> subjectNames = new ArrayList<>();
                for (SubjectEntity s : subjects) {
                    subjectNames.add(s.getName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, subjectNames);
                binding.spinnerSubject.setAdapter(adapter);

                // If editing, re-select the subject once loaded
                if (isEditMode && selectedSubjectId != null) {
                    for (int i = 0; i < subjectList.size(); i++) {
                        if (subjectList.get(i).getId().equals(selectedSubjectId)) {
                            binding.spinnerSubject.setText(subjectList.get(i).getName(), false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setupSpinners() {
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, priorities);
        binding.spinnerPriority.setAdapter(priorityAdapter);
        binding.spinnerPriority.setText(priorities[1], false); // Default Medium

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, statuses);
        binding.spinnerStatus.setAdapter(statusAdapter);
        binding.spinnerStatus.setText(statuses[0], false); // Default Pending

        binding.spinnerSubject.setOnItemClickListener((parent, view, position, id) -> {
            selectedSubjectId = subjectList.get(position).getId();
        });
    }

    private void setupClickListeners() {
        binding.toolbar.setNavigationOnClickListener(v -> navController.navigateUp());

        binding.btnPickDate.setOnClickListener(v -> showDatePicker());
        binding.btnPickTime.setOnClickListener(v -> showTimePicker());

        binding.btnSave.setOnClickListener(v -> saveDeadline());

        binding.btnDelete.setOnClickListener(v -> {
            if (currentDeadline != null) {
                viewModel.delete(currentDeadline);
                navController.navigateUp();
            }
        });
    }

    private void showDatePicker() {
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            dueCalendar.set(Calendar.YEAR, year);
            dueCalendar.set(Calendar.MONTH, month);
            dueCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTimeText();
        }, dueCalendar.get(Calendar.YEAR), dueCalendar.get(Calendar.MONTH), dueCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            dueCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dueCalendar.set(Calendar.MINUTE, minute);
            dueCalendar.set(Calendar.SECOND, 0);
            updateDateTimeText();
        }, dueCalendar.get(Calendar.HOUR_OF_DAY), dueCalendar.get(Calendar.MINUTE), true).show();
    }

    private void updateDateTimeText() {
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.DATETIME_FORMAT, Locale.getDefault());
        binding.tvSelectedDateTime.setText(sdf.format(dueCalendar.getTime()));
    }

    private void loadDeadlineData() {
        viewModel.getAllDeadlines().observe(getViewLifecycleOwner(), deadlines -> {
            if (deadlines != null) {
                for (DeadlineEntity d : deadlines) {
                    if (d.getId().equals(deadlineId)) {
                        currentDeadline = d;
                        populateFields();
                        break;
                    }
                }
            }
        });
    }

    private void populateFields() {
        if (currentDeadline == null) return;

        binding.etTitle.setText(currentDeadline.getTitle());
        binding.etDescription.setText(currentDeadline.getDescription());

        dueCalendar.setTimeInMillis(currentDeadline.getDueDate());
        updateDateTimeText();

        int priority = currentDeadline.getPriority();
        if (priority >= 0 && priority < priorities.length) {
            binding.spinnerPriority.setText(priorities[priority], false);
        }

        String status = currentDeadline.getStatus();
        if ("COMPLETED".equals(status)) {
            binding.spinnerStatus.setText(statuses[1], false);
        } else if ("OVERDUE".equals(status)) {
            binding.spinnerStatus.setText(statuses[2], false);
        } else {
            binding.spinnerStatus.setText(statuses[0], false);
        }

        selectedSubjectId = currentDeadline.getSubjectId();
        if (selectedSubjectId != null && !subjectList.isEmpty()) {
            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getId().equals(selectedSubjectId)) {
                    binding.spinnerSubject.setText(subjectList.get(i).getName(), false);
                    break;
                }
            }
        }
    }

    private void saveDeadline() {
        String title = binding.etTitle.getText() != null ? binding.etTitle.getText().toString().trim() : "";
        String description = binding.etDescription.getText() != null ? binding.etDescription.getText().toString().trim() : "";

        if (TextUtils.isEmpty(title)) {
            binding.tilTitle.setError("Vui lòng nhập tiêu đề");
            return;
        }
        binding.tilTitle.setError(null);

        if (selectedSubjectId == null) {
            binding.tilSubject.setError("Vui lòng chọn môn học");
            return;
        }
        binding.tilSubject.setError(null);

        int priority = 1; // Medium default
        String priorityText = binding.spinnerPriority.getText().toString();
        for (int i = 0; i < priorities.length; i++) {
            if (priorities[i].equals(priorityText)) {
                priority = i;
                break;
            }
        }

        String dbStatus = "PENDING";
        String statusText = binding.spinnerStatus.getText().toString();
        if (statuses[1].equals(statusText)) {
            dbStatus = "COMPLETED";
        } else if (statuses[2].equals(statusText)) {
            dbStatus = "OVERDUE";
        }

        if (isEditMode && currentDeadline != null) {
            currentDeadline.setTitle(title);
            currentDeadline.setDescription(description);
            currentDeadline.setSubjectId(selectedSubjectId);
            currentDeadline.setPriority(priority);
            currentDeadline.setStatus(dbStatus);
            currentDeadline.setDueDate(dueCalendar.getTimeInMillis());

            viewModel.update(currentDeadline);
            Toast.makeText(requireContext(), "Đã cập nhật Deadline", Toast.LENGTH_SHORT).show();
        } else {
            DeadlineEntity newDeadline = new DeadlineEntity();
            newDeadline.setTitle(title);
            newDeadline.setDescription(description);
            newDeadline.setSubjectId(selectedSubjectId);
            newDeadline.setPriority(priority);
            newDeadline.setStatus(dbStatus);
            newDeadline.setDueDate(dueCalendar.getTimeInMillis());

            viewModel.insert(newDeadline);
            Toast.makeText(requireContext(), "Đã tạo Deadline", Toast.LENGTH_SHORT).show();
        }

        navController.navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
