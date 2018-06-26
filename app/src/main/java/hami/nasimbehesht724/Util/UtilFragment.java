package hami.nasimbehesht724.Util;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import hami.nasimbehesht724.Const.KeyConst;
import hami.nasimbehesht724.R;

/**
 * Created by renjer on 1/29/2017.
 */

public class UtilFragment {
    public static void addNewFragment(FragmentManager fragmentManager, Fragment fragment, @Nullable FragmentManager.OnBackStackChangedListener onBackStackChangedListener) {
        fragmentManager.beginTransaction().replace(R.id.frame_Layout, fragment).addToBackStack(KeyConst.KEY_FRAGMENT_INTERNATONAL).commitAllowingStateLoss();
        //fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);
    }

    //-----------------------------------------------
    public static void addNewFragmentWithTag(FragmentManager fragmentManager, Fragment fragment, String tag) {
        fragmentManager.beginTransaction().replace(R.id.frame_Layout, fragment).addToBackStack(tag).commitAllowingStateLoss();
        //fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);
    }

    //-----------------------------------------------
    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame_Layout, fragment, KeyConst.KEY_FRAGMENT_INTERNATONAL).commitAllowingStateLoss();
    }


    //-----------------------------------------------
    public static void addNewFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame_Layout, fragment).addToBackStack(KeyConst.KEY_FRAGMENT_INTERNATONAL).commitAllowingStateLoss();
    }
}
