package com.studyhub.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.studyhub.database.entity.DeadlineEntity;
import com.studyhub.database.entity.TaskEntity;
import com.studyhub.databinding.FragmentAnalyticsBinding;
import com.studyhub.viewmodel.DeadlineViewModel;
import com.studyhub.viewmodel.TaskViewModel;

import java.util.ArrayList;

public class AnalyticsFragment extends Fragment {

    private FragmentAnalyticsBinding binding;
    private TaskViewModel taskViewModel;
    private DeadlineViewModel deadlineViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        deadlineViewModel = new ViewModelProvider(this).get(DeadlineViewModel.class);

        setupPieChart();
        setupBarChart();

        observeTasks();
        observeDeadlines();
    }

    private void setupPieChart() {
        PieChart pieChart = binding.pieChartTask;
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.animateY(1000);
    }

    private void setupBarChart() {
        BarChart barChart = binding.barChartDeadline;
        barChart.getDescription().setEnabled(false);
        barChart.setDrawGridBackground(false);
        barChart.animateY(1000);
        
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Low");
        labels.add("Medium");
        labels.add("High");
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
    }

    private void observeTasks() {
        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> {
            if (tasks != null) {
                int completed = 0;
                int uncompleted = 0;

                for (TaskEntity task : tasks) {
                    if (task.isCompleted()) {
                        completed++;
                    } else {
                        uncompleted++;
                    }
                }

                updatePieChart(completed, uncompleted);
            }
        });
    }

    private void observeDeadlines() {
        deadlineViewModel.getAllDeadlines().observe(getViewLifecycleOwner(), deadlines -> {
            if (deadlines != null) {
                int low = 0, medium = 0, high = 0;

                for (DeadlineEntity deadline : deadlines) {
                    if (deadline.getPriority() == 1) {
                        low++;
                    } else if (deadline.getPriority() == 2) {
                        medium++;
                    } else if (deadline.getPriority() >= 3) {
                        high++;
                    }
                }

                updateBarChart(low, medium, high);
            }
        });
    }

    private void updatePieChart(int completed, int uncompleted) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        if (completed > 0) entries.add(new PieEntry(completed, "Completed"));
        if (uncompleted > 0) entries.add(new PieEntry(uncompleted, "Uncompleted"));

        // If there are no tasks, we don't display anything or maybe add a generic entry.
        if (entries.isEmpty()) {
            binding.pieChartTask.clear();
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "Task Status");
        int colorCompleted = Color.parseColor("#4CAF50");
        int colorUncompleted = Color.parseColor("#F44336");
        dataSet.setColors(colorCompleted, colorUncompleted);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);
        binding.pieChartTask.setData(data);
        binding.pieChartTask.invalidate();
    }

    private void updateBarChart(int low, int medium, int high) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, low));
        entries.add(new BarEntry(1f, medium));
        entries.add(new BarEntry(2f, high));

        BarDataSet dataSet = new BarDataSet(entries, "Priorities");
        int colorLow = Color.parseColor("#4CAF50");
        int colorMedium = Color.parseColor("#FFC107");
        int colorHigh = Color.parseColor("#F44336");
        dataSet.setColors(colorLow, colorMedium, colorHigh);
        dataSet.setValueTextSize(14f);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);
        binding.barChartDeadline.setData(data);
        binding.barChartDeadline.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
