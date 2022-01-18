package onesource.shiftdigitalsolutions.layers.presentation.ui.utility;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.layers.domain.listener.AuthenticationResponse;

public class Utility {
    public void displaySnakeBar(@NonNull View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public Integer handleErrorResponse(@NonNull AuthenticationResponse.ServerResponse response) {
        switch (response) {
            case LOGGED_IN_ANOTHER_DEVICE:
                return R.string.login_soon;
            case NETWORK_ERROR:
                return R.string.network_error;
            case INVALID_TOKEN:
                return R.string.invalid_token;
            case USER_NOT_FOUND:
                return R.string.user_not_found;
            case FIREBASE_ERROR:
                return R.string.firebase_error;
            case SERVER_ERROR:
                return R.string.server_error;
            default:
                return R.string.something_went_wrong;
        }
    }
}
