package onesource.shiftdigitalsolutions.layers.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationUsingSqlEntity {
    @SerializedName("status")
    @Expose
    Integer statusCode;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("SSID")
    @Expose
    String ssid;


    public AuthenticationUsingSqlEntity(String ssid) {
        this.ssid = ssid;
    }

    public Integer getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }
}
