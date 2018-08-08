package com.hami.common.Util;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hami.common.Const.KeyConst;

public class UtilFragment {


    public static void addNewFragmentWithTag(FragmentManager fragmentManager, Fragment fragment, String tag, int id) {
        fragmentManager.beginTransaction().replace(id, fragment).addToBackStack(tag).commitAllowingStateLoss();
        //fragmentManager.addOnBackStackChangedListener(onBackStackChangedListener);
    }

    //-----------------------------------------------
    public static void changeFragment(FragmentManager fragmentManager, Fragment fragment, int id) {
        fragmentManager.beginTransaction().replace(id, fragment, KeyConst.KEY_FRAGMENT_INTERNATONAL).commitAllowingStateLoss();
    }


    //-----------------------------------------------
    public static void addNewFragment(FragmentManager fragmentManager, Fragment fragment, int id) {
        fragmentManager.beginTransaction().replace(id, fragment).addToBackStack(KeyConst.KEY_FRAGMENT_INTERNATONAL).commitAllowingStateLoss();
    }


}
