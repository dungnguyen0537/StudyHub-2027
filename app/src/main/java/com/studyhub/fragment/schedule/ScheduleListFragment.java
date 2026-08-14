package com.studyhub.fragment.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.chip.Chip;
import com.studyhub.R;
import com.studyhub.adapter.ScheduleAdapter;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.FragmentScheduleBinding;
import com.studyhub.viewmodel.ScheduleViewModel;

import java.util.Calendar;

public class ScheduleListFragment extends Fragment {

    private FragmentScheduleBinding binding;
    private ScheduleViewModel scheduleViewModel;
    private ScheduleAdapter adapter;
    private NavController navController;
    private int currentDayOfWeek;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentScheduleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        scheduleViewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);

        setupRecyclerView();
        setupDayPicker();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new ScheduleAdapter(schedule -> {
            Bundle bundle = new Bundle();
            bundle.putString(AppConstants.KEY_SCHEDULE_ID, schedule.getId());
            bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, true);
            navController.navigate(R.id.addScheduleFragment, bundle);
        });
        binding.rvSchedules.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSchedules.setAdapter(adapter);

    }

    private void setupDayPicker() {
        if (scheduleViewModel.getSelectedDayValue() != null) {
            currentDayOfWeek = scheduleViewModel.getSelectedDayValue();
        } else {
            // Default to current day
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            // Map Calendar day (1=Sun, 2=Mon...) to standard (2=Mon, ..., 8=Sun)
            currentDayOfWeek = (day == Calendar.SUNDAY) ? 8 : day;
        }

        checkChipForDay(currentDayOfWeek);

        binding.chipGroupDays.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                if (checkedId == R.id.chipMon) currentDayOfWeek = 2;
                else if (checkedId == R.id.chipTue) currentDayOfWeek = 3;
                else if (checkedId == R.id.chipWed) currentDayOfWeek = 4;
                else if (checkedId == R.id.chipThu) currentDayOfWeek = 5;
                else if (checkedId == R.id.chipFri) currentDayOfWeek = 6;
                else if (checkedId == R.id.chipSat) currentDayOfWeek = 7;
                else if (checkedId == R.id.chipSun) currentDayOfWeek = 8;
                
                scheduleViewModel.setSelectedDay(currentDayOfWeek);
            }
        });
    }

    private void checkChipForDay(int dayOfWeek) {
        int chipId = R.id.chipMon;
        switch (dayOfWeek) {
            case 3: chipId = R.id.chipTue; break;
            case 4: chipId = R.id.chipWed; break;
            case 5: chipId = R.id.chipThu; break;
            case 6: chipId = R.id.chipFri; break;
            case 7: chipId = R.id.chipSat; break;
            case 8: chipId = R.id.chipSun; break;
        }
        binding.chipGroupDays.check(chipId);
    }

    private void setupClickListeners() {
        binding.fabAddSchedule.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
            navController.navigate(R.id.addScheduleFragment, bundle);
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeViewModel() {
        // First observe subjects so adapter has them for mapping
        scheduleViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            adapter.setSubjects(subjects);
        });

        // Then load schedules
        scheduleViewModel.getSchedulesForDay().observe(getViewLifecycleOwner(), schedules -> {
            adapter.submitList(schedules);
            
            if (schedules == null || schedules.isEmpty()) {
                binding.rvSchedules.setVisibility(android.view.View.GONE);
                binding.llEmptyState.setVisibility(android.view.View.VISIBLE);
            } else {
                binding.rvSchedules.setVisibility(android.view.View.VISIBLE);
                binding.llEmptyState.setVisibility(android.view.View.GONE);
            }
        });

        scheduleViewModel.setSelectedDay(currentDayOfWeek);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
