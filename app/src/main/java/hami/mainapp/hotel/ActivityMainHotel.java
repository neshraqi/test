package hami.mainapp.hotel;

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
import hami.mainapp.hotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.mainapp.hotel.Domestic.FragmentFinalBookingDomesticHotel;
import hami.mainapp.hotel.Domestic.FragmentListDomesticHotel;
import hami.mainapp.hotel.International.Controller.Model.InternationalHotelSearchRequest;
import hami.mainapp.hotel.International.FragmentBookingInternationalHotel;
import hami.mainapp.hotel.International.FragmentListInternationalHotel;


public class ActivityMainHotel extends AppCompatActivity {


    private String hotelSearchRequestJson;
    private InternationalHotelSearchRequest hotelSearchRequest;
    private DomesticHotelSearchRequest domesticHotelSearchRequest;
    private static final String TAG = "ActivityMainHotel";

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (getIntent().hasExtra(InternationalHotelSearchRequest.class.getName())) {
                hotelSearchRequestJson = getIntent().getExtras().getString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
                hotelSearchRequest = getIntent().getExtras().getParcelable(InternationalHotelSearchRequest.class.getName());
                setContentView(R.layout.activity_service_main_ltr);
                setupToolbarInternationalHotel();
                setupInternationalHotel();
            } else if (getIntent().hasExtra(DomesticHotelSearchRequest.class.getName())) {
                domesticHotelSearchRequest = (DomesticHotelSearchRequest) getIntent().getExtras().getSerializable(DomesticHotelSearchRequest.class.getName());
                setContentView(R.layout.activity_service_main);
                setupToolbarDomesticHotel();
                setupDomesticHotel();
            }


        } catch (Exception e) {

            finish();
        }

    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            hotelSearchRequestJson = savedInstanceState.getString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
            hotelSearchRequest = savedInstanceState.getParcelable(InternationalHotelSearchRequest.class.getName());
            domesticHotelSearchRequest = (DomesticHotelSearchRequest) savedInstanceState.getSerializable(DomesticHotelSearchRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (outState != null) {
            outState.putString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, hotelSearchRequestJson);
            outState.putParcelable(InternationalHotelSearchRequest.class.getName(), hotelSearchRequest);
            outState.putSerializable(DomesticHotelSearchRequest.class.getName(), domesticHotelSearchRequest);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    private void setupDomesticHotel() {
        timer();
        UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListDomesticHotel.newInstance(domesticHotelSearchRequest), R.id.frame_Layout);
    }

    //-----------------------------------------------
    private void setupInternationalHotel() {
        timer();
        UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListInternationalHotel.newInstance(hotelSearchRequestJson, hotelSearchRequest), R.id.frame_Layout);
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        try {
            int index = getSupportFragmentManager().getFragments().size() - 1;
            if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentBookingInternationalHotel &&
                    ((FragmentBookingInternationalHotel) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
                finish();
            } else if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentFinalBookingDomesticHotel &&
                    ((FragmentFinalBookingDomesticHotel) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
                finish();
            } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        } catch (Exception e) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }

    }

    //-----------------------------------------------
    private void setupToolbarInternationalHotel() {
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitleMenu);
        UtilFonts.overrideFonts(this, txtTitle, UtilFonts.TAHOMA);
        txtTitle.setText(getString(R.string.hotelInternational));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //-----------------------------------------------
    private void setupToolbarDomesticHotel() {
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);

        TextView txtTitle = (TextView) findViewById(R.id.txtTitleMenu);
        UtilFonts.overrideFonts(this, txtTitle, UtilFonts.IRAN_SANS_BOLD);
        txtTitle.setText(getString(R.string.app_namePR) + "(" + getString(R.string.domesticHotel) + ")");
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
                //showExpireTime();
            }
        }.start();
    }

    //-----------------------------------------------
    private void showExpireTime() {
        final DialogExpire dialogExpire = new DialogExpire();
        dialogExpire.show(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExpire.dismiss();
                timer();
                if (getIntent().hasExtra(DomesticHotelSearchRequest.class.getName()))
                    setupDomesticHotel();
                else
                    setupInternationalHotel();
            }
        });
    }
}
