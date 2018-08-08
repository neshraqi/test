package hami.mainapp.Util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by renjer on 2017-03-12.
 */

public class Keyboard {
    public static void closeKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //-----------------------------------------------
    private static String[] pn = {"۰", "۱", "۲", "۳", "۴", "٤", "۵", "٥", "۶", "٦", "۷", "۸", "۹"};
    private static String[] en = {"0", "1", "2", "3", "4", "4", "5", "5", "6", "6", "7", "8", "9"};

    //-----------------------------------------------
    public static String convertPersianNumberToEngNumber(String strNum) {
        try{
            String chash = strNum;
            for (int i = 0; i < en.length; i++)
                chash = chash.replace(pn[i], en[i]);
            return chash;
        }
        catch (Exception e){
            return strNum;
        }
    }
    //-----------------------------------------------

}
