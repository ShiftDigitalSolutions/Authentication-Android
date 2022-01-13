package onesource.shiftdigitalsolutions.layers.data.repo;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.layers.data.entity.AuthenticationUsingSqlEntity;
import onesource.shiftdigitalsolutions.layers.data.remote.ClientApi;
import onesource.shiftdigitalsolutions.layers.data.remote.RetrofitClient;

public class SqlRepo {
    private final RetrofitClient retrofitClient;

    public SqlRepo() {
        retrofitClient = new RetrofitClient();
    }

    public Observable<AuthenticationUsingSqlEntity> authenticate(String token, String ssid) {
        return retrofitClient.getRetrofitClient(token, ssid).authenticate();
    }
}
