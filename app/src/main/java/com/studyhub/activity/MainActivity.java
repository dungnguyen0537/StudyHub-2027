package com.studyhub.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.studyhub.R;
import com.studyhub.constant.AppConstants;
import com.studyhub.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            
            // Listen for destination changes to show/hide bottom nav
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                int id = destination.getId();
                if (id == R.id.loginFragment || id == R.id.registerFragment || id == R.id.forgotPasswordFragment) {
                    binding.bottomNavigation.setVisibility(View.GONE);
                } else {
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                }
            });
            
            // Handle Intent routing from SplashActivity
            if (savedInstanceState == null) {
                handleIntentRouting();
            }
        }
        
        requestNotificationPermission();
    }

    private void requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                androidx.core.app.ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        
        requestExactAlarmPermission();
    }

    private void requestExactAlarmPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            android.app.AlarmManager alarmManager = (android.app.AlarmManager) getSystemService(android.content.Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                // Show a dialog or toast explaining why the permission is needed
                android.widget.Toast.makeText(this, "Vui lòng cấp quyền báo thức để thông báo Deadline chính xác", android.widget.Toast.LENGTH_LONG).show();
                android.content.Intent intent = new android.content.Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }
    
    private void handleIntentRouting() {
        if (getIntent() != null && getIntent().hasExtra(AppConstants.NAV_EXTRA_NAVIGATE_TO)) {
            String navigateTo = getIntent().getStringExtra(AppConstants.NAV_EXTRA_NAVIGATE_TO);
            if (AppConstants.NAV_LOGIN.equals(navigateTo)) {
                // Ensure we are on auth graph
                if (navController.getCurrentDestination() != null && 
                    navController.getCurrentDestination().getId() != R.id.loginFragment) {
                    navController.navigate(R.id.loginFragment);
                }
            } else if (AppConstants.NAV_DASHBOARD.equals(navigateTo)) {
                // We're authenticated, switch to main graph
                navController.navigate(R.id.nav_main);
            }
        }
    }
}
