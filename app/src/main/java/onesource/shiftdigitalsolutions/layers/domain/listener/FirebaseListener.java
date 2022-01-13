package onesource.shiftdigitalsolutions.layers.domain.listener;

public interface FirebaseListener {
    void onTokenGenerated(FirebaseListenerResult listener, String token);
    enum FirebaseListenerResult {
        SUCCESS, FAILURE
    }
}