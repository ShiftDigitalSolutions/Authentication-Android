package onesource.shiftdigitalsolutions.authmodule.authentication.data.repository;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.model.ClientModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    private static final String BASE_URL = "https://authmodule.azurewebsites.net/";
    private final ClientInterface clientInterface;

    private static Client INSTANCE;

    private Client(final String token, final String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient(token, ssid))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        clientInterface = retrofit.create(ClientInterface.class);
    }

    public static Client getInstance(final String token, final String ssid) {
        if (INSTANCE == null)
            INSTANCE = new Client(token, ssid);
        return INSTANCE;
    }

    private static OkHttpClient getOkHttpClient(final String token, final String ssid) {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("SSID", ssid)
                    .build();
            return chain.proceed(newRequest);
        }).build();
    }


    public Call<ClientModel> authenticate() {
        return clientInterface.authenticate();
    }
}
