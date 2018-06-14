package hami.aniticket.Util;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by renjer on 2017-12-27.
 */

public class UtilVibrator {

    private android.os.Vibrator mVibrator;
    private Uri notification;
    private Ringtone ringtone;
    int dot = 200; // Length of a Morse Code "dot" in milliseconds
    int dash = 500; // Length of a Morse Code "dash" in milliseconds
    int short_gap = 200; // Length of Gap Between dots/dashes
    int medium_gap = 500; // Length of Gap Between Letters
    int long_gap = 1000; // Length of Gap Between Words
    long[] pattern = {0, // Start immediately
            dot, short_gap, dot, short_gap, dot // s
    };

    //-----------------------------------------------
    public UtilVibrator(Context context) {
        mVibrator = (android.os.Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(context, notification);
    }

    //-----------------------------------------------
    public void vibrate() {
        try {
            mVibrator.vibrate(pattern, -1);
        } catch (Exception e) {
            if (mVibrator != null)
                mVibrator.cancel();
        }
    }

    //-----------------------------------------------
    public void vibrateBySound() {
        try {
            mVibrator.vibrate(pattern, -1);
            ringtone.play();
        } catch (Exception e) {
            if (mVibrator != null)
                mVibrator.cancel();
        }
    }
    //-----------------------------------------------
}
