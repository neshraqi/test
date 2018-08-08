package hami.mainapp;

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

import hami.mainapp.Activity.Authentication.SettingUserMaterial;
import hami.mainapp.Activity.PastPurchases.PastPurchasesServicesMaterialFragment;
import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.MainServicesSearchMaterialFragment;
import hami.mainapp.Activity.Updates.GetUpdatePresenter;
import hami.mainapp.Activity.Updates.UpdatesApi;
import hami.mainapp.BaseController.AccessStatusResponse;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.Const.KeyConst;
import hami.mainapp.Util.CustomTypefaceSpan;
import hami.mainapp.Util.CustomeChrome.CustomTabsPackages;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.Util.UtilFragment;
import hami.mainapp.View.ToastMessageBar;


public class MainActivityMaterial extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = MainActivityMaterial.class.getSimpleName();
    private AccessStatusResponse accessStatusResponse;
    private int serviceId ;
    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_material);

        if (getIntent().hasExtra(AccessStatusResponse.class.getName())) {
            accessStatusResponse = (AccessStatusResponse) getIntent().getExtras().getSerializable(AccessStatusResponse.class.getName());
            serviceId =  getIntent().getExtras().getInt(ServiceID.INTENT_SERVICE_ID);

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
        setupNavigation();
        setupToolbar();
        UtilFragment.changeFragment(getSupportFragmentManager(), MainServicesSearchMaterialFragment.newInstance(accessStatusResponse,serviceId));
    }
    //-----------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            PastPurchasesServicesMaterialFragment myFragment = (PastPurchasesServicesMaterialFragment) getSupportFragmentManager().findFragmentByTag(KeyConst.KEY_FRAGMENT_INTERNATONAL);
            if (myFragment != null && myFragment.isVisible()) {
                UtilFragment.changeFragment(getSupportFragmentManager(), PastPurchasesServicesMaterialFragment.newInstance());
            }
        } catch (Exception e) {

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void setupToolbar() {

        txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        txtSubTitleMenu = (TextView) findViewById(R.id.txtSubTitleMenu);
        UtilFonts.overrideFonts(this, txtTitleMenu, UtilFonts.IRAN_SANS_BOLD);
        UtilFonts.overrideFonts(this, txtSubTitleMenu, UtilFonts.IRAN_SANS_NORMAL);
        ImageView btnMenuActionMenu = (ImageView) findViewById(R.id.btnMenuNavigation);
        btnMenuActionMenu.setOnClickListener(onClickListener);
        txtTitleMenu.setText(R.string.app_namePR);
        txtSubTitleMenu.setText(R.string.searchFlight);
        txtSubTitleMenu.setSelected(true);
        txtTitleMenu.setSelected(true);
    }

    //-----------------------------------------------
    private void applyFontToMenuItem(MenuItem mi) {

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/" + UtilFonts.IRAN_SANS_BOLD);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    //-----------------------------------------------
    public void setupNavigation() {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        TextView txtSite1 = findViewById(R.id.txtSite1);
       // TextView txtSite2 = findViewById(R.id.txtSite2);
        ImageView imgInstagram = findViewById(R.id.imgInstagram);
        ImageView imgTelegram = findViewById(R.id.imgTelegram);
        ImageView imgAparat = findViewById(R.id.imgAparat);
        ImageView imgFacebook = findViewById(R.id.imgFacebook);
        imgInstagram.setOnClickListener(onClickListener);
        imgTelegram.setOnClickListener(onClickListener);
        txtSite1.setOnClickListener(onClickListener);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
//            if (mi.getItemId() == R.id.menuHotel && !accessStatusResponse.getInternationalHotel() && !accessStatusResponse.getDomesticHotel()) {
//                mi.setVisible(false);
//            } else if (mi.getItemId() == R.id.menuTour && !accessStatusResponse.getTour()) {
//                mi.setVisible(false);
//            } else {
            mi.setVisible(true);
            Drawable drawable = mi.getIcon();
            Drawable wrapDrawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(wrapDrawable, getResources().getColor(android.R.color.white));
            mi.setIcon(wrapDrawable);
            if (drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    Drawable subDrawable = subMenuItem.getIcon();
                    Drawable subWrapDrawable = DrawableCompat.wrap(subDrawable);
                    DrawableCompat.setTint(wrapDrawable, getResources().getColor(android.R.color.white));
                    subMenu.setIcon(subWrapDrawable);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
            //}
        }
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
       // navigationView.setItemIconTintList(R.color.white);
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnMenuNavigation:
                    if (drawer.isDrawerOpen(Gravity.RIGHT))
                        drawer.closeDrawer(Gravity.RIGHT);
                    else
                        drawer.openDrawer(Gravity.RIGHT);
                    break;
                case R.id.imgTelegram:
                    new CustomTabsPackages(MainActivityMaterial.this).showUrl("https://t.me/beheshtnasim");
                    break;
                case R.id.imgInstagram:
                    new CustomTabsPackages(MainActivityMaterial.this).showUrl("https://www.instagram.com/nasimbehesht724/");
                    break;

                case R.id.txtSite1:
                    new CustomTabsPackages(MainActivityMaterial.this).showUrl("https://nasimbehesht724.com/");
                    break;

            }
        }
    };


    //-----------------------------------------------
    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.menuSearch:
                    UtilFragment.changeFragment(getSupportFragmentManager(), MainServicesSearchMaterialFragment.newInstance(accessStatusResponse,serviceId));
                    txtSubTitleMenu.setText(R.string.searchTicket);
                    item.setChecked(true);
                    break;

                case R.id.menuRules:
                    String url = BaseConfig.BASE_URL_MASTER + "pages/rules";
                    new CustomTabsPackages(MainActivityMaterial.this).showUrl(url);
                    break;

                case R.id.menuPastPurchases:
                    UtilFragment.changeFragment(getSupportFragmentManager(), PastPurchasesServicesMaterialFragment.newInstance());
                    txtSubTitleMenu.setText(R.string.pastPurchases);
                    item.setChecked(true);
                    break;

                case R.id.menuCallSupport:
                    url = BaseConfig.BASE_URL_MASTER + "contact";
                    new CustomTabsPackages(MainActivityMaterial.this).showUrl(url);
                    break;

                case R.id.menuAccount:
                    Intent intent = new Intent(MainActivityMaterial.this, SettingUserMaterial.class);
                    startActivity(intent);
                    break;

                case R.id.menuExit:
                    finish();
                    break;
            }
            drawer.closeDrawer(Gravity.RIGHT);
            return false;
        }
    };

    //-----------------------------------------------
    private void showUrl(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(ContextCompat.getColor(MainActivityMaterial.this, R.color.colorPrimaryDark));
                customTabsIntent.launchUrl(MainActivityMaterial.this, Uri.parse(url));
            }
        });

    }
    //-----------------------------------------------
}
