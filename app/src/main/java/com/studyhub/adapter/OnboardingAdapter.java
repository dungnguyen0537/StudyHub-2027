package com.studyhub.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.studyhub.R;
import com.studyhub.model.OnboardingItem;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private final List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding_page, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        holder.bind(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivOnboardingImage;
        private final TextView tvOnboardingTitle;
        private final TextView tvOnboardingDesc;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOnboardingImage = itemView.findViewById(R.id.ivOnboardingImage);
            tvOnboardingTitle = itemView.findViewById(R.id.tvOnboardingTitle);
            tvOnboardingDesc = itemView.findViewById(R.id.tvOnboardingDesc);
        }

        void bind(OnboardingItem item) {
            ivOnboardingImage.setImageResource(item.getImage());
            tvOnboardingTitle.setText(item.getTitle());
            tvOnboardingDesc.setText(item.getDescription());
        }
    }
}
