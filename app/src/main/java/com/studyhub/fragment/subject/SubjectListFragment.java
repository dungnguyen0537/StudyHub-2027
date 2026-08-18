package com.studyhub.fragment.subject;

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
import com.studyhub.adapter.SubjectAdapter;
import com.studyhub.constant.AppConstants;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.FragmentSubjectListBinding;
import com.studyhub.viewmodel.SubjectViewModel;

public class SubjectListFragment extends Fragment {

    private FragmentSubjectListBinding binding;
    private SubjectViewModel subjectViewModel;
    private SubjectAdapter adapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSubjectListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new SubjectAdapter(
            subject -> {
                // Navigate to detail/edit screen
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.KEY_SUBJECT_ID, subject.getId());
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, true);
                navController.navigate(R.id.action_subjectFragment_to_addSubjectFragment, bundle);
            },
            (subject, isFavorite) -> {
                // Toggle favorite
                subject.setFavorite(isFavorite);
                subjectViewModel.update(subject);
            }
        );
        binding.rvSubjects.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvSubjects.setAdapter(adapter);

        com.studyhub.utils.SwipeToDeleteCallback swipeCallback = new com.studyhub.utils.SwipeToDeleteCallback(requireContext(), position -> {
            com.studyhub.model.Subject subjectToDelete = adapter.getCurrentList().get(position);
            subjectViewModel.delete(subjectToDelete);
            com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), "Đã xóa Môn học", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác", v -> subjectViewModel.insert(subjectToDelete))
                    .show();
        });
        new androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvSubjects);
    }

    private void setupClickListeners() {
        binding.fabAddSubject.setOnClickListener(v -> 
            navController.navigate(R.id.action_subjectFragment_to_addSubjectFragment)
        );

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            // Re-trigger observer or just hide refresh (ViewModel automatically observes DB)
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeViewModel() {
        subjectViewModel.getAllSubjects().observe(getViewLifecycleOwner(), subjects -> {
            adapter.submitList(subjects);
            
            if (subjects == null || subjects.isEmpty()) {
                binding.rvSubjects.setVisibility(View.GONE);
                binding.llEmptyState.setVisibility(View.VISIBLE);
            } else {
                binding.rvSubjects.setVisibility(android.view.View.VISIBLE);
                binding.llEmptyState.setVisibility(android.view.View.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
