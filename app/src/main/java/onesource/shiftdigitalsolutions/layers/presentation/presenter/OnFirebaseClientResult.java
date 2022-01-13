package onesource.shiftdigitalsolutions.layers.presentation.presenter;

public interface OnFirebaseClientResult {
    void onFirebaseResponse(FirebaseActivityResult result);

    enum FirebaseActivityResult {
        SUCCESS, FAILURE
    }
}
