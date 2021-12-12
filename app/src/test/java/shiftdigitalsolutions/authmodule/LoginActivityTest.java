package shiftdigitalsolutions.authmodule;

import org.junit.Assert;
import org.junit.Test;

import onesource.shiftdigitalsolutions.authmodule.authentication.data.model.ClientModel;
import onesource.shiftdigitalsolutions.authmodule.authentication.data.repository.Client;
import retrofit2.Call;


public class LoginActivityTest {


    //? Dummy Values
    private final String SSID = "f5ea73fd6757ce16";
    private final String TOKEN = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ3OTg5ZTU4ZWU1ODM4OTgzZDhhNDQwNWRlOTVkYTllZTZmNWVlYjgiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vYXV0aG1vZHVsZS00Mzg4MiIsImF1ZCI6ImF1dGhtb2R1bGUtNDM4ODIiLCJhdXRoX3RpbWUiOjE2MzkzMzEwNDIsInVzZXJfaWQiOiJVQlNyclRZSFlxWlBJallKOXpLMHd5M2ZjWWQyIiwic3ViIjoiVUJTcnJUWUhZcVpQSWpZSjl6SzB3eTNmY1lkMiIsImlhdCI6MTYzOTMzMTA0MywiZXhwIjoxNjM5MzM0NjQzLCJwaG9uZV9udW1iZXIiOiIrMjA1NTU1NTU1NTU1IiwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJwaG9uZSI6WyIrMjA1NTU1NTU1NTU1Il19LCJzaWduX2luX3Byb3ZpZGVyIjoicGhvbmUifX0.TragNGfFXwzgdR_N-ZcIFcrGxzuJD5AkP0lz0gY3fJhNod8ZD2xs2ITI4mJ05fUwYS3kPWp5XkfV5YoFaIVXkOgWQtJYL39EG5GwfRWxuSR4PdONuR1KvaiMYAGj9cFirRPnhp9jrvo6OOc8GoNcrT9QfP7u3w9iWIUk2hfSlHRamJ7X9boqU7DXZbpn7jH-7otfSTAu4w4zMyWZg1OaYecIjSQuhU1Vp78Hn60gmuNaHRpC0LKmH2opDSzDr6hdONP7ThcAQECkCOFMnoGCcr6XnMyJRZbQvRm7RQl3BhuBvJwA2DQe4_lVmjhPYVPNC2jKb9wMbd2B9yMEXpfyvg";

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