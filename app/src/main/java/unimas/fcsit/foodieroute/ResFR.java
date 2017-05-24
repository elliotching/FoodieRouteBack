package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by elliotching on 24-Feb-17.
 */

class ResFR {

    static final String currentVersion = "1.0.0.4";

    private final Context context;
    private static final String APP_IS_ACTIVE = "FR_app_is_active";
    static final String PREF_NAME = "FoodieRoute";
    static final String DEVICEUUID = "FR_deviceUUID";
    static final String LANGUAGE = "FR_lang";

    static final String ENGLISH = "en";
    static final String CHINESE = "zh";
    static final String KOREAN = "ko";
    static final String THEME = "FR_theme";
    static final String TOKEN = "FR_token";
    static final String USERNAME = "FR_username";
    static final String DEVICE = "FR_device";
    static final String EMAIL = "FR_email";
    static final String IS_SELLER = "FR_is_seller";
    private static final String SELLER_LOCATION_LAT = "FR_seller_location_lat";
    private static final String SELLER_LOCATION_LNG = "FR_seller_location_lng";
    static final String SELLER_IC_PHOTO = "FR_seller_ic_photo";
    static final String SELLER_DOC_1 = "FR_seller_doc_1";
    static final String SELLER_DOC_2 = "FR_seller_doc_2";
    static final String SELLER_NAME = "FR_seller_name";
    static final String FIREBASE_INSTANCE_ID = "FR_firebase_IID";
    static final String DEFAULT_EMPTY = "empty";
    static final String THEME_LIGHT_DEFAULT = "Light";
    static final String THEME_DARK = "Dark";
    static final float DEFAULT_EMPTY_LOCATION = 12123123123123123.0f;//Float.MAX_VALUE;
    static final float CHECK_EMPTY_LOCATION = 12123123123123.0f;;


    // USED IN ACTIVITY LOG IN ( WHENEVER RECEIVED MESSAGE OF "LOG OUT" = 1 )
    // OPEN THE ACTIVITY LOG IN AND IT WILL CHECK THE EXISTENT OF THIS KEY
    static final String BUNDLE_KEY_KICKED_OUT = "kicked_out";
    static final String BUNDLE_KEY_VIEW_MAP_ONLY = "view_map_only";
    static final String BUNDLE_KEY_MAP_LOCATION = "marker_location";
    static final String BUNDLE_KEY_VIEW_MY_FOOD_ONLY = "view_my_food_only";
    static final String BUNDLE_KEY_ADDING_FOOD_MENU = "adding_food_menu";

    // deprecated!!!
    static final String URL_get_token = "http://foodlocator.com.my/mobile/get_token.php";
    static final String URL_insert_token = "http://foodlocator.com.my/mobile/insert_token.php";

    // currently_active!!!
    static final String URL_send_mesg = "http://foodlocator.com.my/mobile/send_mesg.php";
    static final String URL_sign_up = "http://foodlocator.com.my/mobile/sign_up.php";
    static final String URL_log_in = "http://foodlocator.com.my/mobile/log_in.php";
    static final String URL_check_log_in_status = "http://foodlocator.com.my/mobile/check_log_in_status.php";
    static final String URL_on_token_refresh = "http://foodlocator.com.my/mobile/on_token_refresh.php";
    static final String URL_upload = "http://foodlocator.com.my/mobile/imgupload/upload.php";
    static final String URL_add_food = "http://foodlocator.com.my/mobile/add_food.php";
    static final String URL_read_image = "http://foodlocator.com.my/mobile/imgupload/read_image.php";
    static final String URL_read_small_image = "http://foodlocator.com.my/mobile/imgupload/read_small_image.php";
    static final String URL_get_all_food = "http://foodlocator.com.my/mobile/get_all_food.php";
    static final String URL_get_my_food = "http://foodlocator.com.my/mobile/get_my_food.php";
    static final String URL_check_version_update = "http://foodlocator.com.my/mobile/check_version_update.php";
    static final String URL_account_activation = "http://foodlocator.com.my/mobile/account_activation.php";
    static final String URL_change_password = "http://foodlocator.com.my/mobile/change_password.php";
    static final String URL_log_out = "http://foodlocator.com.my/mobile/log_out.php";
    static final String URL_update_seller_location = "http://foodlocator.com.my/mobile/update_seller_location.php";
    static final String URL_get_all_token = "http://foodlocator.com.my/mobile/get_all_token.php";
    static final String URL_search = "http://foodlocator.com.my/mobile/search.php";


    ResFR(Context c){
        context = c;
    }

    String string(int _R){
        return context.getResources().getString(_R);
    }

    Resources res(){
        return context.getResources();
    }

    int color(int _R){
        return ResourcesCompat.getColor(context.getResources(),_R,null);
    }

    static int dimenPx(Context context,int _d){
        return context.getResources().getDimensionPixelSize(_d);
    }

    static String string(Context context, int _R){
        return context.getResources().getString(_R);
    }

    static String getPrefString(Context context, String keyName){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        return pref.getString(keyName, DEFAULT_EMPTY);
    }

    static String getPrefTheme(Context context, String keyName){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        return pref.getString(keyName, THEME_LIGHT_DEFAULT);
    }

    static boolean getPrefIsAppRunning(Context context){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        return pref.getBoolean(APP_IS_ACTIVE , false);
    }

    static double[] getPrefLocation(Context context){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        double lat = pref.getFloat(SELLER_LOCATION_LAT , DEFAULT_EMPTY_LOCATION);
        double lng = pref.getFloat(SELLER_LOCATION_LNG , DEFAULT_EMPTY_LOCATION);
        return new double[]{lat, lng};
    }

    static String getPrefLocationLat(Context context){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        double lat = pref.getFloat(SELLER_LOCATION_LAT , DEFAULT_EMPTY_LOCATION);
        if(lat == DEFAULT_EMPTY_LOCATION){
            return DEFAULT_EMPTY;
        }else{
            return stringOf(lat);
        }
    }

    static String getPrefLocationLng(Context context){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        double lng = pref.getFloat(SELLER_LOCATION_LNG , DEFAULT_EMPTY_LOCATION);
        if(lng == DEFAULT_EMPTY_LOCATION){
            return DEFAULT_EMPTY;
        }else{
            return stringOf(lng);
        }
    }

    static void setPrefString(Context context, String keyName, String value){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit().putString(keyName, value);
        editor.commit();
    }

    static void setPrefTheme(Context context, String keyName, String value){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit().putString(keyName, value);
        editor.commit();
    }

    static void setPrefIsAppRunning(Context context, boolean value){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit().putBoolean( APP_IS_ACTIVE , value);
        editor.commit();
    }

    static void setPrefLocation(Context context, double[] loc){
        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat( SELLER_LOCATION_LAT , (float)loc[0]);
        editor.putFloat( SELLER_LOCATION_LNG , (float)loc[1]);
        editor.commit();
    }

    private static String stringOf(double value){
        return String.format("%.10f", value);
    }

    static double doubleOf(String s){
        try {
            return Double.valueOf(s);
        }catch(Exception e){
            return ResFR.DEFAULT_EMPTY_LOCATION;
        }
    }

}
