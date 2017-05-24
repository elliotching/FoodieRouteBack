package unimas.fcsit.foodieroute;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by elliotching on 08-May-17.
 */

public class ActivityMain extends MyCustomActivity {

    Button buttonsellerupdatelocation;
    Button buttonselleraddfood;
    Button buttonusersharefood;
    Button buttonsellerviewmyfoodmenu;
    Button buttonallviewlistfood;
    Button buttonbroadcast;
    Button buttonwipelocation;
    TextView textLocationInfo;

    private Listener listener;
    private FusedLocationTracker locationTracker;
    private CustomHTTP httpUpdateSellerLocation;
    private String sellerType;
    private double[] mycurrentlocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMyView(R.layout.activity_main, R.id.toolbar);
        changeMenu(true, true, true, true, false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        buttonselleraddfood = (Button) findViewById(R.id.button_addfood);
        buttonsellerupdatelocation = (Button) findViewById(R.id.button_updatelocation);
        buttonusersharefood = (Button) findViewById(R.id.button_sharefooduser);
        buttonsellerviewmyfoodmenu = (Button) findViewById(R.id.button_viewmyfoodmenu);
        buttonallviewlistfood = (Button) findViewById(R.id.button_viewfoodlist);
        buttonbroadcast = (Button) findViewById(R.id.button_broadcast);
        buttonwipelocation = (Button) findViewById(R.id.button_wipeloc);
        textLocationInfo = (TextView) findViewById(R.id.text_showlocation);

        listener = new Listener();
        buttonsellerupdatelocation.setOnClickListener(listener);
        buttonselleraddfood.setOnClickListener(listener);
        buttonusersharefood.setOnClickListener(listener);
        buttonsellerviewmyfoodmenu.setOnClickListener(listener);
        buttonallviewlistfood.setOnClickListener(listener);
        buttonbroadcast.setOnClickListener(listener);
        buttonwipelocation.setOnClickListener(listener);

        /* get my current location */
        mycurrentlocation = ResFR.getPrefLocation(context);
        sellerType = ResFR.getPrefString(context, ResFR.IS_SELLER);
        // check is my current location is empty
        if (sellerType.equals("1")) {
            hideButtonsForSeller();
        } else if (sellerType.equals("2")) {
            hideButtonsForMobile();
            if (isLocationEmpty(mycurrentlocation)) {
                showDialogYourLocationIsEmpty();
            }
        } else if (sellerType.equals("0")) {
            buttonsellerviewmyfoodmenu.setText(R.string.s_button_viewfoodsishared);
            hideButtonsForUser();
        }
    }

    private void showDialogYourLocationIsEmpty() {
        new Dialog_AlertNotice(context, R.string.s_dialog_title_warning, R.string.s_dialog_msg_yoursellerlocationempty).setPositiveKey(R.string.s_dialog_btn_ok, null);
    }

    private void hideButtonsForUser() {
        buttonselleraddfood.setVisibility(View.GONE);
        buttonsellerupdatelocation.setVisibility(View.GONE);
        buttonbroadcast.setVisibility(View.GONE);
        buttonwipelocation.setVisibility(View.GONE);
    }

    private void hideButtonsForSeller() {
        buttonusersharefood.setVisibility(View.GONE);
        buttonsellerupdatelocation.setVisibility(View.GONE);
        buttonwipelocation.setVisibility(View.GONE);
    }

    private void hideButtonsForMobile() {
        buttonusersharefood.setVisibility(View.GONE);
    }

    private void startUpdateLocation() {
        locationTracker = new FusedLocationTracker(context, listener);
        textLocationInfo.setText(R.string.s_text_gettingyourlocation);
    }

