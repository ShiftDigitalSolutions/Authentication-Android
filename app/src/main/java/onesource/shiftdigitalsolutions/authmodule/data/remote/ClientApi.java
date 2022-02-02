package onesource.shiftdigitalsolutions.authmodule.data.remote;

import io.reactivex.rxjava3.core.Observable;
import onesource.shiftdigitalsolutions.authmodule.data.entity.AuthenticationEntity;
import retrofit2.http.GET;

public interface ClientApi {
    @GET("validateViaFirebase")
    Observable<AuthenticationEntity> authenticate();

}
