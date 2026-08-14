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
import com.studyhub.databinding.FragmentRegisterBinding;
import com.studyhub.repository.AuthRepository;
import com.studyhub.viewmodel.AuthViewModel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;
    private GoogleSignInClient mGoogleSignInClient;

    private final ActivityResultLauncher<android.content.Intent> googleSignInLauncher = 
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    if (account != null) {
                        com.google.firebase.auth.AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                        authViewModel.loginWithGoogle(credential);
                    }
                } catch (ApiException e) {
                    Toast.makeText(requireContext(), "Đăng nhập Google thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
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
        binding.btnRegister.setOnClickListener(v -> {
            String fullName = binding.etFullName.getText() != null ? binding.etFullName.getText().toString().trim() : "";
            String email = binding.etEmail.getText() != null ? binding.etEmail.getText().toString().trim() : "";
            String password = binding.etPassword.getText() != null ? binding.etPassword.getText().toString().trim() : "";
            String confirmPassword = binding.etConfirmPassword.getText() != null ? binding.etConfirmPassword.getText().toString().trim() : "";
            
            authViewModel.register(fullName, email, password, confirmPassword);
        });

        binding.tvLogin.setOnClickListener(v -> navController.navigateUp());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        binding.btnGoogleLogin.setOnClickListener(v -> {
            googleSignInLauncher.launch(mGoogleSignInClient.getSignInIntent());
        });
    }

    private void observeViewModel() {
        authViewModel.getNameError().observe(getViewLifecycleOwner(), error -> 
            binding.tilFullName.setError(error)
        );

        authViewModel.getEmailError().observe(getViewLifecycleOwner(), error -> 
            binding.tilEmail.setError(error)
        );

        authViewModel.getPasswordError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && error.contains("xác nhận")) {
                binding.tilPassword.setError(null);
                binding.tilConfirmPassword.setError(error);
            } else {
                binding.tilPassword.setError(error);
                binding.tilConfirmPassword.setError(null);
            }
        });

        authViewModel.getAuthState().observe(getViewLifecycleOwner(), state -> {
            if (state == null) return;
            
            switch (state) {
                case LOADING:
                    setLoading(true);
                    break;
                case SUCCESS:
                    setLoading(false);
                    if (authViewModel.isEmailVerified()) {
                        // Logged in with Google or auto-verified
                        Toast.makeText(requireContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.action_registerFragment_to_nav_main);
                    } else {
                        // Standard Email/Password registration
                        Toast.makeText(requireContext(), R.string.register_success, Toast.LENGTH_LONG).show();
                        authViewModel.logout(); // Ensure they have to log in manually after verification
                        navController.navigateUp(); // Go back to login
                    }
                    authViewModel.resetState();
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
        binding.btnRegister.setEnabled(!isLoading);
        binding.btnGoogleLogin.setEnabled(!isLoading);
        binding.etFullName.setEnabled(!isLoading);
        binding.etEmail.setEnabled(!isLoading);
        binding.etPassword.setEnabled(!isLoading);
        binding.etConfirmPassword.setEnabled(!isLoading);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
