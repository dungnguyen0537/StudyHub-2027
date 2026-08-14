package com.studyhub.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.database.entity.NoteEntity;
import com.studyhub.databinding.ItemNoteBinding;
import com.studyhub.interfaces.OnItemClickListener;
import com.studyhub.utils.DateUtils;
import java.util.Objects;

public class NoteAdapter extends ListAdapter<NoteEntity, NoteAdapter.NoteViewHolder> {

    private final OnItemClickListener<NoteEntity> clickListener;

    public NoteAdapter(OnItemClickListener<NoteEntity> clickListener) {
        super(new DiffCallback());
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final ItemNoteBinding binding;

        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(getItem(position));
                }
            });
        }

        void bind(NoteEntity note) {
            binding.tvTitle.setText(note.getTitle());
            binding.tvContent.setText(note.getContent());
            binding.tvDate.setText(DateUtils.formatDateTime(note.getUpdatedAt()));
            
            // Color is determined by the associated subject, not the note itself
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<NoteEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                   Objects.equals(oldItem.getContent(), newItem.getContent()) &&
                   oldItem.getUpdatedAt() == newItem.getUpdatedAt();
        }
    }
}
