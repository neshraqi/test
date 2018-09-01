package com.hami.common.View.Validation;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hami.common.R;
import com.hami.common.Util.ValidateNationalCode;
import com.hami.common.View.ToastMessageBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by renjer on 1/26/2017.
 */

public class ValidationClass {
    public static Boolean validateEditTextNormal(View coordinator, EditText editText, int errorShow, int minLength) {
        if (editText != null && editText.length() >= minLength) {
            return true;
        } else {
            Snackbar snackbar = Snackbar.make(coordinator, errorShow, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateEditTextToast(Context context, EditText editText, int errorShow, int minLength) {
        if (editText != null && editText.length() >= minLength) {
            return true;
        } else {
            Toast.makeText(context, errorShow, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateNationalCodeToast(Context context, String value) {
        if (ValidateNationalCode.isValidNationalCode(value))
            return true;
        else {
            ToastMessageBar.show(context, R.string.validateNationalCode);

            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateNationalCodeNormal(View coordinator, String value) {
        if (ValidateNationalCode.isValidNationalCode(value))
            return true;
        else {
            Snackbar snackbar = Snackbar.make(coordinator, R.string.validateNationalCode, Snackbar.LENGTH_SHORT);
            snackbar.show();
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateEditTextToast(Context context, EditText editText, int errorShow) {
        if (editText != null && editText.length() > 0)
            return true;
        else {

            ToastMessageBar.show(context, errorShow);
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateEmailEditTextToast(Context context, EditText editText, int errorShow) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (editText != null && editText.length() > 0 && editText.getText().toString().matches(emailPattern))
            return true;
        else {
            ToastMessageBar.show(context, errorShow);
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateEmail(String emailString) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (emailString != null && emailString.length() > 0 && emailString.matches(emailPattern))
            return true;
        else {
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateExpPassportNormal(View coordinator, String dateFlight, String datePassport, int errorShow) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateF = formatter.parse(dateFlight);
            Date dateP = formatter.parse(datePassport);
            long diff = dateP.getTime() - dateF.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            if (days >= 181) {
                return true;
            } else {
                Snackbar snackbar = Snackbar.make(coordinator, errorShow, Snackbar.LENGTH_SHORT);
                snackbar.show();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateExpPassportToast(Context context, String dateFlight, String datePassport, int errorShow) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date dateF = formatter.parse(dateFlight);
            Date dateP = formatter.parse(datePassport);
            long diff = dateP.getTime() - dateF.getTime();
            long days = diff / (24 * 60 * 60 * 1000);
            if (days >= 181) {
                return true;
            } else {
                Toast.makeText(context, errorShow, Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //-----------------------------------------------
    public static Boolean validateBirthDayToast(Context context, int year, int typePassenger) {
        try {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            switch (typePassenger) {
                case 1:
                    if ((currentYear - year) >= 12)
                        return true;
                    else {
                        Toast.makeText(context, R.string.validateBirthDayAdults, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                case 2:
                    if ((currentYear - year) < 12 && (currentYear - year) >= 2)
                        return true;
                    else {
                        Toast.makeText(context, R.string.validateBirthDayChild, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                case 3:
                    if ((currentYear - year) < 2)
                        return true;
                    else {
                        Toast.makeText(context, R.string.validateBirthDayInfants, Toast.LENGTH_SHORT).show();
                        return false;
                    }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


}
