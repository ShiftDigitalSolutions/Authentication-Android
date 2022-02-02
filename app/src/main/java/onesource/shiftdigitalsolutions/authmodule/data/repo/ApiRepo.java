package onesource.shiftdigitalsolutions.authmodule.data.repo;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.authmodule.data.entity.AuthenticationEntity;
import onesource.shiftdigitalsolutions.authmodule.data.remote.RetrofitClient;

public class ApiRepo {
    public Observable<AuthenticationEntity> authenticate(String token, String ssid) {
        try {
            return RetrofitClient.getRetrofitClient(token, ssid).authenticate();
        } catch (Exception e) {
            return Observable.empty();
        }
    }
}
