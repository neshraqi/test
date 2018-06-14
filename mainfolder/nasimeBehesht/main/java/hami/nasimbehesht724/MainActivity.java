package hami.nasimbehesht724;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hami.nasimbehesht724.Activity.Authentication.SettingUserMaterial;
import hami.nasimbehesht724.Activity.PastPurchases.PastPurchasesServicesMaterialFragment;
import hami.nasimbehesht724.Activity.ServiceSearch.ConstService.ServiceID;
import hami.nasimbehesht724.Activity.ServiceSearch.MainServicesSearchMaterialFragment;
import hami.nasimbehesht724.Activity.Updates.GetUpdatePresenter;
import hami.nasimbehesht724.Activity.Updates.UpdatesApi;
import hami.nasimbehesht724.BaseController.AccessStatusResponse;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.Const.KeyConst;
import hami.nasimbehesht724.Util.CustomTypefaceSpan;
import hami.nasimbehesht724.Util.CustomeChrome.CustomTabsPackages;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.View.ToastMessageBar;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private ImageView ivToor, ivHotel, ivTrain, ivPlane, ivBus;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    private AccessStatusResponse accessStatusResponse;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra(AccessStatusResponse.class.getName())) {
            accessStatusResponse = (AccessStatusResponse) getIntent().getExtras().getSerializable(AccessStatusResponse.class.getName());
            //initialGCM();
            initialComponentActivity();
        } else {
            ToastMessageBar.show(this, R.string.msgErrorApplication);
            finish();
        }

    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(AccessStatusResponse.class.getName(), accessStatusResponse);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            accessStatusResponse = (AccessStatusResponse) savedInstanceState.getSerializable(AccessStatusResponse.class.getName());
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    //-----------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();

    }

    //-----------------------------------------------
    private void initialComponentActivity() {

        ivToor = (ImageView) findViewById(R.id.ivToor);
        ivHotel = (ImageView) findViewById(R.id.ivHotel);
        ivTrain = (ImageView) findViewById(R.id.ivTrain);
        ivPlane = (ImageView) findViewById(R.id.ivPlane);
        ivBus = (ImageView) findViewById(R.id.ivBus);

        if (!accessStatusResponse.getTour()) {
            ivToor.setVisibility(View.GONE);
        }
        if (!accessStatusResponse.getDomesticHotel() && !accessStatusResponse.getInternationalHotel()) {
            ivHotel.setVisibility(View.GONE);
        }
        if (!accessStatusResponse.getTrain()) {
            ivTrain.setVisibility(View.GONE);
        }
        if (!accessStatusResponse.getFlight() && !accessStatusResponse.getInternational()) {
            ivPlane.setVisibility(View.GONE);
        }
        if (!accessStatusResponse.getBus()) {
            ivBus.setVisibility(View.GONE);
        }

        ivToor.setOnClickListener(this);
        ivHotel.setOnClickListener(this);
        ivTrain.setOnClickListener(this);
        ivPlane.setOnClickListener(this);
        ivBus.setOnClickListener(this);

        getUpdates();

//        setupNavigation();
//        setupToolbar();
//        UtilFragment.changeFragment(getSupportFragmentManager(), MainServicesSearchMaterialFragment.newInstance(accessStatusResponse));

    }


    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    //-----------------------------------------------
    private void getUpdates() {
        new UpdatesApi(this).getUpdate(getUpdatePresenter);
    }

    //-----------------------------------------------
    private GetUpdatePresenter getUpdatePresenter = new GetUpdatePresenter() {
        @Override
        public void hasUpdate() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendNotification();
                }
            });
        }

        @Override
        public void noUpdate() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };

    //-----------------------------------------------
    public void sendNotification() {
        String content = "نسخه جدید نرم افزار " + getString(R.string.app_namePR) + " را هم اکنون از Store گوگل یا بازار دانلود کنید ";
        String title = "نسخه جدید " + getString(R.string.app_namePR);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(Notification.PRIORITY_HIGH);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }


    //-----------------------------------------------
    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.ivToor:
                startActivity(new Intent(MainActivity.this, MainActivityMaterialTourService.class));
                break;

            case R.id.ivHotel:
                intent = new Intent(MainActivity.this, MainActivityMaterial.class);
                intent.putExtra(AccessStatusResponse.class.getName(), accessStatusResponse);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_HOTEL);
                startActivity(intent);
                break;

            case R.id.ivTrain:
                intent = new Intent(MainActivity.this, MainActivityMaterial.class);
                intent.putExtra(AccessStatusResponse.class.getName(), accessStatusResponse);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_TRAIN);
                startActivity(intent);
                break;

            case R.id.ivPlane:
                intent = new Intent(MainActivity.this, MainActivityMaterial.class);
                intent.putExtra(AccessStatusResponse.class.getName(), accessStatusResponse);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_FLIGHT_DOMESTIC);
                startActivity(intent);
                break;

            case R.id.ivBus:
                intent = new Intent(MainActivity.this, MainActivityMaterial.class);
                intent.putExtra(AccessStatusResponse.class.getName(), accessStatusResponse);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_BUS);
                startActivity(intent);
                break;

        }
    }
    //-----------------------------------------------
}
