package com.studyhub.fragment.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.studyhub.R;
import com.studyhub.databinding.FragmentForgotPasswordBinding;
import com.studyhub.repository.AuthRepository;
import com.studyhub.viewmodel.AuthViewModel;

public class ForgotPasswordFragment extends Fragment {

    private FragmentForgotPasswordBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        setupClickListeners();
        observeViewModel();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> navController.navigateUp());

        binding.btnSendReset.setOnClickListener(v -> {
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            authViewModel.resetPassword(email);
        });
    }

    private void observeViewModel() {
        authViewModel.getEmailError().observe(getViewLifecycleOwner(), error -> 
            binding.tilEmail.setError(error)
        );

        authViewModel.getAuthState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;
            
            switch (state) {
                case LOADING:
                    setLoading(true);
                    break;
                case SUCCESS:
                    setLoading(false);
                    Toast.makeText(requireContext(), R.string.reset_email_sent, Toast.LENGTH_LONG).show();
                    authViewModel.resetState();
                    navController.navigateUp();
                    break;
                case ERROR:
                    setLoading(false);
                    break;
                case IDLE:
                default:
                    setLoading(false);
                    break;
            }
        });

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null && !message.isEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean isLoading) {
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        binding.btnSendReset.setEnabled(!isLoading);
        binding.etEmail.setEnabled(!isLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
