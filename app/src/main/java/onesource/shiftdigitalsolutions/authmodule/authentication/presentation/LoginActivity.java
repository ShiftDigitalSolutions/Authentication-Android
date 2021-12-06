package onesource.shiftdigitalsolutions.authmodule.authentication.presentation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.model.ClientModel;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.repository.Client;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.repository.OnFirebaseLogin;
import onesource.shiftdigitalsolutions.authmodule.databinding.ActivityLoginBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements OnFirebaseLogin {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

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

                    final IdpResponse response = IdpResponse.fromResultIntent(result.getData());

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        if (user != null) {
                            user.getIdToken(true).addOnSuccessListener(getTokenResult -> {

                                //? start authentication using sql
                                onTokenGenerated(getTokenResult.getToken());

                            });
                        }
                    } else {
                        if (response != null && response.getError() != null) {
                            Log.e("AUTHENTICATION_TAG", "Response error: " + response.getError());
                        }
                    }
                });
    }

    private void authenticateUsingSql(final String token, final String ssid) {
        Client.getInstance(token, ssid)
                .authenticate()
                .enqueue(new Callback<ClientModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ClientModel> call, @NonNull Response<ClientModel> response) {
                        binding.authenticationProgress.setVisibility(View.GONE);
                        if (response.isSuccessful() && response.body() != null)
                            Snackbar.make(binding.getRoot(), response.body().getMessage(), Snackbar.LENGTH_LONG).show();
                        else
                            Snackbar.make(binding.getRoot(), "Failed to login response Code is: " + response.code(), Snackbar.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ClientModel> call, @NonNull Throwable t) {
                        Snackbar.make(binding.getRoot(), "Failed to login error: " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                        binding.authenticationProgress.setVisibility(View.GONE);
                    }
                });
    }

    private String getSSID() {
        @SuppressLint("HardwareIds") String ANDROID_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("AUTHENTICATION_TAG", ANDROID_ID);
        return ANDROID_ID;
    }

    @Override
    public void onTokenGenerated(String token) {
        binding.authenticationProgress.setVisibility(View.VISIBLE);
        authenticateUsingSql(token, getSSID());
    }
}