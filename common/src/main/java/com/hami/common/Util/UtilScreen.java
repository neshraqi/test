package com.hami.common.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by renjer on 2018-03-04.
 */

public class UtilScreen {
    private Context ctx;
    private DisplayMetrics metrics;

    public UtilScreen(Context ctx) {
        this.ctx = ctx;
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        metrics = new DisplayMetrics();
        display.getMetrics(metrics);
    }

    //-----------------------------------------------
    public int getHeight() {
        return metrics.heightPixels;
    }

    //-----------------------------------------------
    public int getWidth() {
        return metrics.widthPixels;
    }

    public int getRealHeight() {
        return metrics.heightPixels / metrics.densityDpi;
    }

    //-----------------------------------------------
    public int getRealWidth() {
        return metrics.widthPixels / metrics.densityDpi;
    }

    //-----------------------------------------------
    public int getDensity() {
        return metrics.densityDpi;
    }

    //-----------------------------------------------
    public int getScale(int picWidth) {
        Display display
                = ((WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width) / new Double(picWidth);
        val = val * 100d;
        return val.intValue();
    }
}
