package com.studyhub.fragment.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.studyhub.R;
import com.studyhub.constant.AppConstants;
import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.FragmentAddScheduleBinding;
import com.studyhub.viewmodel.ScheduleViewModel;
import com.studyhub.viewmodel.SubjectViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AddScheduleFragment extends Fragment {

    private FragmentAddScheduleBinding binding;
    private ScheduleViewModel scheduleViewModel;
    private SubjectViewModel subjectViewModel;
    private NavController navController;

    private boolean isEditMode = false;
    private String scheduleId;
    private ScheduleEntity currentSchedule;

    private List<SubjectEntity> subjectList = new ArrayList<>();
    private String selectedSubjectId = null;

    private static class DaySchedule {
        int dayOfWeek; // 2=Mon, 3=Tue, ..., 8=Sun
        boolean isEnabled;
        String startTime = "07:00";
        String endTime = "09:00";
        String room = "";
        
        // UI References
        View itemView;
        MaterialSwitch switchEnable;
        LinearLayout llDetails;
        MaterialButton btnStartTime;
        MaterialButton btnEndTime;
        TextInputEditText etRoom;
    }
    
    private List<DaySchedule> daySchedules = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEditMode = getArguments().getBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
            scheduleId = getArguments().getString(AppConstants.KEY_SCHEDULE_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> navController.navigateUp());
        
        setupDaysUI();
        observeSubjects();
        
        binding.btnSave.setOnClickListener(v -> saveSchedules());

        if (isEditMode && scheduleId != null) {
            loadScheduleData();
            binding.toolbar.setTitle("Sửa Lịch Học");
            binding.btnSave.setText("Lưu Lịch Học");
            
            // Inflate delete menu
            binding.toolbar.inflateMenu(R.menu.menu_add_schedule);
            binding.toolbar.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_delete) {
                    new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Xóa lịch học")
                            .setMessage("Bạn có chắc chắn muốn xóa lịch học này?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                if (currentSchedule != null) {
                                    scheduleViewModel.delete(currentSchedule);
                                    Toast.makeText(requireContext(), "Đã xóa lịch học", Toast.LENGTH_SHORT).show();
                                    navController.navigateUp();
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                    return true;
                }
                return false;
            });
        }
    }

    private void setupDaysUI() {
        String[] days = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        
        for (int i = 0; i < 7; i++) {
            DaySchedule ds = new DaySchedule();
            ds.dayOfWeek = i + 2;
            
            View itemView = inflater.inflate(R.layout.item_day_schedule, binding.llDaysContainer, false);
            TextView tvDayName = itemView.findViewById(R.id.tvDayName);
            ds.switchEnable = itemView.findViewById(R.id.switchEnable);
            ds.llDetails = itemView.findViewById(R.id.llDetails);
            ds.btnStartTime = itemView.findViewById(R.id.btnStartTime);
            ds.btnEndTime = itemView.findViewById(R.id.btnEndTime);
            ds.etRoom = itemView.findViewById(R.id.etRoom);
            ds.itemView = itemView;
            
            tvDayName.setText(days[i]);
            
            ds.switchEnable.setOnCheckedChangeListener((buttonView, isChecked) -> {
                ds.isEnabled = isChecked;
                ds.llDetails.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            });
            
            ds.btnStartTime.setOnClickListener(v -> showTimePicker(ds, true));
            ds.btnEndTime.setOnClickListener(v -> showTimePicker(ds, false));
            
            binding.llDaysContainer.addView(itemView);
            daySchedules.add(ds);
            
            // If in edit mode, we only want to show the specific day being edited
            if (isEditMode) {
                itemView.setVisibility(View.GONE);
            }
        }
    }

    private void observeSubjects() {
        subjectViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            subjectList = subjects;
            List<String> subjectNames = new ArrayList<>();
            for (SubjectEntity subject : subjects) {
                subjectNames.add(subject.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    subjectNames
            );
            binding.spinnerSubject.setAdapter(adapter);

            binding.spinnerSubject.setOnItemClickListener((parent, view, position, id) -> {
                selectedSubjectId = subjectList.get(position).getId();
            });

            if (isEditMode && currentSchedule != null) {
                setSubjectSelection(currentSchedule.getSubjectId());
            }
        });
    }

    private void showTimePicker(DaySchedule ds, boolean isStart) {
        String timeToParse = isStart ? ds.startTime : ds.endTime;
        int hour = 7;
        int minute = 0;
        try {
            String[] parts = timeToParse.split(":");
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .setTitleText(isStart ? "Chọn Giờ Bắt Đầu" : "Chọn Giờ Kết Thúc")
                .build();

        timePicker.addOnPositiveButtonClickListener(v -> {
            String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", timePicker.getHour(), timePicker.getMinute());
            if (isStart) {
                ds.startTime = formattedTime;
                ds.btnStartTime.setText("Bắt đầu: " + formattedTime);
            } else {
                ds.endTime = formattedTime;
                ds.btnEndTime.setText("Kết thúc: " + formattedTime);
            }
        });

        timePicker.show(getChildFragmentManager(), "TIME_PICKER");
    }

    private void loadScheduleData() {
        scheduleViewModel.getAllSchedules().observe(getViewLifecycleOwner(), schedules -> {
            if (currentSchedule != null) return;
            for (ScheduleEntity schedule : schedules) {
                if (schedule.getId().equals(scheduleId)) {
                    currentSchedule = schedule;
                    populateUI();
                    break;
                }
            }
        });
    }

    private void populateUI() {
        if (currentSchedule == null) return;

        setSubjectSelection(currentSchedule.getSubjectId());
        
        int dayIndex = currentSchedule.getDayOfWeek() - 2;
        if (dayIndex >= 0 && dayIndex < 7) {
            DaySchedule ds = daySchedules.get(dayIndex);
            ds.itemView.setVisibility(View.VISIBLE);
            ds.switchEnable.setChecked(true);
            ds.startTime = currentSchedule.getStartTime();
            ds.endTime = currentSchedule.getEndTime();
            ds.btnStartTime.setText("Bắt đầu: " + ds.startTime);
            ds.btnEndTime.setText("Kết thúc: " + ds.endTime);
            ds.etRoom.setText(currentSchedule.getRoom());
        }
    }

    private void setSubjectSelection(String subjectId) {
        selectedSubjectId = subjectId;
        for (int i = 0; i < subjectList.size(); i++) {
            if (subjectList.get(i).getId().equals(subjectId)) {
                binding.spinnerSubject.setText(subjectList.get(i).getName(), false);
                break;
            }
        }
    }

    private void saveSchedules() {
        if (selectedSubjectId == null) {
            Toast.makeText(requireContext(), "Vui lòng chọn môn học", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean hasAnySchedule = false;
        int firstSavedDay = -1;

        if (isEditMode && currentSchedule != null) {
            int dayIndex = currentSchedule.getDayOfWeek() - 2;
            DaySchedule ds = daySchedules.get(dayIndex);
            if (ds.isEnabled) {
                currentSchedule.setSubjectId(selectedSubjectId);
                currentSchedule.setStartTime(ds.startTime);
                currentSchedule.setEndTime(ds.endTime);
                currentSchedule.setRoom(ds.etRoom.getText() != null ? ds.etRoom.getText().toString().trim() : "");
                currentSchedule.setReminderEnabled(true);
                currentSchedule.setReminderMinutesBefore(30);
                currentSchedule.setUpdatedAt(System.currentTimeMillis());
                scheduleViewModel.update(currentSchedule);
                hasAnySchedule = true;
                firstSavedDay = ds.dayOfWeek;
            }
        } else {
            String userId = "user1"; 
            try {
                com.google.firebase.auth.FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    userId = user.getUid();
                }
            } catch (Exception ignored) {}

            for (DaySchedule ds : daySchedules) {
                if (ds.isEnabled) {
                    ScheduleEntity schedule = new ScheduleEntity();
                    schedule.setId(UUID.randomUUID().toString());
                    schedule.setUserId(userId); 
                    schedule.setSubjectId(selectedSubjectId);
                    schedule.setDayOfWeek(ds.dayOfWeek);
                    schedule.setStartTime(ds.startTime);
                    schedule.setEndTime(ds.endTime);
                    schedule.setRoom(ds.etRoom.getText() != null ? ds.etRoom.getText().toString().trim() : "");
                    schedule.setReminderEnabled(true);
                    schedule.setReminderMinutesBefore(30);
                    schedule.setCreatedAt(System.currentTimeMillis());
                    schedule.setUpdatedAt(System.currentTimeMillis());
                    schedule.setSyncStatus(0);
                    
                    scheduleViewModel.insert(schedule);
                    hasAnySchedule = true;
                    if (firstSavedDay == -1) {
                        firstSavedDay = ds.dayOfWeek;
                    }
                }
            }
        }

        if (!hasAnySchedule && !isEditMode) {
            Toast.makeText(requireContext(), "Vui lòng bật lịch cho ít nhất 1 ngày", Toast.LENGTH_SHORT).show();
            return;
        }

        if (firstSavedDay != -1) {
            scheduleViewModel.setSelectedDay(firstSavedDay);
        }

        Toast.makeText(requireContext(), isEditMode ? "Đã cập nhật lịch học" : "Đã thêm lịch học thành công", Toast.LENGTH_SHORT).show();
        navController.navigateUp();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
