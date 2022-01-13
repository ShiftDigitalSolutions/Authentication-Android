package onesource.shiftdigitalsolutions.layers.data.remote;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.layers.data.entity.AuthenticationUsingSqlEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientApi {
    @GET("validateAndSaveSSID")
    Observable<AuthenticationUsingSqlEntity> authenticate();

    //? For test purpose
    //? testing retrofit requests
//    @GET("validateAndSaveSSID")
//    Call<AuthenticationUsingSqlEntity> authenticateTest();
}
