package hami.mainapp.Util.Rating;

import android.content.Context;
import android.util.Log;


import com.webianks.easy_feedback.EasyFeedback;

/**
 * Created by renjer on 2018-01-09.
 */

public class UtilReportError {
    public static final String TAG = "UtilReportError";
    //-----------------------------------------------
    public void showReportError (Context context) {
        try {
            new EasyFeedback.Builder(context)
                    .withEmail("atr.gol@gmail.com")
                    .withSystemInfo()
                    .build()
                    .start();
        } catch (Exception e) {

        }

    }
}
