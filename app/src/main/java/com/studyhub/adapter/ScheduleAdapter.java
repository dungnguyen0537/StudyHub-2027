package com.studyhub.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.database.entity.ScheduleEntity;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.ItemScheduleBinding;
import com.studyhub.interfaces.OnItemClickListener;
import java.util.Objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAdapter extends ListAdapter<ScheduleEntity, ScheduleAdapter.ScheduleViewHolder> {

    private final OnItemClickListener<ScheduleEntity> clickListener;
    private final Map<String, SubjectEntity> subjectMap = new HashMap<>();

    public ScheduleAdapter(OnItemClickListener<ScheduleEntity> clickListener) {
        super(new DiffCallback());
        this.clickListener = clickListener;
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
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemScheduleBinding binding = ItemScheduleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ScheduleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final ItemScheduleBinding binding;

        ScheduleViewHolder(ItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }

        void bind(ScheduleEntity schedule) {
            binding.tvStartTime.setText(schedule.getStartTime());
            binding.tvEndTime.setText(schedule.getEndTime());
            binding.tvRoom.setText(schedule.getRoom());

            SubjectEntity subject = subjectMap.get(schedule.getSubjectId());
            if (subject != null) {
                binding.tvSubjectName.setText(subject.getName());
                try {
                    binding.vColorIndicator.setBackgroundColor(Color.parseColor(subject.getColorHex()));
                } catch (Exception e) {
                    binding.vColorIndicator.setBackgroundColor(Color.BLUE);
                }
            } else {
                binding.tvSubjectName.setText("Môn học không xác định");
                binding.vColorIndicator.setBackgroundColor(Color.GRAY);
            }

            if (schedule.isReminderEnabled()) {
                binding.ivReminder.setVisibility(View.VISIBLE);
            } else {
                binding.ivReminder.setVisibility(View.GONE);
            }
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<ScheduleEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull ScheduleEntity oldItem, @NonNull ScheduleEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScheduleEntity oldItem, @NonNull ScheduleEntity newItem) {
            return Objects.equals(oldItem.getStartTime(), newItem.getStartTime()) &&
                   Objects.equals(oldItem.getEndTime(), newItem.getEndTime()) &&
                   Objects.equals(oldItem.getRoom(), newItem.getRoom()) &&
                   Objects.equals(oldItem.getSubjectId(), newItem.getSubjectId()) &&
                   oldItem.isReminderEnabled() == newItem.isReminderEnabled();
        }
    }
}
