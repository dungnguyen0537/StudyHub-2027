package com.studyhub.fragment;

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

import com.studyhub.R;
import com.studyhub.adapter.ScheduleAdapter;
import com.studyhub.databinding.FragmentDashboardBinding;
import com.studyhub.viewmodel.DashboardViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private ScheduleAdapter scheduleAdapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        scheduleAdapter = new ScheduleAdapter(schedule -> {
            // Click to view details
        });
        binding.rvTodaySchedule.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTodaySchedule.setAdapter(scheduleAdapter);

        com.studyhub.utils.SwipeToDeleteCallback swipeCallback = new com.studyhub.utils.SwipeToDeleteCallback(requireContext(), position -> {
            com.studyhub.database.entity.ScheduleEntity scheduleToDelete = scheduleAdapter.getCurrentList().get(position);
            dashboardViewModel.deleteSchedule(scheduleToDelete);
            com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), "Đã xóa lịch học", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    // We don't have insert in DashboardViewModel right now, so we skip Undo or add it.
                    // .setAction("Hoàn tác", v -> dashboardViewModel.insertSchedule(scheduleToDelete))
                    .show();
        });
        new androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvTodaySchedule);
    }

    private void setupClickListeners() {
        binding.cardSubjects.setOnClickListener(v -> 
            navController.navigate(R.id.subjectFragment)
        );

        binding.tvViewAllSchedule.setOnClickListener(v -> 
            navController.navigate(R.id.scheduleFragment)
        );
        
        binding.cardTasks.setOnClickListener(v -> {
            if (getActivity() != null) {
                com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = 
                    getActivity().findViewById(R.id.bottom_navigation);
                if (bottomNav != null) {
                    bottomNav.setSelectedItemId(R.id.taskFragment);
                }
            }
        });

        binding.cardAnalytics.setOnClickListener(v -> 
            navController.navigate(R.id.analyticsFragment)
        );

        binding.cardFocus.setOnClickListener(v -> 
            navController.navigate(R.id.focusFragment)
        );
    }

    private void observeViewModel() {
        // Observe subject list just to get count
        dashboardViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            if (subjects != null) {
                binding.tvSubjectCount.setText(String.valueOf(subjects.size()));
                scheduleAdapter.setSubjects(subjects); // provide subjects to adapter to render names/colors
            }
        });

        // Observe today's schedules
        dashboardViewModel.getTodaysSchedules().observe(getViewLifecycleOwner(), schedules -> {
            if (schedules != null && !schedules.isEmpty()) {
                scheduleAdapter.submitList(schedules);
                binding.rvTodaySchedule.setVisibility(View.VISIBLE);
                binding.tvEmptySchedule.setVisibility(View.GONE);
            } else {
                scheduleAdapter.submitList(null);
                binding.rvTodaySchedule.setVisibility(View.GONE);
                binding.tvEmptySchedule.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
