package com.hami.common.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;



/**
 * Created by renjer on 1/22/2017.
 */

public class UtilFonts {
    public final static String IRAN_SANS_WEB = "iran_sans_web.ttf";
    public final static String IRAN_SANS_BOLD = "iransans_bold.ttf";
    public final static String IRAN_SANS_NORMAL = "iran_sans_normal.ttf";
    public final static String TAHOMA = "tahoma.ttf";
    public final static String BOOK = "iran_sans_web.ttf";


    //-----------------------------------------------
    public static void overrideFonts(final Context context, final View v, String font) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child, font);
                }
            } else if (v instanceof TextInputLayout) {
                TextInputLayout vg = (TextInputLayout) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child, font);
                }
            } else if (v instanceof EditText) {
                ((EditText) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + font));
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/" + font));
            }

        } catch (Exception e) {
        }
    }

    //-----------------------------------------------
    public static void overrideFonts(Context context, Snackbar mSnackBar, String fontString) {
        TextView tv = (TextView) (mSnackBar.getView()).findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontString);

        tv.setTypeface(font);
    }



}
