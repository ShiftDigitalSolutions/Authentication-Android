package onesource.shiftdigitalsolutions.authmodule.domain.authentication;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.authmodule.data.entity.AuthenticationEntity;
import onesource.shiftdigitalsolutions.authmodule.data.repo.FirebaseRepo;
import onesource.shiftdigitalsolutions.authmodule.data.repo.ApiRepo;

public class AuthenticationUseCaseImpl implements AuthenticationUseCase {

    FirebaseRepo firebaseRepo;
    ApiRepo apiRepo;

    public AuthenticationUseCaseImpl(FirebaseRepo firebaseRepo, ApiRepo apiRepo) {
        this.firebaseRepo = firebaseRepo;
        this.apiRepo = apiRepo;
    }

    @Override
    public void getFirebaseToken(FirebaseUserListener callback) {
        firebaseRepo.getFirebaseToken(callback);
    }

    @Override
    public Observable<AuthenticationEntity> login(String token, String ssid) {
        return apiRepo.authenticate(token, ssid);
    }
}
