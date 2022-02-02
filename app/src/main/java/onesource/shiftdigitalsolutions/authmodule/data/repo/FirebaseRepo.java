package onesource.shiftdigitalsolutions.authmodule.data.repo;

import static onesource.shiftdigitalsolutions.authmodule.domain.authentication.FirebaseUserListener.FirebaseListenerResult.FAILURE;
import static onesource.shiftdigitalsolutions.authmodule.domain.authentication.FirebaseUserListener.FirebaseListenerResult.SUCCESS;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import onesource.shiftdigitalsolutions.authmodule.domain.authentication.FirebaseUserListener;

public class FirebaseRepo {
    private final FirebaseUser user;

    public FirebaseRepo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getFirebaseToken(FirebaseUserListener callback) {
        if (user == null)
            callback.onTokenGenerated(FAILURE, null);
        else {
            user.getIdToken(false)
                    .addOnSuccessListener(s -> callback.onTokenGenerated(SUCCESS, s.getToken()))
                    .addOnFailureListener(f -> callback.onTokenGenerated(FAILURE, ""));
        }
    }

}
