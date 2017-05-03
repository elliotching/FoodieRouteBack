package unimas.fcsit.foodieroute;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by elliotching on 11-Apr-17.
 */

class AsyncCheckLogInStatus {

    Context context;
    AppCompatActivity activity;
    InterfaceCustomHTTP interfaceCustomHTTP;
    CustomHTTP httpAsyncCheck;
//    Dialog_Progress p;

    AsyncCheckLogInStatus(Context context) {
        this.context = context;
        this.activity = (AppCompatActivity) context;

//        this.p = p;

    }

    void start(){

        String deviceUUID = ResFR.getPrefString(context, ResFR.DEVICEUUID);
        String username = ResFR.getPrefString(context, ResFR.USERNAME);
        String device = ResFR.getPrefString(context, ResFR.DEVICE);
        String token = ResFR.getPrefString(context, ResFR.TOKEN);

        Log.d(this.getClass().getSimpleName(), "deviceUUID = "+deviceUUID);
        Log.d(this.getClass().getSimpleName(), "device = "+device);
        Log.d(this.getClass().getSimpleName(), "username = "+username);
        Log.d(this.getClass().getSimpleName(), "token = "+token);

        if(username.equals(ResFR.DEFAULT_EMPTY) ){

        } else {
            String[][] data = new String[][]{
                    {"pass", "!@#$"},
                    {"token", token},
                    {"deviceUUID", deviceUUID},
                    {"device", device},
                    {"username", username}
            };

            httpAsyncCheck = new CustomHTTP(context, data, ResFR.URL_check_log_in_status);
            httpAsyncCheck.ui = interfaceCustomHTTP;
            httpAsyncCheck.execute();
        }
    }
}
