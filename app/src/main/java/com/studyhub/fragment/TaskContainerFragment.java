package com.studyhub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;
import com.studyhub.R;
import com.studyhub.adapter.TaskPagerAdapter;
import com.studyhub.databinding.FragmentTaskContainerBinding;

public class TaskContainerFragment extends Fragment {

    private FragmentTaskContainerBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskContainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        TaskPagerAdapter pagerAdapter = new TaskPagerAdapter(this);
        binding.viewPager.setAdapter(pagerAdapter);
        
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.deadline_title);
                            break;
                        case 1:
                            tab.setText(R.string.nav_tasks);
                            break;
                        case 2:
                            tab.setText(R.string.notes_title);
                            break;
                    }
                }).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
