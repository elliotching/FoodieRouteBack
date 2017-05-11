//package unimas.fcsit.foodieroute;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
///**
// * Created by elliotching on 03-Mar-17.
// */
//
//public class ActivityMsgBroadcast extends MyCustomActivity {
//
////
////
////    Context context = this;
////    AppCompatActivity activity = this;
////    Button btn;
////    ListView listView;
////    ArrayList<ObjectToken> tokenss = new ArrayList<>();
////    /*used to count the number of message in */
////    int i = 0;
//
//    private CustomHTTP httpGetAllToken;
//    private Dialog_Progress progGetAllToken;
//
//    private Listener listener;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        createMyView(R.layout.activity_broadcast, R.id.toolbar);
////        fetchAllUsersTokenss();
////        btn = (Button) findViewById(R.id.send_fcm);
////        listView = (ListView) findViewById(R.id.list_view);
////        listView.setOnItemClickListener(new OnClick());
////        btn.setOnClickListener(new OnClick());
////
//
//        listener = new Listener();
//
//        fetchAllUsersTokenss();
//
//
//
//    }
//
//    private void fetchAllUsersTokenss() {
//        progGetAllToken = new Dialog_Progress(activity, R.string.s_prgdialog_title_retrieve, R.string.s_prgdialog_msg_gettingalluserstoken, true);
//
//        String[][] data = {
//                {"pass", "!@#$"}
//        };
//        httpGetAllToken = new CustomHTTP(context, data, ResFR.URL_get_all_token);
//        httpGetAllToken.ui = listener;
//        httpGetAllToken.execute();
//    }
//
//    private class Listener implements InterfaceCustomHTTP, View.OnClickListener, AdapterView.OnItemClickListener {
//
//        @Override
//        public void onCompleted(String result) {
//        }
//
//        @Override
//        public void onCompleted(String result, CustomHTTP http) {
//            if(http == httpGetAllToken){
//                progGetAllToken.dismiss();
//
//            }
//        }
//
//        @Override
//        public void onClick(View v) {
//
//        }
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            adapterView, View view,int i, long l){
//                ObjectToken objectToken = (ObjectToken) adapterView.getItemAtPosition(i);
//                String selected_token = objectToken.token;
//                sendMsg(data_body(selected_token));
//            }
//        }
//
//    }
//}