    private void onLocationReceivedDoHttpSave(Location location) {
        locationTracker.stopLocationUpdates();
        String lat = stringOf(location.getLatitude());
        String lng = stringOf(location.getLongitude());
        String savingyourlocation = ResFR.string(context, R.string.s_text_savingyourlocation);
        textLocationInfo.setText(savingyourlocation + " " + lat + " , " + lng);

        String username = ResFR.getPrefString(context, ResFR.USERNAME);

        String[][] data = new String[][]{
                {"pass", "!@#$"},
                {"username", username},
                {"seller_location_lat", lat},
                {"seller_location_lng", lng}
        };

        httpUpdateSellerLocation = new CustomHTTP(context, data, ResFR.URL_update_seller_location);
        httpUpdateSellerLocation.ui = listener;
        httpUpdateSellerLocation.execute();
    }

    private void deleteLocation(){
        String username = ResFR.getPrefString(context, ResFR.USERNAME);

        String[][] data = new String[][]{
                {"pass", "!@#$"},
                {"username", username},
                {"seller_location_lat", ""},
                {"seller_location_lng", ""}
        };

        httpUpdateSellerLocation = new CustomHTTP(context, data, ResFR.URL_update_seller_location);
        httpUpdateSellerLocation.ui = listener;
        httpUpdateSellerLocation.execute();
    }

    private void onHttpUpdateLocationResult(String result) {
        try {
            JSONObject json = new JSONObject(result);
            String success = json.optString("success");
            String latS = json.optString("lat");
            String lngS = json.optString("lng");
            if (success.equals("1")) {
                if(latS.equals("") || lngS.equals("")){
                    textLocationInfo.setText(R.string.s_deletelocationsuccess);
                    double emptyLocation = ResFR.DEFAULT_EMPTY_LOCATION;
                    ResFR.setPrefLocation(context, new double[]{emptyLocation, emptyLocation});
                }else {
                    textLocationInfo.setText(R.string.s_dialog_title_success);
                    double lat = doubleOf(latS);
                    double lng = doubleOf(lngS);
                    ResFR.setPrefLocation(context, new double[]{lat, lng});
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            showDialogPhpError(result);
        }
    }

    private void checkLocationAndAddFood() {

        if (sellerType.equals("1")) {
            if (isLocationEmpty(mycurrentlocation)) {
                showDialogYourLocationIsEmpty();
            }
        } else if (sellerType.equals("2")) {
            if (isLocationEmpty(mycurrentlocation)) {
                showDialogYourLocationIsEmpty();
            }
        }
        Intent i = new Intent(context, ActivityAddFood.class);
        startActivity(i);
    }


    private void postShareFood() {
        Intent i = new Intent(context, ActivityAddFood.class);
        startActivity(i);
    }

    private void viewAllFood() {
        Intent i = new Intent(context, ActivityFoodListingListViewElliot.class);
        startActivity(i);
    }

    private void viewMyFood() {
        Intent i = new Intent(context, ActivityFoodMenu.class);
        startActivity(i);
    }

    private void gotoBroadcastPage() {
        Intent i = new Intent(context, ActivityMsgBroadcast.class);
        startActivity(i);
    }

    private class Listener implements View.OnClickListener, FusedLocationDataInterface, InterfaceCustomHTTP {

        @Override
        public void onClick(View v) {
            if (v == buttonselleraddfood) {
                checkLocationAndAddFood();
            }
            if (v == buttonsellerupdatelocation) {
                startUpdateLocation();
            }
            if (v == buttonusersharefood) {
                postShareFood();
            }
            if (v == buttonsellerviewmyfoodmenu) {
                viewMyFood();
            }
            if (v == buttonallviewlistfood) {
                viewAllFood();
            }
            if (v == buttonbroadcast) {
                gotoBroadcastPage();
            }
            if( v == buttonwipelocation){
                deleteLocation();
            }
//            Button buttonsellerupdatelocation;
//            Button buttonselleraddfood;
//            Button buttonusersharefood;
//            Button buttonsellerviewmyfoodmenu;
//            Button buttonallviewlistfood;
//            Button buttonsellersharefood;
        }

        @Override
        public void getFusedLocationData(Location location) {
            onLocationReceivedDoHttpSave(location);
        }

        @Override
        public void onCompleted(String result) {

        }

        @Override
        public void onCompleted(String result, CustomHTTP http) {
            if (http == httpUpdateSellerLocation) {
                onHttpUpdateLocationResult(result);
            }
        }
    }

}
