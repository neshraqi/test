package hami.mainapp.Util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by renjer on 2017-12-26.
 */

public class SecureAndroid {
    public static String getSecureId(Context context) {
        try {
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            return androidId;
        } catch (Exception e) {
            return "";
        }
    }
    //-----------------------------------------------
    
}
