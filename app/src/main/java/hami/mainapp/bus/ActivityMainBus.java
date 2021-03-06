package hami.mainapp.bus;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.Const.KeyConst;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.View.DialogExpire;

import hami.mainapp.R;
import hami.mainapp.bus.Services.Controller.Model.SearchBusRequest;
import hami.mainapp.bus.Services.Fragment.FragmentListWentBus;
import hami.mainapp.bus.Services.Fragment.FragmentReserveBus;

public class ActivityMainBus extends AppCompatActivity {


    private SearchBusRequest searchBusRequest;

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        try {
            searchBusRequest = (SearchBusRequest) getIntent().getExtras().getSerializable(SearchBusRequest.class.getName());
        } catch (Exception e) {

        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            searchBusRequest = (SearchBusRequest) savedInstanceState.getSerializable(SearchBusRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMainContent), UtilFonts.IRAN_SANS_NORMAL);
        setupToolbar();
        timer();
        UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentBus.newInstance(searchBusRequest), R.id.frame_Layout);
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        try {
            int index = getSupportFragmentManager().getFragments().size() - 1;
            if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentReserveBus &&
                    ((FragmentReserveBus) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
                finish();
            } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                finish();
            }
        } catch (Exception e) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            } else {
                finish();
            }
        }
    }

    //-----------------------------------------------
    private void setupToolbar() {
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitleMenu);
        txtTitle.setText("اتوبوس");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //-----------------------------------------------
    private void timer() {
        new CountDownTimer(KeyConst.TIME_EXPIRE, KeyConst.TIME_STEP) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                showExpireTime();
            }
        }.start();
    }

    //-----------------------------------------------
    private void showExpireTime() {
        final DialogExpire dialogExpire = new DialogExpire();
        dialogExpire.show(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = getSupportFragmentManager().getBackStackEntryCount();
                while (counter >= 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                    counter = getSupportFragmentManager().getBackStackEntryCount();
                }
                dialogExpire.dismiss();
                timer();
                UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentBus.newInstance(searchBusRequest), R.id.frame_Layout);
            }
        });
    }
}
