package onesource.shiftdigitalsolutions.layers.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import onesource.shiftdigitalsolutions.layers.domain.authentication.AuthenticationUseCase;
import onesource.shiftdigitalsolutions.layers.domain.listener.AuthenticationResponse;
import onesource.shiftdigitalsolutions.layers.domain.listener.FirebaseListener;

public class AuthenticationViewMode extends ViewModel {
    private final AuthenticationUseCase authenticationUseCase;
    private final MutableLiveData<AuthenticationResponse.ServerResponse> response = new MutableLiveData<>();

    public AuthenticationViewMode() {
        this.authenticationUseCase = new AuthenticationUseCase();
    }

    public MutableLiveData<AuthenticationResponse.ServerResponse> getResponse() {
        return response;
    }

    public void authenticate(final String ssid) {
        authenticationUseCase.getFirebaseToken((listener, token) -> {
            if (listener == FirebaseListener.FirebaseListenerResult.SUCCESS) {
                authenticationUseCase.logInUsingSql(token, ssid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                r -> {
                                    if (r.getStatusCode() == 200)
                                        response.setValue(AuthenticationResponse.ServerResponse.SUCCESS);
                                    else if (r.getStatusCode() == 406)
                                        response.setValue(AuthenticationResponse.ServerResponse.LOGGED_IN_ANOTHER_DEVICE);
                                    else if (r.getStatusCode() == 404)
                                        response.setValue(AuthenticationResponse.ServerResponse.SERVER_ERROR);
                                    else if (r.getStatusCode() == 500) {
                                        response.setValue(AuthenticationResponse.ServerResponse.SERVER_ERROR);
                                    }
                                },
                                e -> response.setValue(AuthenticationResponse.ServerResponse.SERVER_ERROR)
                        );
            } else {
                response.setValue(AuthenticationResponse.ServerResponse.FIREBASE_ERROR);
            }
        });
    }
}
