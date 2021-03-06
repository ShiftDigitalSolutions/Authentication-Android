package onesource.shiftdigitalsolutions.authmodule.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityAuthenticationBinding;
import onesource.shiftdigitalsolutions.authmodule.presentation.presenter.ApiResponse;
import onesource.shiftdigitalsolutions.authmodule.presentation.presenter.OnFirebaseClientListener;
import onesource.shiftdigitalsolutions.authmodule.presentation.ui.utility.Utility;
import onesource.shiftdigitalsolutions.authmodule.presentation.viewmodel.AuthenticationViewModel;

public class AuthenticationActivity extends AppCompatActivity implements OnFirebaseClientListener {

    //+ Constant region
    private String SQL_KEY;
    private String ANDROID_ID;
    //- End region

    //+ Variable region
    private SharedPreferenceHelper sharedPreferenceHelper;
    ActivityAuthenticationBinding binding;
    Utility utility;
    String HOME_SCREEN_KEY;
    //- End region

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);

        init();
    }

    @SuppressLint("HardwareIds")
    private void init() {
        String SHARED_PREF_KEY = getString(R.string.shared_preference_key);
        SQL_KEY = getString(R.string.sql_authentication_success_key);
        HOME_SCREEN_KEY = getString(R.string.home_screen_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE));
        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        utility = new Utility();

        actions();
    }

    private void actions() {
        startFirebaseLogin();
    }

    private void startFirebaseLogin() {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.PhoneBuilder().build());


        ActivityResultLauncher<Intent> authenticationActivity = getFirebaseAuthenticationActivity();

        //? For Test
//        AuthUI.IdpConfig phoneConfigWithDefaultNumber = new AuthUI.IdpConfig.PhoneBuilder()
//                .setDefaultNumber("+204444444444")
//                .build();

        authenticationActivity.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher)
                .setTheme(R.style.FirebaseUI)
//                .setDefaultProvider(phoneConfigWithDefaultNumber)
                .build());
    }

    private ActivityResultLauncher<Intent> getFirebaseAuthenticationActivity() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        onFirebaseResponse(FirebaseActivityResult.SUCCESS);
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        this.finishAffinity();
                    } else {
                        onFirebaseResponse(FirebaseActivityResult.FAILURE);
                    }
                });
    }

    @Override
    public void onFirebaseResponse(FirebaseActivityResult result) {
        switch (result) {
            case SUCCESS:
                binding.authenticationProgressBar.setVisibility(View.VISIBLE);
                startAuthentication();
                break;
            case FAILURE:
                Snackbar.make(binding.getRoot(), getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(this::startFirebaseLogin, 3000);
                break;
        }
    }

    private void startAuthentication() {
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        authenticationViewModel.authenticate(ANDROID_ID);
        authenticationViewModel.getResponse().observe(this, this::onAuthenticationComplete);
    }

    private void onAuthenticationComplete(ApiResponse.ServerResponse response) {
        binding.authenticationProgressBar.setVisibility(View.GONE);
        if (response == ApiResponse.ServerResponse.SUCCESS) {
            sharedPreferenceHelper.saveObject(SQL_KEY, 200);
            startHomeScreenActivity();
        } else handleErrorResponse(response);
    }

    private void startHomeScreenActivity() {
        finishAffinity();
        Intent homeScreenActivity = new Intent(getApplicationContext(), BaseActivity.class);
        startActivity(homeScreenActivity);
    }

    private void handleErrorResponse(ApiResponse.ServerResponse response) {
        if (response == ApiResponse.ServerResponse.NETWORK_ERROR) {
            utility.displaySnakeBar(binding.getRoot(), getString(utility.handleErrorResponse(response)));
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
        Intent errorScreenActivity = new Intent(getApplicationContext(), ErrorScreenActivity.class);
        errorScreenActivity.putExtra(getString(R.string.server_in_maintenance), isInMaintenance);
        new Handler().postDelayed(() -> {
            finishAffinity();
            startActivity(errorScreenActivity);
        }, 1500);
    }
}