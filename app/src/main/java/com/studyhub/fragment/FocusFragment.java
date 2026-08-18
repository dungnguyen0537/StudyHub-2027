package com.studyhub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.studyhub.R;

public class FocusFragment extends Fragment {

    private TextView tvTimer;
    private MaterialButton btnStart;
    private MaterialButton btnPauseStop;

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;
    private long timeLeftInMillis = 25 * 60 * 1000; // 25 minutes
    private static final long START_TIME_IN_MILLIS = 25 * 60 * 1000;

    public FocusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_focus, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTimer = view.findViewById(R.id.tvTimer);
        btnStart = view.findViewById(R.id.btnStart);
        btnPauseStop = view.findViewById(R.id.btnPauseStop);

        btnStart.setOnClickListener(v -> startTimer());
        
        btnPauseStop.setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            } else {
                resetTimer();
            }
        });

        updateCountDownText();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                btnStart.setEnabled(true);
                btnStart.setText("Bắt đầu");
                btnPauseStop.setText("Dừng");
                timeLeftInMillis = START_TIME_IN_MILLIS;
                updateCountDownText();
                vibrateAndShowSuccess();
            }
        }.start();

        isTimerRunning = true;
        btnStart.setEnabled(false);
        btnPauseStop.setText("Tạm dừng");
    }

    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        btnStart.setEnabled(true);
        btnStart.setText("Tiếp tục");
        btnPauseStop.setText("Dừng");
    }

    private void resetTimer() {
        timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        btnStart.setText("Bắt đầu");
        btnPauseStop.setText("Tạm dừng / Dừng");
        btnStart.setEnabled(true);
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        tvTimer.setText(timeLeftFormatted);
    }

    private void vibrateAndShowSuccess() {
        Context context = getContext();
        if (context != null) {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(500);
                }
            }
            Toast.makeText(context, "Hoàn thành phiên tập trung!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
