package hami.hamibelit.Util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.Nullable;


import java.util.Calendar;
import hami.hamibelit.R;

/**
 * Created by renjer on 1/9/2017.
 */

public class UtilDate {
    private static final String TIME_PICKER = "TimePickerDialog", DATE_PICKER = "DatePickerDialog";

    //    //-----------------------------------------------
//    public static void instanceDialogPersianTime(Activity activity , TimePickerDialog.OnTimeSetListener onTimeSetListener  ){
//        PersianCalendar now = new PersianCalendar();
//        TimePickerDialog tpd = TimePickerDialog.newInstance(
//                onTimeSetListener,
//                now.get(PersianCalendar.HOUR_OF_DAY),
//                now.get(PersianCalendar.MINUTE),
//                true
//        );
//        //tpd.setThemeDark(modeDarkTime.isChecked());
//        tpd.show(activity.getFragmentManager(), TIME_PICKER);
//    }
    //-----------------------------------------------
    public static void instanceDialogTime(Activity activity , android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener  ){
        Calendar calendar = Calendar.getInstance();

    }
    //-----------------------------------------------
    public static void instanceDialogDate(Activity activity , @Nullable String currentBirthDay , android.app.DatePickerDialog.OnDateSetListener onDateSetListener){
        String[] res = null;
        Calendar calendar = Calendar.getInstance();
        if(currentBirthDay!=null && currentBirthDay.length()>5) {
            res = currentBirthDay.split("/");
            calendar.set(Integer.valueOf(res[0]),Integer.valueOf(res[1]),Integer.valueOf(res[2]));
        }
        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(activity,onDateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==DialogInterface.BUTTON_NEGATIVE){

                }
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==DialogInterface.BUTTON_NEGATIVE){

                }
            }
        });
        datePickerDialog.show();
        //dpd.show(activity.getFragmentManager(), DATE_PICKER);
    }
}
