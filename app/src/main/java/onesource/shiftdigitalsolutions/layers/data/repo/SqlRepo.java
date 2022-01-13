package onesource.shiftdigitalsolutions.layers.data.repo;

import onesource.shiftdigitalsolutions.layers.data.remote.ClientApi;
import onesource.shiftdigitalsolutions.layers.data.remote.RetrofitClient;

public class SqlRepo {
    private final RetrofitClient retrofitClient;

    public SqlRepo() {
        retrofitClient = new RetrofitClient();
    }

    public ClientApi authenticate(String token, String ssid) {
        return retrofitClient.getRetrofitClient(token, ssid);
    }
}
