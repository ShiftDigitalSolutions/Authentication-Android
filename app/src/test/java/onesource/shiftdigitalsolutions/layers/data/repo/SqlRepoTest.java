package onesource.shiftdigitalsolutions.layers.data.repo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentCaptor.*;

import android.accounts.NetworkErrorException;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Subscriber;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockWebServer;
import onesource.shiftdigitalsolutions.layers.data.entity.AuthenticationUsingSqlEntity;
import onesource.shiftdigitalsolutions.layers.data.remote.ClientApi;
import onesource.shiftdigitalsolutions.layers.data.remote.RetrofitClient;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;


//? For test purpose
//? testing retrofit requests

@RunWith(MockitoJUnitRunner.class)
public class SqlRepoTest {
//    public static final String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjM1MDZmMzc1MjI0N2ZjZjk0Y2JlNWQyZDZiNTlmYThhMmJhYjFlYzIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXV0aG1vZHVsZS00Mzg4MiIsImF1ZCI6ImF1dGhtb2R1bGUtNDM4ODIiLCJhdXRoX3RpbWUiOjE2NDE5OTk0MjIsInVzZXJfaWQiOiJVQlNyclRZSFlxWlBJallKOXpLMHd5M2ZjWWQyIiwic3ViIjoiVUJTcnJUWUhZcVpQSWpZSjl6SzB3eTNmY1lkMiIsImlhdCI6MTY0MTk5OTQyNiwiZXhwIjoxNjQyMDAzMDI2LCJwaG9uZV9udW1iZXIiOiIrMjA1NTU1NTU1NTU1IiwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJwaG9uZSI6WyIrMjA1NTU1NTU1NTU1Il19LCJzaWduX2luX3Byb3ZpZGVyIjoicGhvbmUifX0.ZjF93JAja58x21o00gKfKGG8xYvRePL9rwAUkuA5Psq8vTAVQ0Klj1uXkqK_4g6Bf3n6JQdoyOKG2ZcT21PoseK570Vb53SF5j0og94fRDCOEUsjvPieFIlml9XYuYyxBgCVSicULKLfDzVeQ8VHXDf5aBVzC46gWqk4siIzbgXYSfzH4wveutI2X_c2IS_anAumVzpXa1PKTzwLQnzVPbISQJjRmzv0JoBD_i0JOuMxPJlmVUc9MCtV0F-JoNf8BCPJxhzQ8-fhFW5gJVBodScUUkY8ogoj3XFup1Ok-rPvoe0ABaUo5fvzZmCyhQ-40_j_VhVR5lddVxF5e2cboA";
//    public static final String SSID = "43fa13542946b06b";
//
//    SqlRepo SUT;
//
//    @Before
//    public void setup() throws Exception {
//        SUT = new SqlRepo();
//    }
//
//
//    @Test
//    public void authenticate_correctTokenAndCorrectSsid_responseOf200Returned() throws Exception {
//        int response = authenticateUsingSqlTest(TOKEN, SSID);
//        assertThat(response, is(200));
//    }
//
//    @Test
//    public void authenticate_correctTokenAndInvalidSsid_responseOf401Returned() throws Exception {
//        int response = authenticateUsingSqlTest(TOKEN, "");
//        assertThat(response, is(401));
//    }
//
//    @Test
//    public void authenticate_invalidTokenAndCorrectSsid_responseOf401Returned() throws Exception {
//        int response = authenticateUsingSqlTest("", SSID);
//        assertThat(response, is(401));
//    }
//
//    @Test
//    public void authenticate_invalidTokenAndInvalidSsid_responseOf401Returned() throws Exception {
//        int response = authenticateUsingSqlTest("", "");
//        assertThat(response, is(401));
//    }
//
//    private int authenticateUsingSqlTest(String token, String ssid) throws Exception {
//        Call<AuthenticationUsingSqlEntity> call = SUT.authenticate(token, ssid).authenticateTest();
//        retrofit2.Response<AuthenticationUsingSqlEntity> res = call.execute();
//        return res.code();
//    }
}