package onesource.shiftdigitalsolutions.authmodule;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

//import androidx.multidex.MultiDex;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class UserCons extends Application {
    public String Token;
    public double Lat;
    public double Lon;
    public String Gps;
    public String CompressedImageURI1;
    public String CompressedImageURI2;
    public String CompressedImageURI3;
    public String CompressedImageURI4;
    public String QuickQR;

    public String PromoterName;
    public String PromoterPhone;
    public String Who;
    public String Version;
    public int BatteryPercentage;
    public String State;
    public String ShiftStatus;
    public long StartDateLong;
    public long EndDateLong;
    public  long LastDBUpdate;
    public static UserCons mContext;
    public  String DID;
    public  String DWho;
    private String MapproName;
    private String MappromoterPhone;
    private long MapstartTime;
    private long MapendTime;
    private String MaprequestedView;
    private boolean wadiORstand;
    public static final String CHANNEL_ID_1 = "LocationServiceChannel";
    public static final String CHANNEL_ID_2 = "ForegroundServiceChannel";

    public static UserCons getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
//        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
        mContext = this;


        createNotificationChannel();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel Channel_1 = new NotificationChannel(
                    CHANNEL_ID_1,
                    "Location Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            Channel_1.setDescription("Location Service Is Working...");
            NotificationChannel Channel_2 = new NotificationChannel(
                    CHANNEL_ID_2,
                    "Sending Data Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            Channel_2.setDescription("Sending Data Service Is Working...");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(Channel_1);
            manager.createNotificationChannel(Channel_2);

        }
    }
    public boolean isWadiORstand() {
        return wadiORstand;
    }

    public void setWadiORstand(boolean wadiORstand) {
        this.wadiORstand = wadiORstand;
    }

    public String getMapproName() {
        return MapproName;
    }

    public void setMapproName(String mapproName) {
        MapproName = mapproName;
    }

    public String getMappromoterPhone() {
        return MappromoterPhone;
    }

    public void setMappromoterPhone(String mappromoterPhone) {
        MappromoterPhone = mappromoterPhone;
    }

    public long getMapstartTime() {
        return MapstartTime;
    }

    public void setMapstartTime(long mapstartTime) {
        MapstartTime = mapstartTime;
    }

    public long getMapendTime() {
        return MapendTime;
    }

    public void setMapendTime(long mapendTime) {
        MapendTime = mapendTime;
    }

    public String getMaprequestedView() {
        return MaprequestedView;
    }

    public void setMaprequestedView(String maprequestedView) {
        MaprequestedView = maprequestedView;
    }

    public String getQuickQR() {
        return QuickQR;
    }

    public void setQuickQR(String quickQR) {
        QuickQR = quickQR;
    }

    public String getDWho() {
        return DWho;
    }

    public void setDWho(String DWho) {
        this.DWho = DWho;
    }

    public static UserCons getmContext() {
        return mContext;
    }

    public static void setmContext(UserCons mContext) {
        UserCons.mContext = mContext;
    }

    public String getDID() {
        return DID;
    }

    public void setDID(String DID) {
        this.DID = DID;
    }

    public long getLastDBUpdate() {
        return LastDBUpdate;
    }

    public void setLastDBUpdate(long lastDBUpdate) {
        LastDBUpdate = lastDBUpdate;
    }

    public long getStartDateLong() {
        return StartDateLong;
    }

    public void setStartDateLong(long startDateLong) {
        StartDateLong = startDateLong;
    }

    public long getEndDateLong() {
        return EndDateLong;
    }

    public void setEndDateLong(long endDateLong) {
        EndDateLong = endDateLong;
    }

    public String getShiftStatus() {
        return ShiftStatus;
    }

    public void setShiftStatus(String shiftStatus) {
        ShiftStatus = shiftStatus;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getGps() {
        return Gps;
    }

    public void setGps(String gps) {
        Gps = gps;
    }

    public int getBatteryPercentage() {
        return BatteryPercentage;
    }

    public void setBatteryPercentage(int batteryPercentage) {
        BatteryPercentage = batteryPercentage;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }

    public String getCompressedImageURI1() {
        return CompressedImageURI1;
    }

    public void setCompressedImageURI1(String compressedImageURI1) {
        CompressedImageURI1 = compressedImageURI1;
    }

    public String getCompressedImageURI2() {
        return CompressedImageURI2;
    }

    public void setCompressedImageURI2(String compressedImageURI2) {
        CompressedImageURI2 = compressedImageURI2;
    }

    public String getCompressedImageURI3() {
        return CompressedImageURI3;
    }

    public void setCompressedImageURI3(String compressedImageURI3) {
        CompressedImageURI3 = compressedImageURI3;
    }

    public String getCompressedImageURI4() {
        return CompressedImageURI4;
    }

    public void setCompressedImageURI4(String compressedImageURI4) {
        CompressedImageURI4 = compressedImageURI4;
    }

    public String getPromoterName() {
        return PromoterName;
    }

    public void setPromoterName(String promoterName) {
        PromoterName = promoterName;
    }

    public String getPromoterPhone() {
        return PromoterPhone;
    }

    public void setPromoterPhone(String promoterPhone) {
        PromoterPhone = promoterPhone;
    }

    public String getWho() {
        return Who;
    }

    public void setWho(String who) {
        Who = who;
    }
}



