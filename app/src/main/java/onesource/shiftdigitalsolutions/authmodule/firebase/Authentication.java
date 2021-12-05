package onesource.shiftdigitalsolutions.authmodule.firebase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import onesource.shiftdigitalsolutions.authmodule.R;

public class Authentication extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;

    //? Constants
    private static final String TAG = "MainActivityTag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build());
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.common_full_open_on_phone)       // Set logo drawable
                        .setTheme(R.style.Base_Theme_AppCompat)      // Set theme
                        .build(),
                RC_SIGN_IN);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            final IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user_FIRE = mAuth.getCurrentUser();
                if (user_FIRE != null) {
//                    startActivity(new Intent(Authentication.this, PermissionsCheck.class));
//                    finish();
                }else {

                }
            } else {
                if (response!=null && response.getError()!=null ){//&& response.getError().getErrorCode()!=0

                }
            }
        }
    }
}
