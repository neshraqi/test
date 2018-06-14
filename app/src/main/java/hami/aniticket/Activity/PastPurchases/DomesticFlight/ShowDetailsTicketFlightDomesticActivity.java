package hami.aniticket.Activity.PastPurchases.DomesticFlight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hami.aniticket.Activity.Authentication.Controller.UserApi;
import hami.aniticket.Activity.Authentication.Controller.UserResponse;
import hami.aniticket.Activity.PastPurchases.Adapter.PastPurchasesListFlightDomesticPassengerAdapter;
import hami.aniticket.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.aniticket.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.R;
import hami.aniticket.Util.CustomeChrome.CustomTabsPackages;
import hami.aniticket.Util.Hashing;
import hami.aniticket.Util.TimeDate;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.HeaderBar;
import hami.aniticket.View.MessageBar;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class ShowDetailsTicketFlightDomesticActivity extends AppCompatActivity {

    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private HeaderBar headerBar;
    private PurchasesFlightDomestic registerFlightResponse;
    private TextView tvElapsed;
    private TextView txtFlyTime, txtTimeLanding, txtTime, txtDate;
    private TextView txtFromPlace, txtToPlace;
    private TextView txtFromPlacePersian, txtToPlacePersian;
    private ShimmerLayout shimmerLayout;
    private MessageBar messageBar;
    private static final String TAG = "ShowDetailsTicketFlightDomesticActivity";
    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_tickets_flight_domestic);
        if (getIntent().hasExtra(PurchasesFlightDomestic.class.getName())) {
            registerFlightResponse = (PurchasesFlightDomestic) getIntent().getSerializableExtra(PurchasesFlightDomestic.class.getName());
        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        Button btnGetTicket = (Button) findViewById(R.id.btnGetTicket);
        messageBar = (MessageBar) findViewById(R.id.messageBar);
        shimmerLayout = (ShimmerLayout) findViewById(R.id.shimmer_layout);
        Button btnCheckRefund = (Button) findViewById(R.id.btnCheckRefund);
        headerBar = (HeaderBar) findViewById(R.id.headerBar);
        tvElapsed = (TextView) findViewById(R.id.tvElapsed);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtFromPlace = (TextView) findViewById(R.id.txtFromPlace);
        txtToPlace = (TextView) findViewById(R.id.txtToPlace);
        txtFromPlacePersian = (TextView) findViewById(R.id.txtFromPlacePersian);
        txtToPlacePersian = (TextView) findViewById(R.id.txtToPlacePersian);
        txtFromPlace.setText(registerFlightResponse.getFrom());
        txtToPlace.setText(registerFlightResponse.getTo());
        txtFromPlacePersian.setText(registerFlightResponse.getFrom_persian());
        txtToPlacePersian.setText(registerFlightResponse.getTo_persian());
        txtTime.setText(registerFlightResponse.getTtime());
        txtDate.setText(registerFlightResponse.getTdate_persian_show());
        btnCheckRefund.setOnClickListener(onClickListener);
        btnGetTicket.setOnClickListener(onClickListener);
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
//                else
//                    ToastMessageBar.show(ShowDetailsTicketFlightDomesticActivity.this, R.string.msgErrorRefunded);
            }
        }
    };

    //-----------------------------------------------
    private void showRefund() {
        Intent intent = new Intent(this, RefundTicketFlightDomesticActivity.class);
        intent.putExtra(PurchasesFlightDomestic.class.getName(), registerFlightResponse);
        startActivity(intent);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = registerFlightResponse.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "flight/pdfticket/" + ticketId + "/" + hash;
        new CustomTabsPackages(this).showUrl(url);
//        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//        CustomTabsIntent customTabsIntent = builder.build();
//        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
//        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    //-----------------------------------------------
    private void setupTimeline() {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Date date1 = new Date();
            String dateFlight = registerFlightResponse.getTdate();
            String timeFlight = registerFlightResponse.getTtime();
            Date date2 = simpleDateFormat.parse(dateFlight + " " + timeFlight);
            TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(date1, date2);
            if (resultDateDiff.getElapsedDays() >= 1) {
                String elapsed = resultDateDiff.getElapsedDays() + " روز "
                        + resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("زمان باقیمانده  تا پرواز : " + elapsed);
            } else if (resultDateDiff.getElapsedDays() == 0 && resultDateDiff.getElapsedHours() > 3) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("زمان باقیمانده تا پرواز : " + elapsed);
            } else if (resultDateDiff.getElapsedHours() == 3 || resultDateDiff.getElapsedHours() == 2) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت ";
                headerBar.showMessageBar("زمان باقیمانده تا پرواز : " + elapsed + "حرکت به سمت فرودگاه ");
                tvElapsed.setText(elapsed);
            } else if ((resultDateDiff.getElapsedHours() == 1 && resultDateDiff.getElapsedMinutes() < 10) || (resultDateDiff.getElapsedMinutes() <= 50 && resultDateDiff.getElapsedMinutes() >= 30)) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("در حال دريافت كارت پرواز");
            } else if ((resultDateDiff.getElapsedHours() == 0) || (resultDateDiff.getElapsedMinutes() < 30 && resultDateDiff.getElapsedMinutes() >= 15)) {
                String elapsed = resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("در حال سوار شدن");
            } else if ((resultDateDiff.getElapsedHours() == 0) || (resultDateDiff.getElapsedMinutes() > 0 && resultDateDiff.getElapsedMinutes() < 15)) {
                String elapsed = resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("آماده پرواز");
            } else {
                String elapsed = "اتمام";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("هواپیما از " + registerFlightResponse.getFrom_persian() + " به سمت  " + registerFlightResponse.getTo_persian() + " پرواز کرد ");
            }
        } catch (java.text.ParseException e) {

            e.printStackTrace();
        }
    }



    //-----------------------------------------------
    private void setupRecyclerViewDomestic(ArrayList<PassengerFlightDomestic> results) {
        if (results != null && results.size() > 0) {
            RecyclerView rvResult = (RecyclerView) findViewById(R.id.rvResult);
            rvResult.setVisibility(View.VISIBLE);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            //rvResult.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
            PastPurchasesListFlightDomesticPassengerAdapter mAdapter = new PastPurchasesListFlightDomesticPassengerAdapter(this, results, passengerFlightDomesticSelectItemList);
            rvResult.setAdapter(mAdapter);
        } else {
            //messageBar.showMessageBar(R.string.msgErrorNoFlight);
            //messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
            //headerBar.hideMessageBar();
        }
    }

    //-----------------------------------------------
    SelectItemList<PassengerFlightDomestic> passengerFlightDomesticSelectItemList = new SelectItemList<PassengerFlightDomestic>() {
        @Override
        public void onSelectItem(PassengerFlightDomestic object,int index) {

        }
    };

    //-----------------------------------------------
    private void getListPassenger() {
        //rvResult.setVisibility(View.GONE);
        //messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
        new UserApi(this).getPassengerListTicketDomesticFlight(registerFlightResponse.getId(), new ResultSearchPresenter<ArrayList<PassengerFlightDomestic>>() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                        shimmerLayout.setVisibility(View.VISIBLE);
                        shimmerLayout.startShimmerAnimation();
                    }
                });
            }

            @Override
            public void onErrorServer(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorPassenger);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getListPassenger();
                            }
                        });
                    }
                });
            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getListPassenger();
                            }
                        });
                    }
                });
            }

            @Override
            public void onSuccessResultSearch(final ArrayList<PassengerFlightDomestic> result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                        setupRecyclerViewDomestic(result);
                    }
                });
            }

            @Override
            public void noResult(final int type) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (type == -100) {
                            reSignIn();

                        } else {
                            messageBar.showMessageBar(R.string.msgErrorPassenger);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getListPassenger();
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onError(String msg) {

            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shimmerLayout.setVisibility(View.GONE);
                        shimmerLayout.stopShimmerAnimation();
                    }
                });
            }
        });
    }

    //-----------------------------------------------
    private void reSignIn() {
        new UserApi(this).reSignIn(new ResultSearchPresenter<UserResponse>() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                    }
                });

            }

            @Override
            public void onErrorServer(int type) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorPassenger);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reSignIn();
                            }
                        });
                    }
                });

            }

            @Override
            public void onErrorInternetConnection() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reSignIn();
                            }
                        });
                    }
                });


            }

            @Override
            public void noResult(final int type) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorPassenger);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reSignIn();
                            }
                        });
                    }
                });
            }


            @Override
            public void onSuccessResultSearch(UserResponse result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        getListPassenger();
                    }
                });
            }


            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorPassenger);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                reSignIn();
                            }
                        });
                    }
                });

            }

            @Override
            public void onFinish() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //shimmerLayout.setVisibility(View.GONE);
                        //shimmerLayout.stopShimmerAnimation();
                    }
                });
            }


        });
    }

    //-----------------------------------------------

}
