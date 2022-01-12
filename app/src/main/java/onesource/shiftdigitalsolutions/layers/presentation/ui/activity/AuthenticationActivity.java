package onesource.shiftdigitalsolutions.layers.presentation.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityAuthenticationBinding;
import onesource.shiftdigitalsolutions.layers.data.local.SharedPreferenceHelper;
import onesource.shiftdigitalsolutions.layers.domain.authentication.AuthenticationUseCase;
import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;
import onesource.shiftdigitalsolutions.layers.presentation.presenter.OnFirebaseClientResult;

public class AuthenticationActivity extends AppCompatActivity implements OnFirebaseClientResult {

    //+ Constant region
    private static final String TAG = "FirebaseAuthActivityTag";
    private String SQL_KEY;
    private String ANDROID_ID;
    //- End region

    //+ Variable region
    private SharedPreferenceHelper sharedPreferenceHelper;
    ActivityAuthenticationBinding binding;
    //- End region

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication);
        init();
        startFirebaseLogin();
    }

    @SuppressLint("HardwareIds")
    private void init() {
        String SHARED_PREF_KEY = getString(R.string.shared_preference_key);
        SQL_KEY = getString(R.string.sql_authentication_success_key);
        sharedPreferenceHelper = new SharedPreferenceHelper(getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE));
        ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
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
                    final IdpResponse response = IdpResponse.fromResultIntent(result.getData());
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        onFirebaseResponse(FirebaseActivityResult.SUCCESS);
                    } else {
                        if (response != null && response.getError() != null) {
                            onFirebaseResponse(FirebaseActivityResult.GENERAL_ERROR);
                        } else {
                            onFirebaseResponse(FirebaseActivityResult.FAILURE);
                        }
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
            case GENERAL_ERROR:
            case FAILURE:
                Snackbar.make(binding.getRoot(), getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(this::startFirebaseLogin, 3000);
                break;
        }
    }

    private void onSqlResponse(Integer code) {
        if (code == 200) {
            sharedPreferenceHelper.saveObject(SQL_KEY, 200);
            binding.progressBar.setVisibility(View.GONE);
            binding.done.setText("Done !!");
        } else {
            sharedPreferenceHelper.remove(SQL_KEY);
            Snackbar.make(binding.getRoot(), getString(R.string.something_went_wrong), Snackbar.LENGTH_LONG).show();
            binding.progressBar.setVisibility(View.GONE);
            new Handler().postDelayed(this::startFirebaseLogin, 3000);
        }
    }

    private void startAuthentication() {
        //TODO should initialized by view model
        AuthenticationUseCase authentication = new AuthenticationUseCase();
        authentication.getFirebaseToken(
                (listener, token) -> {
                    if (listener == FirebaseListener.FirebaseListenerResult.SUCCESS) {
                        authentication.logInUsingSql(token, ANDROID_ID)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        r -> onSqlResponse(r.getStatusCode()),
                                        e -> onSqlResponse(500)
                                );
                    } else {
                        onSqlResponse(500);
                    }
                }
        );
    }

}
