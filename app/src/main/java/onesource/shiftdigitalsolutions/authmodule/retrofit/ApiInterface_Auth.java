package onesource.shiftdigitalsolutions.authmodule.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface ApiInterface_Auth {
@GET("validateAndSaveSSID")
    Call<JsonElement> checkAuth(@Query("SSID") String SSID, @Header("Authorization") String auth);
}



