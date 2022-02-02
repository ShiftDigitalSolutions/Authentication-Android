package onesource.shiftdigitalsolutions.authmodule.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationEntity {
    @SerializedName("status")
    @Expose
    Integer statusCode;
    @SerializedName("message")
    @Expose
    String message;


    public Integer getStatusCode() {
        return statusCode;
    }


    public String getMessage() {
        return message;
    }

}
