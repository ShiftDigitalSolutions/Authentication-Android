package onesource.shiftdigitalsolutions.authmodule.presentation.ui.utility;

import android.content.res.Configuration;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

import onesource.shiftdigitalsolutions.authmodule.R;
import onesource.shiftdigitalsolutions.authmodule.presentation.presenter.ApiResponse;

public class Utility {
    public void displaySnakeBar(@NonNull View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public Integer handleErrorResponse(@NonNull ApiResponse.ServerResponse response) {
        switch (response) {
            case SUCCESS:
                return R.string.success_with_no_data;
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
            case BAD_GATEWAY:
                return R.string.bad_gateway;
            default:
                return R.string.something_went_wrong;
        }
    }


}
