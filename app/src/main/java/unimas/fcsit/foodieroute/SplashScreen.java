package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Locale;
import java.util.UUID;

import static unimas.fcsit.foodieroute.ResFR.DEVICE;
import static unimas.fcsit.foodieroute.ResFR.DEVICEUUID;

/**
 * Created by elliotching on 28-Jun-16.
 */
public class SplashScreen extends AppCompatActivity {

    Context context = this;
    TimerTask_FR timerTask;
    boolean tokenSuccess = false;

    public void onCreate(Bundle ins) {
        updateLang();

        super.onCreate(ins);

        String token = ResFR.getPrefString(context, ResFR.TOKEN);
        String deviceUUID = ResFR.getPrefString(context, ResFR.DEVICEUUID);
        String device = ResFR.getPrefString(context, ResFR.DEVICE);
        if(deviceUUID.equals(ResFR.DEFAULT_EMPTY) || device.equals(ResFR.DEFAULT_EMPTY)) {

            Log.d(this.getClass().getSimpleName(), "deviceUUID and device NOT FOUND!!!");

            String deviceModel = Build.MODEL;
            deviceUUID = deviceModel + "-" + UUID.randomUUID().toString();
            device = deviceModel;

            ResFR.setPrefString(context, DEVICEUUID, deviceUUID);
            ResFR.setPrefString(context, DEVICE, device);
        }
        getFirebaseToken(deviceUUID, device);
//        timerTask = new TimerTask_FR(10, new InterfaceMyTimerTask() {
//            @Override
//            public void doTask() {
//                getFirebaseToken(deviceUUID, device);
//            }
//
//            @Override
//            public void secondsPassed(int runningTime) {
//
//            }
//        });

//        finish();
//        startActivity(new Intent(context, ActivityLogIn.class));
    }

    void updateLang(){
        Resources res = context.getResources();
        String appLang = ResFR.getPrefString(context, ResFR.LANGUAGE);
// Change locale settings in the app.

        if(appLang.equals(ResFR.DEFAULT_EMPTY)) {
            appLang = Locale.getDefault().getLanguage();
            Log.d("lg", "updateLang: "+appLang);
        }
        Log.d("language", appLang);
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = (new Locale(appLang)); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }

    private void getFirebaseToken( String deviceUUID, String device) {

        if(deviceUUID.equals(ResFR.DEFAULT_EMPTY) || device.equals(ResFR.DEFAULT_EMPTY)) {

            Log.d(this.getClass().getSimpleName(), "deviceUUID and device NOT FOUND!!!");

            String deviceModel = Build.MODEL;
            deviceUUID = deviceModel + "-" + UUID.randomUUID().toString();
            device = deviceModel;

            ResFR.setPrefString(context, DEVICEUUID, deviceUUID);
            ResFR.setPrefString(context, DEVICE, device);
        }else{
            Log.d(this.getClass().getSimpleName(), "deviceUUID and device FOUND!!!");
        }

        if (FirebaseInstanceId.getInstance().getToken() == null) {
            // do nothing
            Log.d(this.getClass().getSimpleName(), "token not found. ");
            
            //editted 01.05.2019
    
            finish();
            startActivity(new Intent(context, ActivityLogIn.class));
        } else {
            String token = FirebaseInstanceId.getInstance().getToken();
            token = ResFR.getTokenFromFCM_JSON(token);
            ResFR.setPrefString(context, ResFR.TOKEN, token);
//            ResFR.setPrefString(context, ResFR.TOKEN, token);
            Log.d(this.getClass().getSimpleName(), "token get success. " + token);
            Log.d(this.getClass().getSimpleName(), "token shared preference saved success. " + ResFR.getPrefString(context, ResFR.TOKEN));

            timerTask.cancel();

            finish();
            startActivity(new Intent(context, ActivityLogIn.class));
        }
    }
}
