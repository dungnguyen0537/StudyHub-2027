package com.studyhub.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.studyhub.R;
import com.studyhub.database.entity.TaskEntity;
import com.studyhub.viewmodel.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTaskFragment extends Fragment {

    private TextInputEditText editTitle, editCategory, editDeadline;
    private RadioGroup radioGroupPriority;
    private MaterialSwitch switchCompleted;
    private MaterialButton buttonSave;

    private TaskViewModel taskViewModel;

    private boolean isEditMode = false;
    private String taskId = null;
    private TaskEntity currentTask;

    private long selectedDeadline = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public AddTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        editTitle = view.findViewById(R.id.edit_title);
        editCategory = view.findViewById(R.id.edit_category);
        editDeadline = view.findViewById(R.id.edit_deadline);
        radioGroupPriority = view.findViewById(R.id.radio_group_priority);
        switchCompleted = view.findViewById(R.id.switch_completed);
        buttonSave = view.findViewById(R.id.button_save);

        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean("is_edit_mode", false);
            taskId = getArguments().getString("task_id", null);
        }

        editDeadline.setOnClickListener(v -> showDateTimePicker());

        com.google.android.material.appbar.MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> Navigation.findNavController(view).navigateUp());

        if (isEditMode && taskId != null) {
            toolbar.setTitle("Sửa công việc");
            buttonSave.setText("Cập nhật công việc");
            loadTaskData();
            
            // Inflate delete menu
            toolbar.inflateMenu(R.menu.menu_add_task);
            toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_delete) {
                    new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Xóa công việc")
                            .setMessage("Bạn có chắc chắn muốn xóa công việc này?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                if (currentTask != null) {
                                    taskViewModel.delete(currentTask);
                                    Toast.makeText(requireContext(), "Đã xóa công việc", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(requireView()).navigateUp();
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                    return true;
                }
                return false;
            });
        }

        buttonSave.setOnClickListener(v -> saveTask());
    }

    private void loadTaskData() {
        taskViewModel.getTaskById(taskId).observe(getViewLifecycleOwner(), taskEntity -> {
            if (taskEntity != null) {
                currentTask = taskEntity;
                editTitle.setText(taskEntity.getTitle());
                editCategory.setText(taskEntity.getCategory());
                switchCompleted.setChecked(taskEntity.isCompleted());
                
                selectedDeadline = taskEntity.getDeadline();
                if (selectedDeadline > 0) {
                    editDeadline.setText(dateFormat.format(new Date(selectedDeadline)));
                }

                int priority = taskEntity.getPriority();
                if (priority == 1) {
                    radioGroupPriority.check(R.id.radio_high);
                } else if (priority == 3) {
                    radioGroupPriority.check(R.id.radio_low);
                } else {
                    radioGroupPriority.check(R.id.radio_medium);
                }
            }
        });
    }

    private void showDateTimePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Chọn ngày hết hạn")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Chọn giờ hết hạn")
                    .build();

            timePicker.addOnPositiveButtonClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                
                selectedDeadline = calendar.getTimeInMillis();
                editDeadline.setText(dateFormat.format(new Date(selectedDeadline)));
            });

            timePicker.show(getParentFragmentManager(), "TIME_PICKER");
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    private void saveTask() {
        String title = editTitle.getText() != null ? editTitle.getText().toString().trim() : "";
        String category = editCategory.getText() != null ? editCategory.getText().toString().trim() : "";
        
        if (TextUtils.isEmpty(title)) {
            editTitle.setError("Tiêu đề không được để trống");
            return;
        }

        int priority = 2; // Default Medium
        int checkedId = radioGroupPriority.getCheckedRadioButtonId();
        if (checkedId == R.id.radio_high) {
            priority = 1;
        } else if (checkedId == R.id.radio_low) {
            priority = 3;
        }

        boolean isCompleted = switchCompleted.isChecked();

        if (isEditMode && currentTask != null) {
            currentTask.setTitle(title);
            currentTask.setCategory(category);
            currentTask.setPriority(priority);
            currentTask.setDeadline(selectedDeadline);
            currentTask.setCompleted(isCompleted);
            currentTask.setNotificationEnabled(true);
            taskViewModel.update(currentTask);
            Toast.makeText(getContext(), "Đã cập nhật công việc", Toast.LENGTH_SHORT).show();
        } else {
            TaskEntity newTask = new TaskEntity();
            newTask.setTitle(title);
            newTask.setCategory(category);
            newTask.setPriority(priority);
            newTask.setDeadline(selectedDeadline);
            newTask.setCompleted(isCompleted);
            newTask.setNotificationEnabled(true);
            taskViewModel.insert(newTask);
            Toast.makeText(getContext(), "Đã thêm công việc thành công", Toast.LENGTH_SHORT).show();
        }

        Navigation.findNavController(requireView()).navigateUp();
    }
}
