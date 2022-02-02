package onesource.shiftdigitalsolutions.authmodule.presentation.presenter;

public interface OnFirebaseClientListener {
    void onFirebaseResponse(FirebaseActivityResult result);

    enum FirebaseActivityResult {
        SUCCESS, FAILURE
    }
}
