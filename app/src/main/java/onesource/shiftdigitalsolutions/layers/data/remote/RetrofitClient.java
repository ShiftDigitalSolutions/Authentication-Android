package onesource.shiftdigitalsolutions.layers.data.remote;

import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://authmodule.azurewebsites.net/";

    public ClientApi getRetrofitClient(final String token, final String ssid) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient(token, ssid))
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofit.create(ClientApi.class);
    }

    private OkHttpClient getOkHttpClient(final String token, final String ssid) {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .addHeader("SSID", ssid)
                    .build();
            return chain.proceed(newRequest);
        })
                .connectTimeout(90, TimeUnit.SECONDS)
                .build();
    }

}
