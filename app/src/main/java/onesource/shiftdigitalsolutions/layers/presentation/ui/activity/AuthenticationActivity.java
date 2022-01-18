package onesource.shiftdigitalsolutions.layers.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityAuthenticationBinding;
import onesource.shiftdigitalsolutions.layers.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.layers.domain.listener.AuthenticationResponse;
import onesource.shiftdigitalsolutions.layers.presentation.presenter.OnFirebaseClientResult;
import onesource.shiftdigitalsolutions.layers.presentation.ui.utility.Utility;
import onesource.shiftdigitalsolutions.layers.presentation.viewmodel.AuthenticationViewModel;

public class AuthenticationActivity extends AppCompatActivity implements OnFirebaseClientResult {

    //+ Constant region
    private String SQL_KEY;
    private String ANDROID_ID;
    //- End region

    //+ Variable region
    private SharedPreferenceHelper sharedPreferenceHelper;
    ActivityAuthenticationBinding binding;
    Utility utility;
    //- End region

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        init();
    }

    @SuppressLint("HardwareIds")
    private void init() {
        String SHARED_PREF_KEY = getString(R.string.shared_preference_key);
        SQL_KEY = getString(R.string.sql_authentication_success_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE));
        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
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

        authenticationActivity.launch(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.googleg_standard_color_18)
                .setTheme(R.style.Theme_AuthModule)
                .build());
    }

    private ActivityResultLauncher<Intent> getFirebaseAuthenticationActivity() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        onFirebaseResponse(FirebaseActivityResult.SUCCESS);
                    } else {
                        onFirebaseResponse(FirebaseActivityResult.FAILURE);
                    }
                });
    }

    @Override
    public void onFirebaseResponse(FirebaseActivityResult result) {
        switch (result) {
            case SUCCESS:
                binding.progressBar.setVisibility(View.VISIBLE);
                startAuthentication();
                break;
            case FAILURE:
                Snackbar.make(binding.getRoot(), getString(R.string.firebase_error), Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(this::startFirebaseLogin, 3000);
                break;
        }
    }

    private void startAuthentication() {
        AuthenticationViewModel authenticationViewModel =
                new ViewModelProvider(this).get(AuthenticationViewModel.class);
        authenticationViewModel.authenticate(ANDROID_ID);
        authenticationViewModel.getResponse().observe(this, this::onAuthenticationComplete);
    }

    private void onAuthenticationComplete(AuthenticationResponse.ServerResponse response) {
        if (response == AuthenticationResponse.ServerResponse.SUCCESS) {
            sharedPreferenceHelper.saveObject(SQL_KEY, 200);
            binding.progressBar.setVisibility(View.GONE);
            //? TODO next task
            binding.done.setText("Done !!");
        } else {
            sharedPreferenceHelper.remove(SQL_KEY);
            binding.progressBar.setVisibility(View.GONE);
            handleErrorResponse(response);
            new Handler().postDelayed(this::startFirebaseLogin, 3000);
        }
    }

    private void handleErrorResponse(AuthenticationResponse.ServerResponse response) {
        switch (response) {
            case LOGGED_IN_ANOTHER_DEVICE:
                utility.displaySnakeBar(binding.getRoot(), getString(R.string.login_soon));
                break;
            case INVALID_TOKEN:
                utility.displaySnakeBar(binding.getRoot(), getString(R.string.invalid_token));
                break;
            case NETWORK_ERROR:
                utility.displaySnakeBar(binding.getRoot(), getString(R.string.network_error));
                break;
            case FIREBASE_ERROR:
                utility.displaySnakeBar(binding.getRoot(), getString(R.string.firebase_error));
                break;
            case SERVER_ERROR:
                utility.displaySnakeBar(binding.getRoot(), getString(R.string.server_error));
                break;
        }
    }

}
