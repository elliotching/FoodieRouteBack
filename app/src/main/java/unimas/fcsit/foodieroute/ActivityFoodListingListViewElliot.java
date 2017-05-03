package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Elliot on 19-Aug-16.
 */
public class ActivityFoodListingListViewElliot extends MyCustomActivity {

    private String TAG = this.getClass().getSimpleName();
    Context context = this;
    AppCompatActivity activity = this;
    ListView mListView;
    ListView listView;
    Toolbar mToolbar;
    AdapterFoodListingListViewElliot adapter;

    private Dialog_Progress progGetAllFoods = null;

    private ArrayList<FoodListingObject> foodArray;

    private boolean locationStarted = false;
    private CustomHTTP httpGetAllFood;

    //    private LocationListener locationListener;
//    private ItemClicked adapterClickedListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMyView(R.layout.activity_food_listing_list_view, R.id.m_list_view_toolbar);

        changeMenu(false, true, false);
        listView = (ListView) findViewById(R.id.m_list_view);

        progGetAllFoods = new Dialog_Progress(activity, R.string.s_prgdialog_title_loading, R.string.s_prgdialog_loading_all_food, true);

        Listener allListener = new Listener();

        listView.setOnItemClickListener(allListener);

        FusedLocationTracker locationTracker = new FusedLocationTracker(context, allListener);

    }

//    void f(){
//
//        adapter = new AdapterFoodListingListViewElliot(context, createList());
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new ListViewOnClick());
//    }

    private class Listener implements FusedLocationDataInterface, InterfaceCustomHTTP, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FoodListingObject food = (FoodListingObject) adapter.getItem(position);
            Intent i = new Intent(context, ActivityFoodDetail.class);

        }

        @Override
        public void getFusedLocationData(Location location) {
            Log.d(TAG, "fusedLocationChanged.");

            AdapterFoodListingListViewElliot.myLocation = new double[]{location.getLatitude(), location.getLongitude()};

            if (!locationStarted) {
                locationStarted = true;


                String[][] data = new String[][]{
                        {"pass", "!@#$"}
                };
                httpGetAllFood = new CustomHTTP(context, data, ResFR.URL_get_all_food);
                httpGetAllFood.ui = this;
                httpGetAllFood.execute();
                Log.d(TAG, "HTTP request started");
            } else {
                refreshedList();
            }
        }

        @Override
        public void onCompleted(String result) {

        }

        @Override
        public void onCompleted(String result, CustomHTTP customHTTP) {
            progGetAllFoods.dismiss();

            Log.d("OnCompleteGetAllFood", result);

            if(customHTTP == httpGetAllFood) {
                if (isJSONArray(result)) {
                    try {

                        foodArray = new ArrayList<>();

                        JSONArray jsonArray = new JSONArray(result);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json = jsonArray.optJSONObject(i);
                            String date_time = json.optString("date_time");
                            String username = json.optString("username");
                            String image_file_name = json.optString("image_file_name");
                            String food_name = json.optString("food_name");
                            String food_price = json.optString("food_price");
                            String seller_location_lat = json.optString("seller_location_lat");
                            String seller_location_lng = json.optString("seller_location_lng");
                            String seller_name = json.optString("seller_name");
                            String is_seller = json.optString("is_seller");
                            String food_comment = json.optString("food_comment");

                            date_time = date_time.replace("----", "_");
                            String[] datetime = date_time.split("_");
                            String date = datetime[0];
                            String time = datetime[1];
                            date_time = date + " " + time;
                            FoodListingObject food = new FoodListingObject(date_time, username, image_file_name, food_name, food_price, seller_location_lat, seller_location_lng, seller_name, is_seller, food_comment);

                            foodArray.add(food);
                        }

                        refreshedList();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (isJSONObject(result)) {
                    showDialogError(result);
                } else {
                    showDialogError(result);
                }
            }
        }


    }

    private void showDialogError(String result) {
        new Dialog_AlertNotice(context, R.string.s_dialog_title_error, result).setPositiveKey(R.string.s_dialog_btn_ok, null);
    }

    private void refreshedList() {
        Log.d(TAG, "List refreshed.");
        adapter = new AdapterFoodListingListViewElliot(context, foodArray);
        listView.setAdapter(adapter);
    }

    boolean isJSONObject(String result) {
        try {
            JSONObject json = new JSONObject(result);
        } catch (JSONException e) {
            return false;
        }

        return true;
    }

    boolean isJSONArray(String result) {
        try {
            JSONArray json = new JSONArray(result);
        } catch (JSONException e) {
            return false;
        }

        return true;
    }
}

//        listView.setLayoutManager(new LinearLayoutManager(context));

//        listView.setOnScrollListener(new HidingScrollListener(context) {
//            @Override
//            public void onMoved(int distance) {
//                mToolbar.setTranslationY(-distance);
//            }
//
//            @Override
//            public void onShow() {
//
//            }
//
//            @Override
//            public void onHide() {
//
//            }
//        });