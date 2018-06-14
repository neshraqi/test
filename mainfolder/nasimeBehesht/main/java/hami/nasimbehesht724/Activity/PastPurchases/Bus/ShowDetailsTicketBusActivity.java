package hami.nasimbehesht724.Activity.PastPurchases.Bus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesBus;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.View.ToolsBusChair;
import hami.nasimbehesht724.BaseNetwork.BaseConfig;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.CustomeChrome.CustomTabsPackages;
import hami.nasimbehesht724.Util.Hashing;
import hami.nasimbehesht724.Util.TimeDate;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.View.HeaderBar;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class ShowDetailsTicketBusActivity extends AppCompatActivity {

    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private HeaderBar headerBar;
    private PurchasesBus purchasesBus;
    private TextView tvElapsed;
    private TextView txtFlyTime, txtTimeLanding, txtTime, txtDate;
    private TextView txtFromPlace, txtToPlace;
    private ShimmerLayout shimmerLayout;
    private LinearLayout chairs;
    private RelativeLayout layoutPassenger;
    private static final String TAG = "ShowDetailsTicketBusActivity";

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_tickets_bus);
        try {
            if (getIntent().hasExtra(PurchasesBus.class.getName())) {
                purchasesBus = (PurchasesBus) getIntent().getSerializableExtra(PurchasesBus.class.getName());
            }
            initialComponentActivity();
        } catch (Exception e) {

        }

    }
    //-----------------------------------------------

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(PurchasesBus.class.getName(), purchasesBus);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            purchasesBus = (PurchasesBus) savedInstanceState.getSerializable(PurchasesBus.class.getName());
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        layoutPassenger = (RelativeLayout) findViewById(R.id.layoutPassenger);
        Button btnGetTicket = (Button) findViewById(R.id.btnGetTicket);
        shimmerLayout = (ShimmerLayout) findViewById(R.id.shimmer_layout);
        Button btnCheckRefund = (Button) findViewById(R.id.btnCheckRefund);
        headerBar = (HeaderBar) findViewById(R.id.headerBar);
        tvElapsed = (TextView) findViewById(R.id.tvElapsed);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtFromPlace = (TextView) findViewById(R.id.txtFromPlace);
        txtToPlace = (TextView) findViewById(R.id.txtToPlace);
//        txtFromPlacePersian = (TextView) findViewById(R.id.txtFromPlacePersian);
//        txtToPlacePersian = (TextView) findViewById(R.id.txtToPlacePersian);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(5);
        txtFromPlace.setFilters(fArray);
        txtToPlace.setFilters(fArray);
        txtFromPlace.setText(purchasesBus.getFrom());
        txtToPlace.setText(purchasesBus.getTo());
//        txtFromPlacePersian.setText(purchasesBus.getFrom());
//        txtToPlacePersian.setText(purchasesBus.getTo());
        txtTime.setText(purchasesBus.getTtime());
        txtDate.setText(purchasesBus.getTdate_persian_show());
        btnCheckRefund.setVisibility(View.GONE);
        //btnCheckRefund.setOnClickListener(onClickListener);
        btnGetTicket.setOnClickListener(onClickListener);
        setupToolbar();
        setupTimeline();
        getListPassenger();
    }

    //-----------------------------------------------
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnGetTicket) {
                getTicket();
            } else if (v.getId() == R.id.btnCheckRefund) {
                showRefund();
            }
        }
    };

    //-----------------------------------------------
    private void showRefund() {
//        Intent intent = new Intent(this, RefundTicketFlightDomesticActivity.class);
//        intent.putExtra(PurchasesFlightDomestic.class.getName(), registerFlightResponse);
//        startActivity(intent);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = purchasesBus.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "bus/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(this).showUrl(url);
    }

    //-----------------------------------------------
    private void setupTimeline() {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date1 = new Date();
            String dateFlight = purchasesBus.getTdate();
            String timeFlight = purchasesBus.getTtime();
            Date date2 = simpleDateFormat.parse(dateFlight + " " + timeFlight);
            TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(date1, date2);
            if (resultDateDiff.getElapsedDays() >= 1) {
                String elapsed = resultDateDiff.getElapsedDays() + " روز "
                        + resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("زمان باقیمانده  تا حرکت : " + elapsed);
            } else if (resultDateDiff.getElapsedDays() == 0 && resultDateDiff.getElapsedHours() > 3) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("زمان باقیمانده تا حرکت : " + elapsed);
            } else if (resultDateDiff.getElapsedHours() == 3 || resultDateDiff.getElapsedHours() == 2) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت ";
                headerBar.showMessageBar("زمان باقیمانده تا حرکت : " + elapsed + "حرکت به سمت ترمینال ");
                tvElapsed.setText(elapsed);
            } else if ((resultDateDiff.getElapsedHours() == 1 && resultDateDiff.getElapsedMinutes() < 10) || (resultDateDiff.getElapsedMinutes() <= 50 && resultDateDiff.getElapsedMinutes() >= 30)) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("در حال بررسی مسافر");
            } else if ((resultDateDiff.getElapsedHours() == 0) || (resultDateDiff.getElapsedMinutes() < 30 && resultDateDiff.getElapsedMinutes() >= 15)) {
                String elapsed = resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("در حال سوار شدن");
            } else if ((resultDateDiff.getElapsedHours() == 0) || (resultDateDiff.getElapsedMinutes() > 0 && resultDateDiff.getElapsedMinutes() < 15)) {
                String elapsed = resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("آماده حرکت");
            } else {
                String elapsed = "اتمام";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("اتوبوس از " + purchasesBus.getFrom() + " به سمت  " + purchasesBus.getTo() + " حرکت کرد ");
            }
        } catch (java.text.ParseException e) {

            e.printStackTrace();
        }
    }


    //-----------------------------------------------
    private void setupToolbar() {
        txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        txtSubTitleMenu = (TextView) findViewById(R.id.txtSubTitleMenu);
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTitleMenu.setText("جزییات بلیط اتوبوس");
        txtSubTitleMenu.setText("بلیط اتوبوس " + purchasesBus.getId());
    }


    //-----------------------------------------------
    private void getListPassenger() {
        shimmerLayout.setVisibility(View.GONE);
        shimmerLayout.stopShimmerAnimation();
        setupChairs();
    }

    //-----------------------------------------------
    private void setupChairs() {
        layoutPassenger.removeAllViews();
        LinearLayout chairs = new LinearLayout(this);
        chairs.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams ladderParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
        chairs.setLayoutParams(ladderParams);
        String[] chairsString = purchasesBus.getChairs().split(",");
        for (int i = 0; i < chairsString.length; i++) {
            String number = chairsString[i].split("/")[0];
            ToolsBusChair toolsBusChair = new ToolsBusChair(this, ToolsBusChair.CHAIR_TYPE_CAN_SELECT, number);
            chairs.addView(toolsBusChair);
        }
        layoutPassenger.addView(chairs);
    }

    //-----------------------------------------------

}
