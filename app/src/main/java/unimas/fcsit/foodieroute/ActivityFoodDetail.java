package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

/**
 * Created by elliotching on 08-Mar-17.
 */

public class ActivityFoodDetail extends MyCustomActivity {

    Context context = this;
    AppCompatActivity activity = this;
    TextView textFoodName;
    ImageView imageViewFoodImage;
    TextView textFoodComment;
    TextView textFoodPosterUsername;
    TextView textFoodDistanceString;
    TextView textFoodSeller;
    TextView textFoodPrice;
    Button buttonlocateme;
    Button buttongetdirection;
    private FusedLocationTracker locationTracker;
    private Listener listener;
    static FoodListingObject viewingFood;
    private final String URL_READ_IMAGE = ResFR.URL_read_image + "?image_name=";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createMyView(R.layout.activity_food_detail, R.id.toolbar);

        changeMenu(false, true, false);

        listener = new Listener();

        locationTracker = new FusedLocationTracker(context, listener);
        textFoodName = (TextView) findViewById(R.id.text_fooddetail_name);
        textFoodPrice = (TextView) findViewById(R.id.text_fooddetail_price);
        imageViewFoodImage = (ImageView) findViewById(R.id.image_fooddetail_image);
        textFoodComment = (TextView) findViewById(R.id.text_fooddetail_comment);
        textFoodDistanceString = (TextView) findViewById(R.id.text_fooddetail_distance);
        textFoodPosterUsername = (TextView) findViewById(R.id.text_fooddetail_poster);
        textFoodSeller = (TextView) findViewById(R.id.text_fooddetail_seller);
        buttonlocateme = (Button)findViewById(R.id.button_fooddetail_locateme);
        buttongetdirection = (Button)findViewById(R.id.button_fooddetail_getdirection);

        buttonlocateme.setOnClickListener(listener);
        buttongetdirection.setOnClickListener(listener);
        /* Adjust Image Viewport Size */
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageViewFoodImage.getLayoutParams();
        Log.d("AdapterImageView", "LinearLayout.LayoutParams params.width = "+params.width);
        int screenWidth = Screen.getWidth(context);
        Log.d("AdapterImageView", "screenWidth = "+screenWidth);
        int pixelOf20dp = Screen.getPixels(context, 20.0f);
        Log.d("AdapterImageView", "pixelOf20dp = "+pixelOf20dp);
        int marginLR = pixelOf20dp * 2;
        Log.d("AdapterImageView", "marginLR = "+marginLR);
        int maxImageWidth = screenWidth - marginLR;
        Log.d("AdapterImageView", "maxImageWidth = "+maxImageWidth);

//        int imageViewNewWidth = maxImageWidth / 2;
        if(maxImageWidth > 1280){
            Log.d("AdapterImageView", "maxImageWidth > 1280 TRUE");
            maxImageWidth = 1280;
            params.width = 1280;
        }
        int imageViewNewHeight = maxImageWidth / 4 * 3;

        params.height = imageViewNewHeight;
        imageViewFoodImage.setLayoutParams(params);

        Ion.with(context)
                .load(URL_READ_IMAGE+viewingFood.image_file_name)
                .withBitmap()
                .placeholder(R.mipmap.ic_loading)
                .intoImageView(imageViewFoodImage);

        textFoodName.setText(viewingFood.food_name);

        /* Price */
        String price = ResFR.string(context, R.string.s_listview_price);
        double priceD = ResFR.doubleOf(viewingFood.food_price);
        String priceDS = String.format("%.2f",priceD);
        price = price.replace("$money$", priceDS);
        textFoodPrice.setText(price);

        /* Seller */
        textFoodSeller.setText(viewingFood.seller_name);

        if(viewingFood.is_seller.equals("1") || viewingFood.is_seller.equals("2")) {

        }else if(viewingFood.is_seller.equals("0")){
            /* Comment: */
            String comment = ResFR.string(context, R.string.s_listview_comment);
            comment = comment + " " + viewingFood.food_comment;
            textFoodComment.setText(comment);

            /* username who posted/shared this food */
            String poster = ResFR.string(context, R.string.s_listview_username);
            poster = poster.replace("$user$", viewingFood.username);
            textFoodPosterUsername.setText(poster);
        }

        textFoodDistanceString.setText(viewingFood.distanceString);
    }

    private class Listener implements FusedLocationDataInterface, View.OnClickListener{

        @Override
        public void getFusedLocationData(Location location) {
            updateFoodDistanceStringAndDouble(viewingFood, location.getLatitude(), location.getLongitude());
            textFoodDistanceString.setText(viewingFood.distanceString);
        }

        @Override
        public void onClick(View v) {
            if(v==buttonlocateme){
                if(!isLocationEmpty(new double[]{viewingFood.lat, viewingFood.lng}))
                gotoActivityMap();
            }
            if(v==buttongetdirection){
                if(!isLocationEmpty(new double[]{viewingFood.lat, viewingFood.lng}))
                getDirectionInGoogleMap();
            }
        }
    }

    private void getDirectionInGoogleMap() {

        // Original
//        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" +
//                "saddr="+ viewingFood.lat + "," + viewingFood.lng + "&daddr=" + marker.getPosition().latitude + "," +
//                marker.getPosition().longitude+ "&sensor=false&units=metric&mode=driving"));

        final Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?" +
                "daddr=" + viewingFood.lat + "," + viewingFood.lng +
                "&sensor=false" +
                        "&units=metric" +
                        "&mode=driving"));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    private void gotoActivityMap() {
        Intent i = new Intent(context, ActivityMaps.class);
        i.putExtra(ResFR.BUNDLE_KEY_VIEW_MAP_ONLY, true);
        i.putExtra(ResFR.BUNDLE_KEY_MAP_LOCATION, new double[]{viewingFood.lat, viewingFood.lng});
        startActivity(i);
    }

    void backButtonPressed(){
        locationTracker.stopLocationUpdates();
        super.backButtonPressed();
    }
}
