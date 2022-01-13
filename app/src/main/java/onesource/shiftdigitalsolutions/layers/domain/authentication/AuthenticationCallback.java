package onesource.shiftdigitalsolutions.layers.domain.authentication;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.layers.data.entity.AuthenticationUsingSqlEntity;
import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;

public interface AuthenticationCallback {
    void getFirebaseToken(FirebaseListener callback);

    Observable<AuthenticationUsingSqlEntity> logInUsingSql(String token, String ssid);
}
