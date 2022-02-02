package onesource.shiftdigitalsolutions.authmodule.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.SocketTimeoutException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import onesource.shiftdigitalsolutions.authmodule.data.repo.ApiRepo;
import onesource.shiftdigitalsolutions.authmodule.data.repo.FirebaseRepo;
import onesource.shiftdigitalsolutions.authmodule.domain.authentication.AuthenticationUseCaseImpl;
import onesource.shiftdigitalsolutions.authmodule.domain.authentication.FirebaseUserListener;
import onesource.shiftdigitalsolutions.authmodule.presentation.presenter.ApiResponse;
import retrofit2.HttpException;

public class AuthenticationViewModel extends ViewModel implements ApiResponse {
    //+ Declaration region
    private final AuthenticationUseCaseImpl authenticationUseCaseImpl;
    private MutableLiveData<ApiResponse.ServerResponse> response = new MutableLiveData<>();
    //- End region


    public AuthenticationViewModel() {
        this.authenticationUseCaseImpl = new AuthenticationUseCaseImpl(new FirebaseRepo(), new ApiRepo());
    }

    public AuthenticationUseCaseImpl getAuthenticationUseCaseImpl() {
        return authenticationUseCaseImpl;
    }

    public MutableLiveData<ApiResponse.ServerResponse> getResponse() {
        return response;
    }

    public void authenticate(final String ssid) {
        if (response.hasActiveObservers())
            response = new MutableLiveData<>();
        authenticationUseCaseImpl.getFirebaseToken((listener, token) -> {
            if (listener == FirebaseUserListener.FirebaseListenerResult.SUCCESS)
                authenticationUseCaseImpl.login(token, ssid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                r -> onAuthenticationCompleteListener(r.getStatusCode()),
                                e -> {
                                    try {
                                        onAuthenticationCompleteListener(e.getClass() != SocketTimeoutException.class ? ((HttpException) e).code() : 502);
                                    } catch (Exception exception) {
                                        onAuthenticationCompleteListener(12029);
                                    }
                                }
                        );
            else
                onAuthenticationCompleteListener(600);
        });
    }

    @Override
    public void onAuthenticationCompleteListener(int requestCode) {
        if (requestCode == 200)
            response.setValue(ApiResponse.ServerResponse.SUCCESS);
        else if (requestCode == 401)
            response.setValue(ApiResponse.ServerResponse.INVALID_TOKEN);
        else if (requestCode == 12029)
            response.setValue(ServerResponse.NETWORK_ERROR);
        else if (requestCode == 404)
            response.setValue(ApiResponse.ServerResponse.USER_NOT_FOUND);
        else if (requestCode == 406)
            response.setValue(ApiResponse.ServerResponse.LOGGED_IN_ANOTHER_DEVICE);
        else if (requestCode == 500)
            response.setValue(ApiResponse.ServerResponse.SERVER_ERROR);
        else if (requestCode == 502)
            response.setValue(ApiResponse.ServerResponse.BAD_GATEWAY);
        else if (requestCode == 600)
            response.setValue(ApiResponse.ServerResponse.FIREBASE_ERROR);
    }

}
