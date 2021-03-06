package com.hami.common.Util.CustomeChrome;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsService;
import android.support.v4.content.ContextCompat;
import com.hami.common.R;
import com.hami.common.View.ToastMessageBar;
import java.util.ArrayList;
import java.util.List;


public class CustomTabsPackages {
    private static final String TAG = "CustomTabsPackages";
    private Context context;
    private int color = R.color.browser_actions_bg_grey;
    //-----------------------------------------------

    public CustomTabsPackages(Context context) {
        this.context = context;
    }

    //----------------------------------------
    public CustomTabsPackages(Context context, int color) {
        this.context = context;
        this.color = color;
    }

    //-----------------------------------------------
    public ArrayList<ResolveInfo> getCustomTabsPackages(Context context, String url) {
        try {
            PackageManager pm = context.getPackageManager();
            // Get default VIEW intent handler.
            Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

            // Get all apps that can handle VIEW intents.
            List<ResolveInfo> resolvedActivityList = pm.queryIntentActivities(activityIntent, 0);
            ArrayList packagesSupportingCustomTabs = new ArrayList<>();
            for (ResolveInfo info : resolvedActivityList) {
                Intent serviceIntent = new Intent();
                serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
                serviceIntent.setPackage(info.activityInfo.packageName);
                // Check if this package also resolves the Custom Tabs service.
                if (pm.resolveService(serviceIntent, 0) != null) {
                    packagesSupportingCustomTabs.add(info);
                }
            }
            return packagesSupportingCustomTabs;
        } catch (Exception e) {

            return null;
        }

    }

    //-----------------------------------------------
    public String getIntentPackage(String url) {
        try {
            ArrayList<ResolveInfo> results = getCustomTabsPackages(context, url);
            if (results == null || results.size() == 0)
                return "";
            return results.get(0).activityInfo.packageName;
        } catch (Exception e) {

            return "";
        }
    }

    //-----------------------------------------------
    public void showUrl(String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            builder.setToolbarColor(ContextCompat.getColor(context, color));
            String intentPackage = new CustomTabsPackages(context).getIntentPackage(url);
            if (intentPackage != null && intentPackage.length() > 0)
                customTabsIntent.intent.setPackage(intentPackage);
            customTabsIntent.launchUrl(context, Uri.parse(url));
        } catch (Exception e) {
        }

    }
    //-----------------------------------------------
}
