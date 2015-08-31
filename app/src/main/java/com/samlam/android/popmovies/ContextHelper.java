package com.samlam.android.popmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by slam on 8/29/2015.
 */
public class ContextHelper {
    public static Display getDisplay(Context ctx){
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public static Boolean IsStringNullOrEmpty(String value){
        return (value == null || value.isEmpty() || value.equals("null"));
    }

    public static boolean IsNetworkConnected(Context ctx) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
        }catch(Exception e){
            return false;
        }
    }
}
