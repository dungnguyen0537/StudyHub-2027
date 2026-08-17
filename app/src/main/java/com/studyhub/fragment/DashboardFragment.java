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

        androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback swipeCallback = new androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback(0, androidx.recyclerview.widget.ItemTouchHelper.LEFT | androidx.recyclerview.widget.ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, @NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                com.studyhub.database.entity.ScheduleEntity scheduleToDelete = scheduleAdapter.getCurrentList().get(position);
                
                new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Xóa lịch học")
                        .setMessage("Bạn có chắc chắn muốn xóa lịch học này?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            dashboardViewModel.deleteSchedule(scheduleToDelete);
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> {
                            scheduleAdapter.notifyItemChanged(position);
                        })
                        .setOnCancelListener(dialog -> scheduleAdapter.notifyItemChanged(position))
                        .show();
            }
        };

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
