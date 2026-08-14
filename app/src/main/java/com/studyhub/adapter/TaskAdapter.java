package com.studyhub.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.database.entity.TaskEntity;
import com.studyhub.databinding.ItemTaskBinding;
import com.studyhub.interfaces.OnItemClickListener;
import java.util.Objects;

public class TaskAdapter extends ListAdapter<TaskEntity, TaskAdapter.TaskViewHolder> {

    private final OnItemClickListener<TaskEntity> clickListener;
    private final OnItemClickListener<TaskEntity> checkListener;

    public TaskAdapter(OnItemClickListener<TaskEntity> clickListener, 
                       OnItemClickListener<TaskEntity> checkListener) {
        super(new DiffCallback());
        this.clickListener = clickListener;
        this.checkListener = checkListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskBinding binding;

        TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });

            binding.cbCompleted.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    TaskEntity task = getItem(position);
                    task.setCompleted(binding.cbCompleted.isChecked());
                    checkListener.onItemClick(task);
                }
            });
        }

        void bind(TaskEntity task) {
            binding.tvTitle.setText(task.getTitle());
            binding.cbCompleted.setChecked(task.isCompleted());
            
            if (task.isCompleted()) {
                binding.tvTitle.setPaintFlags(binding.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                binding.getRoot().setAlpha(0.6f);
            } else {
                binding.tvTitle.setPaintFlags(binding.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                binding.getRoot().setAlpha(1.0f);
            }
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<TaskEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull TaskEntity oldItem, @NonNull TaskEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull TaskEntity oldItem, @NonNull TaskEntity newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                   oldItem.isCompleted() == newItem.isCompleted();
        }
    }
}
