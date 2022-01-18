package onesource.shiftdigitalsolutions.layers.presentation.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import onesource.shiftdigitalsolutions.layers.domain.authentication.AuthenticationUseCase;
import onesource.shiftdigitalsolutions.layers.domain.listener.AuthenticationResponse;
import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;
import retrofit2.HttpException;

public class AuthenticationViewModel extends ViewModel implements AuthenticationResponse {

    //+ Declaration region
    private final AuthenticationUseCase authenticationUseCase = new AuthenticationUseCase();
    private final MutableLiveData<ServerResponse> response = new MutableLiveData<>();
    //- End region

    public MutableLiveData<ServerResponse> getResponse() {
        return response;
    }

    public void authenticate(final String ssid) {
        authenticationUseCase.getFirebaseToken((listener, token) -> {
            Log.d("SalamaTag", "token: " + token);
            Log.d("SalamaTag", "ssid " + ssid);
            if (listener == FirebaseListener.FirebaseListenerResult.SUCCESS) {
                authenticationUseCase.logInUsingSql(token, ssid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                r -> onAuthenticationCompleteListener(r.getStatusCode()),
                                e -> onAuthenticationCompleteListener(((HttpException) e).code())
                        );
            } else {
                onAuthenticationCompleteListener(600);
            }
        });
    }

    @Override
    public void onAuthenticationCompleteListener(int requestCode) {
        if (requestCode == 200)
            response.setValue(ServerResponse.SUCCESS);
        else if (requestCode == 406)
            response.setValue(ServerResponse.LOGGED_IN_ANOTHER_DEVICE);
        else if (requestCode == 401)
            response.setValue(ServerResponse.INVALID_TOKEN);
        else if (requestCode == 500)
            response.setValue(ServerResponse.SERVER_ERROR);
        else if (requestCode == 600) {
            response.setValue(ServerResponse.FIREBASE_ERROR);
        }
    }
}
