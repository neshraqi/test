package hami.hamibelit.Activity.Authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import hami.hamibelit.Activity.Authentication.Controller.UserResponse;
import hami.hamibelit.R;
import hami.hamibelit.Util.Database.DataSaver;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilPrice;
import hami.hamibelit.View.Progressbar.ButtonWithProgress;
import hami.hamibelit.View.ToastMessageBar;

public class SettingUserMaterial extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;
    private TextView txtCredit, txtInventory;
    private Button btnGetGift, btnChargeGift;
    private EditText edtFullName, edtNameOfAccountHolder, edtNumberAccountSheba;
    private ButtonWithProgress btnApplyEdition;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_user_layout);
        if (!checkLogin()) {
            ToastMessageBar.show(this, R.string.msgErrorEmailOrMobile);
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();
        } else
            initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.root), UtilFonts.IRAN_SANS_NORMAL);
        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DataSaver(SettingUserMaterial.this).logout();
                finish();

            }
        });
        btnApplyEdition = (ButtonWithProgress) findViewById(R.id.btnApplyEdition);
        btnApplyEdition.setConfig("ثبت تغییرات", "درحال اعمال تغییرات", "ثبت تغییرات");
        btnApplyEdition.setCallBack(onClickListener);
        setupToolbar();
        setupTools();


    }

    //-----------------------------------------------
    private void setupTools() {
        TextView txtCredit = (TextView) findViewById(R.id.txtCredit);
        TextView txtInventory = (TextView) findViewById(R.id.txtInventory);
        Button btnGetGift = (Button) findViewById(R.id.btnGetGift);
        Button btnChargeGift = (Button) findViewById(R.id.btnChargeGift);
        UserResponse userResponse = new DataSaver(this).getUser();
        txtCredit.setText("اعتبار: " + UtilPrice.getFinalPriceToman(userResponse.getUser().getUser_etebar()));
        txtInventory.setText("موجودی: " + UtilPrice.getFinalPriceToman(userResponse.getUser().getUser_mojodi()));
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveEdition();
        }
    };

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//        this.doubleBackToExitPressedOnce = true;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);
    }

    //-----------------------------------------------
    private void saveEdition() {
    }

    //-----------------------------------------------
    private Boolean checkLogin() {
        return new DataSaver(this).hasLogin();
    }

    //-----------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void setupToolbar() {
        TextView txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        UtilFonts.overrideFonts(this, txtTitleMenu, UtilFonts.IRAN_SANS_BOLD);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);

        txtTitleMenu.setText(R.string.app_namePR);
        txtTitleMenu.setSelected(true);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    //-----------------------------------------------
    private void showUrl(final String url) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                builder.setToolbarColor(ContextCompat.getColor(SettingUserMaterial.this, R.color.colorPrimaryDark));
                customTabsIntent.launchUrl(SettingUserMaterial.this, Uri.parse(url));


            }
        });

    }
    //-----------------------------------------------

}
