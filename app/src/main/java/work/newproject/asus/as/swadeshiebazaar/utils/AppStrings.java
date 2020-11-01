package work.newproject.asus.as.swadeshiebazaar.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class AppStrings {

    public static String base_url = "http://swadeshiebazaar.com/app/App/";
    public static String imag_path = "http://swadeshiebazaar.com/uploads/";
    public static String cat_path = "http://swadeshiebazaar.com/";
    public static String userID= "userID";
    public static String child_base_url = "http://swadeshiebazaar.com/";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            return true;
        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            return false;
        }
        return false;

    }

}

