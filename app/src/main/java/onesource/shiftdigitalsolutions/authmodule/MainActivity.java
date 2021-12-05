package onesource.shiftdigitalsolutions.authmodule;

import static onesource.shiftdigitalsolutions.authmodule.UserCons.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import onesource.shiftdigitalsolutions.authmodule.firebase.Authentication;
import onesource.shiftdigitalsolutions.authmodule.retrofit.ApiInterface_Auth;
import onesource.shiftdigitalsolutions.authmodule.retrofit.RetrofitClient;
import onesource.shiftdigitalsolutions.authmodule.retrofit.Survey;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String Token;
    private String SSID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);
         SSID = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user_FIRE = firebaseAuth.getCurrentUser();
                if (user_FIRE != null) {

                    user_FIRE.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                        @Override
                        public void onSuccess(GetTokenResult result) {
                            Token=result.getToken();
                            Log.e("kjdhdfkjsd","  getToken "+ result.getToken());
                            Log.e("kjdhdfkjsd","  SSID "+ SSID);
                            Ret();
                        }
                    });
                } else {
                    startActivity(new Intent(MainActivity.this, Authentication.class));
                    finish();
                }
            }
        };

    }
    public void Ret(){
        Call<JsonElement> call = RetrofitClient.getInstance().getMyApi().checkAuth(SSID, "Bearer "+Token);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                switch (response.code()){
                    case 400:
                        break;
                    case 401:
                        break;
                    case 402:
                        break;

                }
                Log.e("kjdhdfkjsd", "response.message()  "+response.code());
                Log.e("kjdhdfkjsd", "response.message()  "+response.message() );
            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.e("dfgfgdfgd", "onFailure " );            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);

        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}