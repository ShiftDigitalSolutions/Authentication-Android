package onesource.shiftdigitalsolutions.layers.domain.authentication;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.layers.data.entity.AuthenticationUsingSqlEntity;
import onesource.shiftdigitalsolutions.layers.data.repo.FirebaseRepo;
import onesource.shiftdigitalsolutions.layers.data.repo.SqlRepo;
import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;

public class AuthenticationUseCase implements AuthenticationCallback {

    public AuthenticationUseCase() {
    }

    @Override
    public void getFirebaseToken(FirebaseListener callback) {
        FirebaseRepo firebaseRepo = new FirebaseRepo();
        firebaseRepo.getFirebaseToken(callback);
    }

    @Override
    public Observable<AuthenticationUsingSqlEntity> logInUsingSql(String token, String ssid) {
        SqlRepo sqlRepo = new SqlRepo();
        return sqlRepo.authenticate(token, ssid).authenticate();
    }

}
