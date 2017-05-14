package unimas.fcsit.foodieroute;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by elliotching on 03-Mar-17.
 */

public class ActivityMsgBroadcast extends MyCustomActivity {

//
//
//    Context context = this;
//    AppCompatActivity activity = this;
//    Button btn;
    private ListView listView;
    private AdapterTokenListView adapter;
//    ArrayList<ObjectToken> tokenss = new ArrayList<>();
//    /*used to count the number of message in */
//    int i = 0;

    private CustomHTTP httpGetAllToken;
    private Dialog_Progress progGetAllToken;

    private Listener listener;
    private Button buttonSend;
    private EditText editBroadcastMsg;

    private ArrayList<TokenListObject> arrayListToken = null;
    private CustomHTTP[] sendingTokenHttps = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMyView(R.layout.activity_broadcast, R.id.toolbar);
//        fetchAllUsersTokenss();
//        btn = (Button) findViewById(R.id.send_fcm);
//        listView = (ListView) findViewById(R.id.list_view);
//        listView.setOnItemClickListener(new OnClick());
//        btn.setOnClickListener(new OnClick());

        listView = (ListView) findViewById(R.id.list_view);
        buttonSend = (Button) findViewById(R.id.send_braodcast);
        editBroadcastMsg = (EditText) findViewById(R.id.edit_broadcastmsg);

        listener = new Listener();

        buttonSend.setOnClickListener(listener);

        fetchAllUsersTokenss();

    }

    private void fetchAllUsersTokenss() {
        if(progGetAllToken != null){
            progGetAllToken.dismiss();
        }
        progGetAllToken = new Dialog_Progress(activity, R.string.s_prgdialog_title_retrieve, R.string.s_prgdialog_msg_gettingalluserstoken, true);

        String[][] data = {
                {"pass", "!@#$"}
        };
        httpGetAllToken = new CustomHTTP(context, data, ResFR.URL_get_all_token);
        httpGetAllToken.ui = listener;
        httpGetAllToken.execute();
    }

    private class Listener implements InterfaceCustomHTTP, View.OnClickListener, AdapterView.OnItemClickListener {

        @Override
        public void onCompleted(String result) {
        }

        @Override
        public void onCompleted(String result, CustomHTTP http) {
            if(http == httpGetAllToken){
                progGetAllToken.dismiss();
                populateList(result);
            }
            if(sendingTokenHttps != null) {
                for (int i = 0; i < sendingTokenHttps.length; i++) {
                    if (http == sendingTokenHttps[i]) {
                        Log.d("result" + i, result);
                        break;
                    }
                }
            }
        }

        @Override
        public void onClick(View v) {
            if(v==buttonSend){
                Log.d("Send", "sending");
                startSend();
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    private void startSend() {


        if(arrayListToken != null) {
            int size = arrayListToken.size();
            sendingTokenHttps = new CustomHTTP[size];
            String username = ResFR.getPrefString(context, ResFR.USERNAME);
            String shopname = ResFR.getPrefString(context, ResFR.SELLER_NAME);
            String msgContent = editBroadcastMsg.getText().toString();
            for (int i = 0; i < sendingTokenHttps.length ; i ++) {

                String[][] data = new String[][]{
                        {"pass", "!@#$"},
                        {"token", arrayListToken.get(i).token},
                        {"data_body", "["+shopname+"]"+" "+msgContent},
                        {"username", username}
                };
                sendingTokenHttps[i] = new CustomHTTP(context, data, ResFR.URL_send_mesg);
                sendingTokenHttps[i].ui = listener;
            }
        }

        if(sendingTokenHttps != null && sendingTokenHttps.length >= 1) {
            Log.d("startSend", "startSend");
            for (int i = 0; i < sendingTokenHttps.length; i++) {
                sendingTokenHttps[i].execute();
                Log.d("startSend", "sendingTokenHttps["+i+"]");
            }
        }
//        if(sendingTokenHttps == null){
//            Log.d("startSend", "sendingTokenHttps == null");
//        }
//            Log.d("startSend", "sendingTokenHttps.length = " + sendingTokenHttps.length);
    }

    private void populateList(String s) {
        try {
            JSONArray jarray = new JSONArray(s);
            arrayListToken = new ArrayList<>();
            for(int i = 0 ; i < jarray.length() ; i ++){
                JSONObject json = jarray.getJSONObject(i);
                String username = json.optString("username", "");
                String token = json.optString("token", "");

                TokenListObject tokenObj = new TokenListObject(username, token);
                arrayListToken.add(tokenObj);
            }
            dumpInitListView();
        } catch (JSONException e) {
            e.printStackTrace();
            showDialogPhpError(s);
        }

    }

    private void initListOfTokenHttps() {

    }

    private void dumpInitListView() {
        if(arrayListToken != null) {
            adapter = new AdapterTokenListView(context, arrayListToken);
            listView.setAdapter(adapter);
        }
    }
}
