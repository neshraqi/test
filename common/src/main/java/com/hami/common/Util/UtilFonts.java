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

import com.hami.common.R;

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
    //-----------------------------------------------
    public static void applyFontTabRouting(Activity activity, TabLayout tabLayout) {
        View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
        TextView tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setText(R.string.capacity);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.rules);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));

        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.details);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        tabLayout.getTabAt(2).select();

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.twoWay));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.oneWay),true);

//        tv.setText(viewPager.getAdapter().getPageTitle(i));
//        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
//        tv.setTypeface(typeface);
//        tabLayout.getTabAt(0).setCustomView(view);

    }


    //-----------------------------------------------
    public static void applyFontTabPassenger(Activity activity, TabLayout tabLayout) {
        View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
        TextView tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setText(R.string.infant);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.children);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));

        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.adults);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        tabLayout.getTabAt(2).select();

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.twoWay));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.oneWay),true);

//        tv.setText(viewPager.getAdapter().getPageTitle(i));
//        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
//        tv.setTypeface(typeface);
//        tabLayout.getTabAt(0).setCustomView(view);

    }


    //-----------------------------------------------
    public static void applyFontTabServicesTour(Activity activity, TabLayout tabLayout) {
        int resTitle[] = {R.string.InternationalTour, R.string.domesticTour, R.string.dayTour, R.string.allTour};
        for (int i = 0; i < resTitle.length; i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            tv.setText(resTitle[i]);
            tv.setTypeface(typeface);
            tv.setTextSize(12);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tv));
        }

        tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
    }


    //-----------------------------------------------
    public static void applyFontTabServicesHotel(Activity activity, TabLayout tabLayout) {
        tabLayout.removeAllTabs();
        int resTitle[] = {R.string.internationalHotel, R.string.domesticHotel};
        for (int i = 0; i < resTitle.length; i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_filter, null);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            tv.setText(resTitle[i]);
            tv.setTypeface(typeface);
            tv.setTextSize(12);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tv));
        }
        tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();
    }

    //-----------------------------------------------
}
