package onesource.shiftdigitalsolutions.authmodule.presentation.presenter;


public interface ApiResponse {
    void onAuthenticationCompleteListener(int requestCode);

    enum ServerResponse {
        SUCCESS, LOGGED_IN_ANOTHER_DEVICE, NETWORK_ERROR, FIREBASE_ERROR, SERVER_ERROR, INVALID_TOKEN, USER_NOT_FOUND, BAD_GATEWAY
    }
}
