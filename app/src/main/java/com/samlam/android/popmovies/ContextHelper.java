package com.samlam.android.popmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by slam on 8/29/2015.
 */
public class ContextHelper {
    public static final String SORTING_KEY = "SORTBY";
    private static SharedPreferences _sharedPref;
    private static SharedPreferences.Editor _editor;

    public static Display GetDisplay(Context ctx){
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

    public static String GetApplicationName(Context ctx){
        return ctx.getString( ctx.getApplicationInfo().labelRes);
    }

    public static SharedPreferences GetSharedPreference(Context ctx){
        if (_sharedPref ==null){
            _sharedPref = ctx.getSharedPreferences(GetApplicationName(ctx), Context.MODE_MULTI_PROCESS);
        }
        return _sharedPref;
    }

    public static SharedPreferences GetDefaultSharedPreference(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static SharedPreferences.Editor GetEditor(Context ctx){
        if (_editor == null) {
            _editor = GetSharedPreference(ctx).edit();
        }
        return _editor;
    }
}
