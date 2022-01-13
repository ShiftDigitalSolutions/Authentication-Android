package onesource.shiftdigitalsolutions.layers.data.repo;

import static onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener.FirebaseListenerResult.FAILURE;
import static onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener.FirebaseListenerResult.SUCCESS;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;

public class FirebaseRepo {
    private final FirebaseUser user;

    public FirebaseRepo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getFirebaseToken(FirebaseListener callback) {
        if (user == null)
            callback.onTokenGenerated(FAILURE, null);
        else {
            user.getIdToken(true)
                    .addOnSuccessListener(s -> callback.onTokenGenerated(SUCCESS, s.getToken()))
                    .addOnFailureListener(f -> callback.onTokenGenerated(FAILURE, ""));
        }
    }

}
