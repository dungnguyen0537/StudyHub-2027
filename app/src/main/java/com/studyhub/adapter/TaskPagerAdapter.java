package com.studyhub.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.studyhub.fragment.deadline.DeadlineListFragment;
import com.studyhub.fragment.note.NoteListFragment;
import com.studyhub.fragment.task.TaskListFragment;

public class TaskPagerAdapter extends FragmentStateAdapter {

    public TaskPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DeadlineListFragment();
            case 1:
                return new TaskListFragment();
            case 2:
                return new NoteListFragment();
            default:
                return new DeadlineListFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
