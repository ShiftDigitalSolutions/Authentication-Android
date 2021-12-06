package onesource.shiftdigitalsolutions.authmodule.authentication.data.repository;

import onesource.shiftdigitalsolutions.authmodule.authentication.data.model.ClientModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientInterface {
    @GET("validateAndSaveSSID")
    Call<ClientModel> authenticate();
}
