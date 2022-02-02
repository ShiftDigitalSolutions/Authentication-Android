package onesource.shiftdigitalsolutions.authmodule.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityMainBinding;
import onesource.shiftdigitalsolutions.authmodule.presentation.presenter.ApiResponse;
import onesource.shiftdigitalsolutions.authmodule.presentation.ui.utility.Utility;
import onesource.shiftdigitalsolutions.authmodule.presentation.viewmodel.AuthenticationViewModel;

public class MainActivity extends AppCompatActivity {

    //+ Constant region
    private String SQL_KEY;
    private static final int SUCCESS_LOGIN = 200;
    private String ANDROID_ID;
    //- End region

    //+ Variables region
    SharedPreferenceHelper sharedPreferenceHelper;
    ActivityMainBinding binding;
    Utility utility;
    //- End region

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    @SuppressLint("HardwareIds")
    private void init() {
        String SHARED_KEY = getString(R.string.shared_preference_key);
        SQL_KEY = getString(R.string.sql_authentication_success_key);
        String DEFAULT_LANG_KEY = getString(R.string.default_language_key);

        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_KEY, MODE_PRIVATE));
        utility = new Utility();
        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String DEFAULT_LANG = (String) sharedPreferenceHelper.getValue(String.class, DEFAULT_LANG_KEY);
        if (!DEFAULT_LANG.equals(""))
            changeDefaultLanguage(DEFAULT_LANG);

        action();
    }

    private void action() {
        binding.reloadMainActivity.setOnClickListener(v -> {
            binding.reloadMainActivity.setVisibility(View.GONE);
            checkLogin();
        });
        checkLogin();
    }

    private void changeDefaultLanguage(@NonNull String language) {
        Configuration configuration = new Configuration(getResources().getConfiguration());
        configuration.setLocale(new Locale(language));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    private void checkLogin() {
        if ((int) sharedPreferenceHelper.getValue(Integer.class, SQL_KEY) != SUCCESS_LOGIN)
            startAuthenticationActivity();
        else
            startAuthenticationUsingApi();
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
    }

    private void startAuthenticationUsingApi() {
        binding.homeProgressBar.setVisibility(View.VISIBLE);
        AuthenticationViewModel authenticationViewModel =
                new ViewModelProvider(this).get(AuthenticationViewModel.class);
        authenticationViewModel.authenticate(ANDROID_ID);
        authenticationViewModel.getResponse().observe(this, this::onAuthenticationComplete);
    }

    private void onAuthenticationComplete(ApiResponse.ServerResponse response) {
        binding.homeProgressBar.setVisibility(View.GONE);
        if (response == ApiResponse.ServerResponse.SUCCESS) {
            sharedPreferenceHelper.saveObject(SQL_KEY, 200);
            startHomeScreenActivity();
        } else handleErrorResponse(response);
    }

    private void startHomeScreenActivity() {
        finishAffinity();
        Intent homeScreenActivity = new Intent(MainActivity.this, BaseActivity.class);
        startActivity(homeScreenActivity);
    }

    private void handleErrorResponse(ApiResponse.ServerResponse response) {
        if (response == ApiResponse.ServerResponse.NETWORK_ERROR) {
            utility.displaySnakeBar(binding.getRoot(), getString(utility.handleErrorResponse(response)));
            binding.reloadMainActivity.setVisibility(View.VISIBLE);
        } else if (response == ApiResponse.ServerResponse.LOGGED_IN_ANOTHER_DEVICE || response == ApiResponse.ServerResponse.SERVER_ERROR) {
            if (response == ApiResponse.ServerResponse.LOGGED_IN_ANOTHER_DEVICE)
                utility.displaySnakeBar(binding.getRoot(), getString(utility.handleErrorResponse(response)));
            startErrorActivityInMaintenance(false);
        } else {
            sharedPreferenceHelper.remove(SQL_KEY);
            startErrorActivityInMaintenance(true);
        }
    }

    private void startErrorActivityInMaintenance(boolean isInMaintenance) {
        Intent errorScreenActivity = new Intent(MainActivity.this, ErrorScreenActivity.class);
        errorScreenActivity.putExtra(getString(R.string.server_in_maintenance), isInMaintenance);
        new Handler().postDelayed(() -> {
            finishAffinity();
            startActivity(errorScreenActivity);
        }, 1500);
    }

}