package com.hami.common.Util.CustomeChrome;

import android.app.Activity;
import android.net.Uri;

/**
 * A Fallback that opens a Webview when Custom Tabs is not available
 */
public class WebviewFallback implements CustomTabActivityHelper.CustomTabFallback {
    @Override
    public void openUri(Activity activity, Uri uri) {
        //Intent intent = new Intent(activity, WebviewActivity.class);
        //intent.putExtra(WebviewActivity.EXTRA_URL, uri.toString());
        //activity.startActivity(intent);
    }
}