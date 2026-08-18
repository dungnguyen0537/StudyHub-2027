package com.studyhub.fragment.note;

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
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.studyhub.R;
import com.studyhub.adapter.NoteAdapter;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.FragmentNoteListBinding;
import com.studyhub.viewmodel.NoteViewModel;

public class NoteListFragment extends Fragment {

    private FragmentNoteListBinding binding;
    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNoteListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Ensure parent fragment provides navController
        try {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        } catch (Exception e) {
            navController = null; // Fallback if used independently
        }
        
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeViewModel();
    }

    private void setupRecyclerView() {
        adapter = new NoteAdapter(note -> {
            // Navigate to detail/edit screen
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putString(AppConstants.KEY_NOTE_ID, note.getId());
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, true);
                navController.navigate(R.id.addNoteFragment, bundle);
            }
        });
        
        // Notes are usually displayed in staggered grid like Google Keep
        binding.rvNotes.setLayoutManager(new androidx.recyclerview.widget.StaggeredGridLayoutManager(2, androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL));
        binding.rvNotes.setAdapter(adapter);

        com.studyhub.utils.SwipeToDeleteCallback swipeCallback = new com.studyhub.utils.SwipeToDeleteCallback(requireContext(), position -> {
            com.studyhub.model.Note noteToDelete = adapter.getCurrentList().get(position);
            noteViewModel.delete(noteToDelete);
            com.google.android.material.snackbar.Snackbar.make(binding.getRoot(), "Đã xóa Ghi chú", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
                    .setAction("Hoàn tác", v -> noteViewModel.insert(noteToDelete))
                    .show();
        });
        new androidx.recyclerview.widget.ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.rvNotes);
    }

    private void setupClickListeners() {
        binding.fabAddNote.setOnClickListener(v -> {
            if (navController != null) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.KEY_IS_EDIT_MODE, false);
                navController.navigate(R.id.addNoteFragment, bundle);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void observeViewModel() {
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            adapter.submitList(notes);
            
            if (notes == null || notes.isEmpty()) {
                binding.rvNotes.setVisibility(View.GONE);
                binding.llEmptyState.setVisibility(View.VISIBLE);
            } else {
                binding.rvNotes.setVisibility(View.VISIBLE);
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
