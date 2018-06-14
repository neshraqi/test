package hami.aniticket.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by renjer on 2018-03-27.
 */

public class UtilActionCall {
    public static void call(Context context, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }
}
