package hami.nasimbehesht724;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hami.nasimbehesht724.Activity.ServiceTour.MainTourServicesMaterialFragment;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;


public class MainActivityMaterialTourService extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = MainActivityMaterialTourService.class.getSimpleName();

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        setupToolbar();
        UtilFragment.changeFragment(getSupportFragmentManager(), MainTourServicesMaterialFragment.newInstance());

    }


    //-----------------------------------------------
    private void setupToolbar() {
        txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        UtilFonts.overrideFonts(this, txtTitleMenu, UtilFonts.IRAN_SANS_BOLD);
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTitleMenu.setText(R.string.app_namePR);
        txtTitleMenu.setSelected(true);
    }
    //-----------------------------------------------
}
