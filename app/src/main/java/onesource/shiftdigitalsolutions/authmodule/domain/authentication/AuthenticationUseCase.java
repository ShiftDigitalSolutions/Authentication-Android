package onesource.shiftdigitalsolutions.authmodule.domain.authentication;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.authmodule.data.entity.AuthenticationEntity;

public interface AuthenticationUseCase {
    void getFirebaseToken(FirebaseUserListener callback);

    Observable<AuthenticationEntity> login(String token, String ssid);
}
