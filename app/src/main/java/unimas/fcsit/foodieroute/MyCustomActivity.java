package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.location.LocationManager.GPS_PROVIDER;
import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by elliotching on 16-Mar-17.
 */

class MyCustomActivity extends AppCompatActivity {

//    public static boolean mIsInForegroundMode = false;
    String themeSetting;

    AppCompatActivity activity = this;
    Context context = this;

    protected void createMyView(int contentViewResID, int toolbarResID) {
        ResFR.setPrefIsAppRunning(this, true);
        debugInfo();

        checkLocationPermission();

        themeSetting = ResFR.getPrefTheme(this, ResFR.THEME);

        if(themeSetting.equals("Dark")){
            this.setTheme(R.style.AppThemeDark);
        }

        this.setContentView(contentViewResID);
        Toolbar toolbar = (Toolbar) this.findViewById(toolbarResID);
        if(themeSetting.equals("Dark"))toolbar.setPopupTheme(R.style.app_bar_white_text_dark_elliot);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void debugInfo() {
        String deviceUUID = ResFR.getPrefString(context, ResFR.DEVICEUUID);
        String username = ResFR.getPrefString(context, ResFR.USERNAME);
        String email = ResFR.getPrefString(context, ResFR.EMAIL);
        String device = ResFR.getPrefString(context, ResFR.DEVICE);
        String token = ResFR.getPrefString(context, ResFR.TOKEN);

        String is_seller = ResFR.getPrefString(context, ResFR.IS_SELLER);
        String seller_name = ResFR.getPrefString(context, ResFR.SELLER_NAME);
        String seller_doc_1 = ResFR.getPrefString(context, ResFR.SELLER_DOC_1);
        String seller_doc_2 = ResFR.getPrefString(context, ResFR.SELLER_DOC_2);
        String seller_ic_photo = ResFR.getPrefString(context, ResFR.SELLER_IC_PHOTO);

        double[] loc = ResFR.getPrefLocation(context);
        double lat = loc[0];
        double lng = loc[1];

        Log.d(this.getClass().getSimpleName(), "deviceUUID = "+deviceUUID);
        Log.d(this.getClass().getSimpleName(), "device = "+device);
        Log.d(this.getClass().getSimpleName(), "username = "+username);
        Log.d(this.getClass().getSimpleName(), "email = "+email);
        Log.d(this.getClass().getSimpleName(), "token = "+token);
        Log.d(this.getClass().getSimpleName(), "is_seller = "+is_seller);
        Log.d(this.getClass().getSimpleName(), "seller_name = "+seller_name);
        Log.d(this.getClass().getSimpleName(), "seller_doc_1 = "+seller_doc_1);
        Log.d(this.getClass().getSimpleName(), "seller_doc_2 = "+seller_doc_2);
        Log.d(this.getClass().getSimpleName(), "seller_ic_photo = "+seller_ic_photo);
        Log.d(this.getClass().getSimpleName(), "location (lat,lng) = "+lat+" , "+lng);
        Log.d(this.getClass().getSimpleName(), "Double.NEGATIVE_INFINITY = "+Double.NEGATIVE_INFINITY);
        Log.d(this.getClass().getSimpleName(), "Double.POSITIVE_INFINITY = "+Double.POSITIVE_INFINITY);
        Log.d(this.getClass().getSimpleName(), "Double.MIN_VALUE = "+Double.MIN_VALUE);
    }


    // THIS METHOD ONLY USED IN FOODIE_MAIN !!!
    protected String getMyTheme(){
        return themeSetting;
    }

    @Override
    protected void onPause() {
        super.onPause();

        boolean isActive = false;
        ResFR.setPrefIsAppRunning(this, isActive);

        Log.d("AppIsActive", String.valueOf(isActive));
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isActive = true;
        ResFR.setPrefIsAppRunning(this, isActive);

        Log.d("AppIsActive", String.valueOf(isActive));
    }




    private boolean showAddFood = false;
    private boolean showSettings = false;
    private boolean showUsername = false;
    private boolean showLogout = false;

    private MenuItem menuAddFood;
    private MenuItem menuSettings;
    private MenuItem menuUsername;
    private MenuItem menuLogout;

    private void createMenu(Context context, Menu menu){
        menuAddFood = menu.findItem(R.id.add_food);

        menuSettings = menu.findItem(R.id.setting);

        menuUsername = menu.findItem(R.id.username);
        String username = menuUsername.getTitle().toString();
        username += ResFR.getPrefString(context, ResFR.USERNAME);
        menuUsername.setTitle(username);

        menuLogout = menu.findItem(R.id.log_out);
    }

    protected void changeMenu(boolean showAddFood, boolean showUsername, boolean showSettings){
        this.showAddFood = showAddFood;
        this.showUsername = showUsername;
        this.showSettings = showSettings;
        invalidateOptionsMenu();
    }
    protected void changeMenu(boolean showAddFood, boolean showUsername, boolean showSettings, boolean logout){
        this.showAddFood = showAddFood;
        this.showUsername = showUsername;
        this.showSettings = showSettings;
        this.showLogout = logout;
        invalidateOptionsMenu();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    /*    OPTIONS MENU    */
    /*    OPTIONS MENU    */
    /*    OPTIONS MENU    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        createMenu(this, menu);
        return true;
    }

    // CALL "invalidateOptionsMenu()" TO FIRE THIS METHOD!!
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuAddFood.setVisible(showAddFood);
        menuUsername.setVisible(showUsername);
        menuSettings.setVisible(showSettings);
        menuLogout.setVisible(showLogout);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item == menuAddFood) {
            Intent intent = new Intent(this, ActivityAddFood.class);
            this.startActivity(intent);
            return true;
        }
        if(item == menuUsername){
            return true;
        }
        if (item == menuSettings){
            Intent intent = new Intent(this, ActivitySetting.class);
            this.startActivity(intent);
            return true;
        }
        if(item == menuLogout){
            doLogout();
            return true;
        }
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*    OPTIONS MENU    */
    /*    OPTIONS MENU    */
    /*    OPTIONS MENU    */









    void doLogout(){
        final Dialog_Progress p = new Dialog_Progress(activity, R.string.s_prgdialog_title_log_out, R.string.s_prgdialog_log_out, false);

        AsyncLogOut logOut = new AsyncLogOut(context);
        logOut.interfaceCustomHTTP = new InterfaceCustomHTTP() {
            @Override
            public void onCompleted(String result) {
                p.dismiss();

                try {
                    JSONObject json = new JSONObject(result);
                    String success = json.optString("success");
                    String log_out = json.optString("log_out");
                    String username_json = json.optString("username");

                    String username = ResFR.getPrefString(context, ResFR.USERNAME);

                    if(log_out.equals("1") && success.equals("1")){
                        ResFR.setPrefString(context, ResFR.USERNAME, ResFR.DEFAULT_EMPTY);
                        ResFR.setPrefString(context, ResFR.EMAIL, ResFR.DEFAULT_EMPTY);

                        ResFR.setPrefString(context, ResFR.SELLER_NAME, ResFR.DEFAULT_EMPTY);
                        ResFR.setPrefString(context, ResFR.SELLER_DOC_1, ResFR.DEFAULT_EMPTY);
                        ResFR.setPrefString(context, ResFR.SELLER_DOC_2, ResFR.DEFAULT_EMPTY);
                        ResFR.setPrefString(context, ResFR.SELLER_IC_PHOTO, ResFR.DEFAULT_EMPTY);
                        ResFR.setPrefLocation(context, new double[]{ResFR.DEFAULT_EMPTY_LOCATION , ResFR.DEFAULT_EMPTY_LOCATION});
                        restartAtLogIn();
                    }
                    else{
                        displayError(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    displayError(result);
                }
            }

            @Override
            public void onCompleted(String result, CustomHTTP customHTTP) {

            }
        };
        logOut.executeThis();
    }

    void restartAtLogIn() {
        Intent i = new Intent(context, ActivityLogIn.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        activity.finish();
        activity.startActivity(i);
    }

    void displayError(String error){
        new Dialog_AlertNotice(context, R.string.s_dialog_title_error, error)
                .setPositiveKey(R.string.s_dialog_btn_ok, null);
    }


    public static final int FR_PERMISSIONS_REQUEST_CODE_LOCATION = 99;

    void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, CHANGE_WIFI_STATE) != PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, ACCESS_WIFI_STATE) != PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[]{ACCESS_FINE_LOCATION, CHANGE_WIFI_STATE, ACCESS_WIFI_STATE, ACCESS_COARSE_LOCATION}, FR_PERMISSIONS_REQUEST_CODE_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FR_PERMISSIONS_REQUEST_CODE_LOCATION: {
                for (int grantResult : grantResults) {
                    if (grantResult == PERMISSION_GRANTED) {

                    } else {
                        this.finish();
                        break;
                    }
                }
//                if (allGranted) {
//                    if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
//                        if (fusedLocationTracker.mGoogleApiClient == null) {
//                            fusedLocationTracker.buildGoogleApiClient();
//                        }
//                        googleMap.setMyLocationEnabled(true);
//                    }
//                } else {
//                    // EXIT MAP!!!!!!
//                    this.onPause();
//                }
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    boolean checkLocationPermission_v22() {
        LocationManager locManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (locManager.isProviderEnabled(GPS_PROVIDER) ||
                locManager.isProviderEnabled(NETWORK_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    void makeToast(String result){
        Toast.makeText(this, "FoodieRoute: "+result, Toast.LENGTH_LONG).show();
    }

    String stringOf(double value){
        return String.format("%.10f", value);
    }
}


