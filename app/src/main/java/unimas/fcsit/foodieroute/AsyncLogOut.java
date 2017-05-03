package unimas.fcsit.foodieroute;

import android.content.Context;

/**
 * Created by elliotching on 28-Apr-17.
 */

class AsyncLogOut {

    Context context;

    InterfaceCustomHTTP interfaceCustomHTTP;

    AsyncLogOut(Context context){
        this.context = context;
    }

    void executeThis(){
        String username = ResFR.getPrefString(context, ResFR.USERNAME);

        String[][] data = new String[][]{
                {"pass", "!@#$"},
                {"username", username}
        };

        CustomHTTP c = new CustomHTTP(context, data, ResFR.URL_log_out);
        c.ui = interfaceCustomHTTP;
        c.execute();
    }
}
