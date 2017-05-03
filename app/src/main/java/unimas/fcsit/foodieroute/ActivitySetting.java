package unimas.fcsit.foodieroute;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by elliotching on 09-Mar-17.
 */

public class ActivitySetting extends MyCustomActivity {
    Context context = this;
    AppCompatActivity activity= this;
    RadioGroup radioGroup;
    RadioButton radioLight;
    RadioButton radioDark;
    Button buttonChangePassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createMyView(R.layout.activity_setting, R.id.toolbar_activity_setting);

        initUIsetup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            restartGoToActivityMain();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* RESTART THE ACTIVITY WITH REMOVE ALL TOP ACTIVITY */
    private void restartGoToActivityMain(){
        Intent intent = new Intent(context, Foodie_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        activity.finish();
        activity.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Build.VERSION.SDK_INT > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            restartGoToActivityMain();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initUIsetup(){


        buttonChangePassword = (Button) findViewById(R.id.button_change_password);
        radioGroup = (RadioGroup) findViewById(R.id.radiogrp_theme_activity_setting);
        radioLight = (RadioButton) findViewById(R.id.radiobutton_theme_light_activity_setting);
        radioDark = (RadioButton) findViewById(R.id.radiobutton_theme_dark_activity_setting);

        SharedPreferences pref = context.getSharedPreferences("FoodieRoute", Context.MODE_PRIVATE);
        String theme = pref.getString(ResFR.THEME,"Light");
        if(theme.equals("Dark")) radioDark.setChecked(true);

        radioLight.setOnClickListener(new OnClick());

        radioDark.setOnClickListener(new OnClick());

        buttonChangePassword.setOnClickListener(new OnClick());

    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {

            if(view == radioLight){

                ResFR.setPrefTheme(context, ResFR.THEME, ResFR.THEME_LIGHT_DEFAULT);

                Intent intent = new Intent(context, ActivitySetting.class);
                activity.startActivity(intent);
                activity.finish();
            }

            if(view == radioDark){

                ResFR.setPrefTheme(context, ResFR.THEME, ResFR.THEME_DARK);

                Intent intent = new Intent(context, ActivitySetting.class);
                activity.startActivity(intent);
                activity.finish();
            }

            if(view == buttonChangePassword){
                Intent intent = new Intent(context, ActivityChangePassword.class);
                activity.startActivity(intent);
            }
        }
    }
}
