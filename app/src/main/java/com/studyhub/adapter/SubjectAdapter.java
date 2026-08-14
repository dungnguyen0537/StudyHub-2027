package com.studyhub.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.constant.SyncStatus;
import com.studyhub.database.entity.SubjectEntity;
import com.studyhub.databinding.ItemSubjectBinding;
import com.studyhub.interfaces.OnFavoriteClickListener;
import com.studyhub.interfaces.OnItemClickListener;
import java.util.Objects;

public class SubjectAdapter extends ListAdapter<SubjectEntity, SubjectAdapter.SubjectViewHolder> {

    private final OnItemClickListener<SubjectEntity> clickListener;
    private final OnFavoriteClickListener<SubjectEntity> favoriteClickListener;

    public SubjectAdapter(OnItemClickListener<SubjectEntity> clickListener, 
                          OnFavoriteClickListener<SubjectEntity> favoriteClickListener) {
        super(new DiffCallback());
        this.clickListener = clickListener;
        this.favoriteClickListener = favoriteClickListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectBinding binding = ItemSubjectBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new SubjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder {
        private final ItemSubjectBinding binding;

        SubjectViewHolder(ItemSubjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            
            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });

            binding.btnFavorite.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    SubjectEntity subject = getItem(position);
                    boolean newStatus = !subject.isFavorite();
                    favoriteClickListener.onFavoriteClick(subject, newStatus);
                }
            });
        }

        void bind(SubjectEntity subject) {
            binding.tvSubjectName.setText(subject.getName());
            binding.tvSubjectCode.setText(subject.getCode());
            binding.tvTeacher.setText(subject.getTeacher() != null && !subject.getTeacher().isEmpty() 
                                      ? subject.getTeacher() : "Chưa có GV");
            binding.tvCredits.setText(subject.getCredits() + " TC");
            
            try {
                binding.vColorIndicator.setBackgroundColor(Color.parseColor(subject.getColorHex()));
            } catch (Exception e) {
                binding.vColorIndicator.setBackgroundColor(Color.BLUE);
            }

            if (subject.isFavorite()) {
                binding.btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                binding.btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
            }

            // Show sync icon if pending sync
            if (subject.getSyncStatus() != SyncStatus.SYNCED) {
                binding.ivSyncStatus.setVisibility(View.VISIBLE);
            } else {
                binding.ivSyncStatus.setVisibility(View.GONE);
            }
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<SubjectEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull SubjectEntity oldItem, @NonNull SubjectEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SubjectEntity oldItem, @NonNull SubjectEntity newItem) {
            return Objects.equals(oldItem.getName(), newItem.getName()) &&
                   oldItem.isFavorite() == newItem.isFavorite() &&
                   oldItem.getSyncStatus() == newItem.getSyncStatus();
        }
    }
}
