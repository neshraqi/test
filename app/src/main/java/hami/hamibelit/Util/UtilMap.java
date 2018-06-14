package hami.hamibelit.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by renjer on 2018-02-27.
 */

public class UtilMap {
    private Context context;
    //-----------------------------------------------

    public UtilMap(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void showGoogleMap(String latitude, String longitude, String title) {
        //double latitude = 40.714728;
        //double longitude = -73.998672;
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + title + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
        Uri uri = Uri.parse(uriString);
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        context.startActivity(mapIntent);
    }
}
