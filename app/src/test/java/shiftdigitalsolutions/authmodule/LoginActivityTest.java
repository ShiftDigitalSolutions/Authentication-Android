package shiftdigitalsolutions.authmodule;

import org.junit.Assert;
import org.junit.Test;

import onesource.shiftdigitalsolutions.authmodule.authentication.data.model.ClientModel;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.repository.Client;
import retrofit2.Call;


public class LoginActivityTest {

    //? Dummy Values
    private final String SSID = "f5ea73fd6757ce16";
    private final String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjM1MDZmMzc1MjI0N2ZjZjk0Y2JlNWQyZDZiNTlmYThhMmJhYjFlYzIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXV0aG1vZHVsZS00Mzg4MiIsImF1ZCI6ImF1dGhtb2R1bGUtNDM4ODIiLCJhdXRoX3RpbWUiOjE2NDE5MDM0NTcsInVzZXJfaWQiOiJVQlNyclRZSFlxWlBJallKOXpLMHd5M2ZjWWQyIiwic3ViIjoiVUJTcnJUWUhZcVpQSWpZSjl6SzB3eTNmY1lkMiIsImlhdCI6MTY0MTkwMzQ1OSwiZXhwIjoxNjQxOTA3MDU5LCJwaG9uZV9udW1iZXIiOiIrMjA1NTU1NTU1NTU1IiwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJwaG9uZSI6WyIrMjA1NTU1NTU1NTU1Il19LCJzaWduX2luX3Byb3ZpZGVyIjoicGhvbmUifX0.I_MQVR3TDYH9-04_Y-NBRLwae1iFXvUY3X8cNZwJfdT3pxm6FCa3Et6cxtvC2whJFFUWGtx7iF6Tnu-oznD3MFA2J-PMFo13qWh_Nze8McJc_QH20I2KUzwREktRrDWkGCRPdTnlmB_t-i6lwFyRs9EwZE0-4_irtqgUJq4RNafZUoAsGcTF6ort4YWXVYIOQOUQg0gWrO_DVTJJEK2s3NCVe2pdCNFWhcqtYLFnYslbKu_s0vBkXqagYpKmyoDegTGZkvbrfEQORVbRc3gLyD_uXl2ZFWdM_YoZfpKdCP7g9zbbJ9CPu2yY9FU6qdy6gITzBejjxi3loyk-8MPusg";

    @Test
    public void testUsingEmptySSIDAndTokenShouldReturnSuccess() throws Exception {
        int response = authenticateUsingSqlTest(TOKEN, SSID);
        Assert.assertEquals(200, response);
    }

    @Test
    public void testUsingEmptySSIDEmptyTokenShouldReturnSuccess() throws Exception {
        int response = authenticateUsingSqlTest("TOKEN", "SSID");
        Assert.assertEquals(401, response);
    }

    @Test
    public void testUsingSSIDAndEmptyTokenShouldReturnSuccess() throws Exception {
        int response = authenticateUsingSqlTest("TOKEN", SSID);
        Assert.assertEquals(401, response);
    }

    @Test
    public void testUsingTokenAndEmptySSIDShouldReturnSuccess() throws Exception {
        int response = authenticateUsingSqlTest(TOKEN, "");
        Assert.assertEquals(401, response);
    }


    private int authenticateUsingSqlTest(String token, String ssid) throws Exception {
        Call<ClientModel> call = Client.getInstance(token, ssid).authenticate();
        retrofit2.Response<ClientModel> res = call.execute();
        return res.code();
    }
}