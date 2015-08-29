package com.samlam.android.popmovies;

import android.content.Context;
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
}
