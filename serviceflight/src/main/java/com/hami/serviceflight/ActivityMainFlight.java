package com.hami.serviceflight;

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
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticRequest;
import com.hami.serviceflight.Services.Domestic.Fragment.FragmentFinalBookingFlightDomestic;
import com.hami.serviceflight.Services.Domestic.Fragment.FragmentListWentDomestic;
import com.hami.serviceflight.Services.International.Controller.Model.FlightInternationalRequest;
import com.hami.serviceflight.Services.International.Fragment.FragmentFinalBookingFlightInternational;
import com.hami.serviceflight.Services.International.Fragment.FragmentListWentInternational;


public class ActivityMainFlight extends AppCompatActivity {
    public final static String TAG_FLIGHT_INTERNATIONAL = "TAG_FLIGHT_INTERNATIONAL ";
    private Boolean hasInternational;
    private DomesticRequest domesticRequest;
    private FlightInternationalRequest flightRequest;

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        try {
            hasInternational = getIntent().getExtras().getBoolean(TAG_FLIGHT_INTERNATIONAL);
            if (hasInternational)
                flightRequest = (FlightInternationalRequest) getIntent().getExtras().getSerializable(FlightInternationalRequest.class.getName());
            else
                domesticRequest = (DomesticRequest) getIntent().getExtras().getSerializable(DomesticRequest.class.getName());
        } catch (Exception e) {

        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            hasInternational = savedInstanceState.getBoolean(TAG_FLIGHT_INTERNATIONAL);
            domesticRequest = (DomesticRequest) savedInstanceState.getSerializable(DomesticRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG_FLIGHT_INTERNATIONAL, hasInternational);
        if (hasInternational)
            outState.putSerializable(FlightInternationalRequest.class.getName(), flightRequest);
        else
            outState.putSerializable(DomesticRequest.class.getName(), domesticRequest);
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMainContent), UtilFonts.IRAN_SANS_NORMAL);
        setupToolbar();
        timer();
        if (hasInternational)
            UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentInternational.newInstance(flightRequest), R.id.frame_Layout);
        else
            UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentDomestic.newInstance(domesticRequest), R.id.frame_Layout);
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        try {
            int index = getSupportFragmentManager().getFragments().size() - 1;
            if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentFinalBookingFlightDomestic &&
                    ((FragmentFinalBookingFlightDomestic) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
                finish();
            } else if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentFinalBookingFlightInternational &&
                    ((FragmentFinalBookingFlightInternational) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
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
        if (hasInternational) {
            txtTitle.setText("پرواز خارجی");
        } else {
            txtTitle.setText("پرواز داخلی");
        }
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
                if (hasInternational)
                    UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentInternational.newInstance(flightRequest), R.id.frame_Layout);
                else
                    UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentDomestic.newInstance(domesticRequest), R.id.frame_Layout);
            }
        });
    }

}
