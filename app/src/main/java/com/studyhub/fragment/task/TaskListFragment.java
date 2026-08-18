package com.studyhub.fragment.task;

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
import com.studyhub.adapter.TaskAdapter;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.FragmentTaskListBinding;
import com.studyhub.viewmodel.TaskViewModel;

public class TaskListFragment extends Fragment {

    private FragmentTaskListBinding binding;
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        } catch (Exception e) {
            navController = null;
        }
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new TaskAdapter(
            task -> {
                // Navigate to detail/edit screen
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.KEY_TASK_ID, task.getId());
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, true);
                navController.navigate(R.id.addTaskFragment, bundle);
            }
            },
            task -> {
                // Checkbox checked/unchecked
                taskViewModel.update(task);
            }
        );
        binding.rvTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvTasks.setAdapter(adapter);

        com.studyhub.utils.SwipeToDeleteCallback swipeCallback = new com.studyhub.utils.SwipeToDeleteCallback(requireContext(), position -> {
            com.studyhub.database.entity.TaskEntity taskToDelete = adapter.getCurrentList().get(position);
            taskViewModel.delete(taskToDelete);
            com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), "Đã xóa công việc", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác", v -> taskViewModel.insert(taskToDelete))
                    .show();
        });

        new androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvTasks);
    }

    private void setupClickListeners() {
        binding.fabAddTask.setOnClickListener(v -> {
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
                navController.navigate(R.id.addTaskFragment, bundle);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeViewModel() {
        // Observe all tasks
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            adapter.submitList(tasks);
            
            if (tasks == null || tasks.isEmpty()) {
                binding.rvTasks.setVisibility(View.GONE);
                binding.llEmptyState.setVisibility(View.VISIBLE);
            } else {
                binding.rvTasks.setVisibility(View.VISIBLE);
                binding.llEmptyState.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
