package com.hami.common.View;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hami.common.Util.UtilFonts;




public class ToastMessageBar {
    public static void show(Context context, String title) {
        Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        UtilFonts.overrideFonts(context, toastTV, UtilFonts.IRAN_SANS_BOLD);
        toastTV.setTextSize(16);
        toast.show();
    }

    //-----------------------------------------------
    public static void show(Context context, int resTitle) {
        Toast toast = Toast.makeText(context, resTitle, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        UtilFonts.overrideFonts(context, toastTV, UtilFonts.IRAN_SANS_BOLD);
        toastTV.setTextSize(16);
        toast.show();
    }

    //-----------------------------------------------
    public static void show(Context context, int resTitle, int fontSize) {
        Toast toast = Toast.makeText(context, resTitle, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        UtilFonts.overrideFonts(context, toastTV, UtilFonts.IRAN_SANS_BOLD);
        toastTV.setTextSize(fontSize);
        toast.show();
    }

    public static void showEngFont(Context context, String title) {
        Toast toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        UtilFonts.overrideFonts(context, toastTV, UtilFonts.TAHOMA);
        toastTV.setTextSize(16);
        toast.show();
    }

    //-----------------------------------------------
    public static void showEngFont(Context context, int resTitle) {
        Toast toast = Toast.makeText(context, resTitle, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        UtilFonts.overrideFonts(context, toastTV, UtilFonts.TAHOMA);
        toastTV.setTextSize(16);
        toast.show();
    }
}
