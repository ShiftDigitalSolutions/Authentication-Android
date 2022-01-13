package onesource.shiftdigitalsolutions.layers.domain.listener;

public interface AuthenticationResponse {
    void onAuthenticationCompleteListener(ServerResponse requestCode);

    enum ServerResponse {
        SUCCESS, LOGGED_IN_ANOTHER_DEVICE, NETWORK_ERROR, FIREBASE_ERROR, SERVER_ERROR
    }
}
