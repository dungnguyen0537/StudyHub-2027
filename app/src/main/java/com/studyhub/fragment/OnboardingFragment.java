package com.studyhub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.studyhub.R;
import com.studyhub.adapter.OnboardingAdapter;
import com.studyhub.model.OnboardingItem;
import com.studyhub.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class OnboardingFragment extends Fragment {

    private ViewPager2 viewPager;
    private LinearLayout layoutDots;
    private MaterialButton btnNext;
    private MaterialButton btnSkip;
    private OnboardingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreferenceManager prefManager = new PreferenceManager(requireContext());
        if (!prefManager.isFirstTimeLaunch()) {
            // Already seen onboarding, jump straight to login
            Navigation.findNavController(view).navigate(R.id.action_onboardingFragment_to_loginFragment);
            return;
        }

        viewPager = view.findViewById(R.id.viewPagerOnboarding);
        layoutDots = view.findViewById(R.id.layoutDots);
        btnNext = view.findViewById(R.id.btnNext);
        btnSkip = view.findViewById(R.id.btnSkip);

        setupOnboardingItems();
        setupIndicators();
        setCurrentIndicator(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);

                if (position == adapter.getItemCount() - 1) {
                    btnNext.setText("Bắt đầu");
                } else {
                    btnNext.setText("Tiếp tục");
                }
            }
        });

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            } else {
                finishOnboarding();
            }
        });

        btnSkip.setOnClickListener(v -> finishOnboarding());
    }

    private void setupOnboardingItems() {
        List<OnboardingItem> items = new ArrayList<>();
        // Use placeholders, we can use app icons or other vectors
        items.add(new OnboardingItem(R.mipmap.owl_logo, "Quản lý Học tập", "Lên lịch trình, ghi chú và quản lý bài tập một cách thông minh nhất."));
        items.add(new OnboardingItem(android.R.drawable.ic_menu_agenda, "Không trễ Deadline", "Nhận thông báo nhắc nhở tự động, đảm bảo bạn không bao giờ bỏ lỡ một hạn chót nào."));
        items.add(new OnboardingItem(android.R.drawable.ic_popup_sync, "Đồng bộ Đám mây", "Dữ liệu của bạn được đồng bộ liên tục, truy cập mọi lúc mọi nơi trên mọi thiết bị."));
        
        adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);
    }

    private void setupIndicators() {
        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(requireContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutDots.addView(indicators[i]);
        }
    }

    private void setCurrentIndicator(int index) {
        int childCount = layoutDots.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutDots.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                ));
            }
        }
    }

    private void finishOnboarding() {
        PreferenceManager prefManager = new PreferenceManager(requireContext());
        prefManager.setFirstTimeLaunch(false);
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_onboardingFragment_to_loginFragment);
    }
}
