package onesource.shiftdigitalsolutions.authmodule.domain.authentication;

public interface FirebaseUserListener {
    void onTokenGenerated(FirebaseListenerResult listener, String token);
    enum FirebaseListenerResult {
        SUCCESS, FAILURE
    }
}