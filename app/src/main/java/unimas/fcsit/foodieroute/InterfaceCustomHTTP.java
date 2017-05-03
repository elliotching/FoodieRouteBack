package unimas.fcsit.foodieroute;

/**
 * Created by elliotching on 21-Mar-17.
 */

interface InterfaceCustomHTTP {
    void onCompleted(String result);
    void onCompleted(String result, CustomHTTP customHTTP);
}
