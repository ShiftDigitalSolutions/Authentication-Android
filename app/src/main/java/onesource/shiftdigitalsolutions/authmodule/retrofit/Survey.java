package onesource.shiftdigitalsolutions.authmodule.retrofit;

import com.google.gson.annotations.SerializedName;
import java.util.HashMap;

public class Survey {

    String SSID;

    public Survey(String SSID) {
        this.SSID = SSID;
    }

    HashMap<String, Object> toHashMap() {
        HashMap<String, Object> request = new HashMap<>();
        request.put("SSID", this.SSID);
        return request;
    }
    @SerializedName("SSID")
    public String getSSID() {
        return SSID;
    }
    @SerializedName("SSID")
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

}
