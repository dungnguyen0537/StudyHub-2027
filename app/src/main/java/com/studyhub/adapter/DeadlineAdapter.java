package com.studyhub.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.constant.Priority;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.ItemDeadlineBinding;
import com.studyhub.interfaces.OnItemClickListener;
import com.studyhub.utils.DateUtils;

import java.util.Objects;
import com.studyhub.constant.DeadlineStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeadlineAdapter extends ListAdapter<DeadlineEntity, DeadlineAdapter.DeadlineViewHolder> {

    private final OnItemClickListener<DeadlineEntity> clickListener;
    private final OnItemClickListener<DeadlineEntity> checkListener;
    private final Map<String, SubjectEntity> subjectMap = new HashMap<>();

    public DeadlineAdapter(OnItemClickListener<DeadlineEntity> clickListener, 
                           OnItemClickListener<DeadlineEntity> checkListener) {
        super(new DiffCallback());
        this.clickListener = clickListener;
        this.checkListener = checkListener;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        subjectMap.clear();
        if (subjects != null) {
            for (SubjectEntity subject : subjects) {
                subjectMap.put(subject.getId(), subject);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeadlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDeadlineBinding binding = ItemDeadlineBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new DeadlineViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadlineViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class DeadlineViewHolder extends RecyclerView.ViewHolder {
        private final ItemDeadlineBinding binding;

        DeadlineViewHolder(ItemDeadlineBinding binding) {
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
                    DeadlineEntity deadline = getItem(position);
                    String newStatus = binding.cbCompleted.isChecked() ? DeadlineStatus.COMPLETED : DeadlineStatus.PENDING;
                    deadline.setStatus(newStatus);
                    checkListener.onItemClick(deadline);
                }
            });
        }

        void bind(DeadlineEntity deadline) {
            binding.tvTitle.setText(deadline.getTitle());
            binding.tvDate.setText(DateUtils.formatDate(deadline.getDueDate()));
            
            // Temporary, format dueTime better in real app
            binding.tvTime.setText(DateUtils.formatTime(deadline.getDueDate()));
            
            SubjectEntity subject = subjectMap.get(deadline.getSubjectId());
            if (subject != null) {
                binding.tvSubject.setText(subject.getCode() + " - " + subject.getName());
            } else {
                binding.tvSubject.setText("Chung");
            }

            binding.cbCompleted.setChecked(DeadlineStatus.COMPLETED.equals(deadline.getStatus()));
            
            if (DeadlineStatus.COMPLETED.equals(deadline.getStatus())) {
                binding.tvTitle.setPaintFlags(binding.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                binding.getRoot().setAlpha(0.6f);
            } else {
                binding.tvTitle.setPaintFlags(binding.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                binding.getRoot().setAlpha(1.0f);
            }
            
            switch (deadline.getPriority()) {
                case Priority.HIGH:
                    binding.ivPriority.setColorFilter(android.graphics.Color.RED);
                    break;
                case Priority.MEDIUM:
                    binding.ivPriority.setColorFilter(android.graphics.Color.parseColor("#FF9800")); // Orange
                    break;
                case Priority.LOW:
                    binding.ivPriority.setColorFilter(android.graphics.Color.parseColor("#4CAF50")); // Green
                    break;
            }
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<DeadlineEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull DeadlineEntity oldItem, @NonNull DeadlineEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull DeadlineEntity oldItem, @NonNull DeadlineEntity newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                   Objects.equals(oldItem.getStatus(), newItem.getStatus()) &&
                   oldItem.getDueDate() == newItem.getDueDate() &&
                   oldItem.getPriority() == newItem.getPriority();
        }
    }
}
