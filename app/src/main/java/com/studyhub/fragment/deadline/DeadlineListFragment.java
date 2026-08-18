package com.studyhub.fragment.deadline;

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
import com.studyhub.adapter.DeadlineAdapter;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.FragmentDeadlineListBinding;
import com.studyhub.viewmodel.DeadlineViewModel;

public class DeadlineListFragment extends Fragment {

    private FragmentDeadlineListBinding binding;
    private DeadlineViewModel deadlineViewModel;
    private DeadlineAdapter adapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDeadlineListBinding.inflate(inflater, container, false);
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
        deadlineViewModel = new ViewModelProvider(this).get(DeadlineViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new DeadlineAdapter(
            deadline -> {
                // Navigate to detail/edit screen
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.KEY_DEADLINE_ID, deadline.getId());
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, true);
                navController.navigate(R.id.addDeadlineFragment, bundle);
            }
            },
            deadline -> {
                // Checkbox checked/unchecked
                deadlineViewModel.update(deadline);
            }
        );
        binding.rvDeadlines.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDeadlines.setAdapter(adapter);

        com.studyhub.utils.SwipeToDeleteCallback swipeCallback = new com.studyhub.utils.SwipeToDeleteCallback(requireContext(), position -> {
            com.studyhub.database.entity.DeadlineEntity deadline = adapter.getCurrentList().get(position);
            deadlineViewModel.delete(deadline);
            com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), "Đã xóa Deadline", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác", v -> deadlineViewModel.insert(deadline))
                    .show();
        });
        new androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvDeadlines);
    }

    private void setupClickListeners() {
        binding.fabAddDeadline.setOnClickListener(v -> {
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
                navController.navigate(R.id.addDeadlineFragment, bundle);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeViewModel() {
        // Observe subjects for mapping subject name in adapter
        deadlineViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            adapter.setSubjects(subjects);
        });

        // Observe all deadlines
        deadlineViewModel.getAllDeadlines().observe(getViewLifecycleOwner(), deadlines -> {
            adapter.submitList(deadlines);
            
            if (deadlines == null || deadlines.isEmpty()) {
                binding.rvDeadlines.setVisibility(View.GONE);
                binding.llEmptyState.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.rvDeadlines.setVisibility(View.VISIBLE);
                binding.llEmptyState.getRoot().setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
