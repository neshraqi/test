package hami.aniticket.Util;

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

import hami.aniticket.R;

/**
 * Created by renjer on 1/22/2017.
 */

public class UtilFonts {
    public final static String IRAN_SANS_WEB = "iran_sans_web.ttf";
    public final static String IRAN_SANS_BOLD = "iransans_bold.ttf";
    public final static String IRAN_SANS_NORMAL = "iran_sans_normal.ttf";
    public final static String TAHOMA = "tahoma.ttf";
    private static int[] tabIcons = {
            R.mipmap.ic_bus,
            R.mipmap.ic_bus,
            R.mipmap.ic_train,
            R.mipmap.ic_airplan_top
    };

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
    public static void applyFontTabAndViewPager(Activity activity, ViewPager viewPager, TabLayout tabLayout) {

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            ImageView imgTab = (ImageView) view.findViewById(R.id.imgTab);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            imgTab.setImageResource(tabIcons[i]);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
            tv.setTypeface(typeface);
            tabLayout.getTabAt(i).setCustomView(view);
        }
    }

    //-----------------------------------------------
    public static void applyFontTab(Activity activity, ViewPager viewPager, TabLayout tabLayout, int textColor) {

        for (int i = 0; i < viewPager.getAdapter().getCount(); i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_filter, null);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            //ImageView imgTab = (ImageView) view.findViewById(R.id.imgTab);
            if (i == viewPager.getCurrentItem()) tv.setSelected(true);
            tv.setText(viewPager.getAdapter().getPageTitle(i));
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
            tv.setTypeface(typeface);
            tabLayout.getTabAt(i).setCustomView(view);
        }
    }

    //-----------------------------------------------
    public static void applyFontTabWays(Activity activity, TabLayout tabLayout) {
        View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
        TextView tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setText(R.string.twoWay);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.oneWay);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));
        tabLayout.getTabAt(1).select();

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
    public static void applyFontTabPassengerTrain(Activity activity, TabLayout tabLayout) {
        View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
        TextView tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setText(R.string.foreign);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));


        view = activity.getLayoutInflater().inflate(R.layout.row_item_tab, null);
        tv = (TextView) view.findViewById(R.id.txtTitle);
        tv.setSelected(true);
        tv.setText(R.string.irani);
        tv.setTypeface(typeface);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tv));

        tabLayout.getTabAt(1).select();
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
    public static void applyFontTabServices(Activity activity, TabLayout tabLayout, ArrayList<Integer> resTitle, ArrayList<Integer> resImg) {
        for (int i = 0; i < resTitle.size(); i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/" + UtilFonts.IRAN_SANS_NORMAL);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            ImageView img = view.findViewById(R.id.img);
            tv.setText(resTitle.get(i));
            tv.setTypeface(typeface);
            tv.setTextSize(15);
            img.setImageResource(resImg.get(i));
            //tv.setCompoundDrawablesWithIntrinsicBounds(0, resImg.get(i), 0, 0);
            tabLayout.addTab(tabLayout.newTab().setCustomView(view));
        }

//        tabLayout.getTabAt(3).select();

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
    public static void applyFontTabServicesFlight(Activity activity, TabLayout tabLayout) {
        tabLayout.removeAllTabs();
        int resTitle[] = {R.string.airPlanInternational, R.string.airPlanDomestic};
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
    public static void applyFontTabServicesDateRange(Activity activity, TabLayout tabLayout) {
        int resTitle[] = {R.string.return_, R.string.went};
        int resImg[] = {R.mipmap.ic_date_range};
        //ImageView img = (ImageView) view.findViewById(R.id.imgTab);
        for (int i = 0; i < resTitle.length; i++) {
            View view = activity.getLayoutInflater().inflate(R.layout.row_item_tab_gray_white, null);
            Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/" + UtilFonts.IRAN_SANS_BOLD);
            TextView tv = (TextView) view.findViewById(R.id.txtTitle);
            tv.setText(resTitle[i]);
            tv.setTypeface(typeface);
            tv.setTextSize(14);
            tabLayout.addTab(tabLayout.newTab().setCustomView(tv));
        }

        tabLayout.getTabAt(tabLayout.getTabCount() - 1).select();

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.twoWay));
        //tabLayout.addTab(tabLayout.newTab().setText(R.string.oneWay),true);

//        tv.setText(viewPager.getAdapter().getPageTitle(i));
//        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/iran_sans_web.ttf");
//        tv.setTypeface(typeface);
//        tabLayout.getTabAt(0).setCustomView(view);

    }

}
