package onesource.shiftdigitalsolutions.authmodule.authentication.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientModel {
    @SerializedName("status")
    @Expose
    Integer statusCode;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("SSID")
    @Expose
    String ssid;


    public ClientModel(String ssid) {
        this.ssid = ssid;
    }

    public Integer getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }
}
