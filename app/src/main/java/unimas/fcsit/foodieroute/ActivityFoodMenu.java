package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
public class ActivityFoodMenu extends MyCustomActivity {

    private String TAG = this.getClass().getSimpleName();
    Context context = this;
    AppCompatActivity activity = this;
    ListView listView;
    AdapterFoodMenu adapter;

    private FusedLocationTracker locationTracker;

    private Dialog_Progress progGetAllFoods = null;

    private ArrayList<FoodListingObject> foodArray;

    private Listener allListener;
    private CustomHTTP httpGetAllMyFood;
//
//    private final static String SELLER = "get_seller_foods_only";
//    private final static String USER = "get_user_foods_only";
//    private final static String ALL = "get_all_foods";
    //    private LocationListener locationListener;
//    private ItemClicked adapterClickedListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMyView(R.layout.activity_food_menu, R.id.toolbar);

        changeMenu(false, true, false, false, false);
        listView = (ListView) findViewById(R.id.listview);

        allListener = new Listener();

        listView.setOnItemClickListener(allListener);
        String username = ResFR.getPrefString(context, ResFR.USERNAME);

        String[][] data = new String[][]{
                {"pass", "!@#$"},
                {"username", username}
        };
        httpGetAllMyFood = new CustomHTTP(context, data, ResFR.URL_get_my_food);

        startGetAllMyFood();
    }

    private void startGetAllMyFood() {
        if(progGetAllFoods != null){
            progGetAllFoods.dismiss();
        }
        progGetAllFoods = new Dialog_Progress(activity, R.string.s_prgdialog_title_loading, R.string.s_prgdialog_loading_all_food, true);

        httpGetAllMyFood.ui = allListener;
        httpGetAllMyFood.execute();
    }
//    void f(){
//
//        adapter = new AdapterFoodListingListViewElliot(context, createList());
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new ListViewOnClick());
//    }

    private class Listener implements  InterfaceCustomHTTP, AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FoodListingObject food = (FoodListingObject) adapter.getItem(position);
            ActivityFoodDetail.viewingFood = food;
            Intent i = new Intent(context, ActivityFoodDetail.class);
            startActivity(i);
        }

        @Override
        public void onCompleted(String result) {

        }

        @Override
        public void onCompleted(String result, CustomHTTP http) {
            progGetAllFoods.dismiss();

            Log.d("OnCompleteGetAllFood", result);

            if (http == httpGetAllMyFood) {
                onCompleteGetFoodDoArrayList(result);
            }
        }


    }

    private void onCompleteGetFoodDoArrayList(String result) {
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
                    food.distanceString = "";
                    foodArray.add(food);
                }

                populateList();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (isJSONObject(result)) {
            showDialogError(result);
        } else {
            showDialogError(result);
        }
    }

    private void showDialogError(String result) {
        new Dialog_AlertNotice(context, R.string.s_dialog_title_error, result).setPositiveKey(R.string.s_dialog_btn_ok, null);
    }
//
//    private void refreshList(Location location) {
//        Log.d(TAG, "List refreshing");
//
//        for(int i = 0; i < foodArray.size(); i++) {
//            updateFoodDistanceStringAndDouble(foodArray.get(i), location.getLatitude(), location.getLongitude());
//        }
//
//        listView.invalidateViews();
//    }

    private void populateList() {
        Log.d(TAG, "List populated. set-up.");
        adapter = new AdapterFoodMenu(context, foodArray);
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