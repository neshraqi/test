package hami.mainapp.Activity.PastPurchases.Train;

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

import hami.mainapp.Activity.Authentication.Controller.UserApi;
import hami.mainapp.Activity.Authentication.Controller.UserResponse;
import hami.mainapp.Activity.PastPurchases.Adapter.PastPurchasesListTrainPassengerAdapter;
import hami.mainapp.Activity.PastPurchases.DomesticFlight.RefundTicketFlightDomesticActivity;
import hami.mainapp.Activity.PastPurchases.Model.PassengerFlightDomestic;
import hami.mainapp.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.mainapp.Activity.PastPurchases.Model.PurchasesTrain;
import hami.mainapp.BaseController.ResultSearchPresenter;
import hami.mainapp.BaseController.SelectItemList;
import hami.mainapp.BaseNetwork.BaseConfig;
import hami.mainapp.R;
import hami.mainapp.Util.CustomeChrome.CustomTabsPackages;
import hami.mainapp.Util.Hashing;
import hami.mainapp.Util.TimeDate;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.View.HeaderBar;
import hami.mainapp.View.MessageBar;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class ShowDetailsTicketTrainActivity extends AppCompatActivity {

    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private HeaderBar headerBar;
    private PurchasesTrain purchasesTrain;
    private TextView tvElapsed;
    private TextView txtFlyTime, txtTimeLanding, txtTime, txtDate;
    private TextView txtFromPlace, txtToPlace;
    //    private TextView txtFromPlacePersian, txtToPlacePersian;
    private ShimmerLayout shimmerLayout;
    private MessageBar messageBar;
    private static final String TAG = "ShowDetailsTicketTrainActivity";

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details_tickets_train);
        if (getIntent().hasExtra(PurchasesTrain.class.getName())) {
            purchasesTrain = (PurchasesTrain) getIntent().getSerializableExtra(PurchasesTrain.class.getName());
        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(PurchasesTrain.class.getName(), purchasesTrain);
        }
        super.onSaveInstanceState(outState);
    }
    //-----------------------------------------------

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            purchasesTrain = (PurchasesTrain) savedInstanceState.getSerializable(PurchasesTrain.class.getName());
        }
        super.onRestoreInstanceState(savedInstanceState);
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
//        txtFromPlacePersian = (TextView) findViewById(R.id.txtFromPlacePersian);
//        txtToPlacePersian = (TextView) findViewById(R.id.txtToPlacePersian);
        txtFromPlace.setText(purchasesTrain.getFrom_persian());
        txtToPlace.setText(purchasesTrain.getTo_persian());
//        txtFromPlacePersian.setText(purchasesTrain.getFrom_persian());
//        txtToPlacePersian.setText(purchasesTrain.getTo_persian());
        txtTime.setText(purchasesTrain.getTtime());
        txtDate.setText(purchasesTrain.getTdate_persian_show());
        btnCheckRefund.setVisibility(View.GONE);
        //btnCheckRefund.setOnClickListener(onClickListener);
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
            }
        }
    };

    //-----------------------------------------------
    private void showRefund() {
        Intent intent = new Intent(this, RefundTicketFlightDomesticActivity.class);
        intent.putExtra(PurchasesFlightDomestic.class.getName(), purchasesTrain);
        startActivity(intent);
    }

    //-----------------------------------------------
    public void getTicket() {
        String ticketId = purchasesTrain.getId();
        String hash = Hashing.getHash(ticketId);
        String url = BaseConfig.BASE_URL_MASTER + "train/pdfticket/" + ticketId + "/" + hash;
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
            String dateFlight = purchasesTrain.getTdate();
            String timeFlight = purchasesTrain.getTtime();
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
                headerBar.showMessageBar("زمان باقیمانده تا حرکت : " + elapsed + "حرکت به سمت راه آهن ");
                tvElapsed.setText(elapsed);
            } else if ((resultDateDiff.getElapsedHours() == 1 && resultDateDiff.getElapsedMinutes() < 10) || (resultDateDiff.getElapsedMinutes() <= 50 && resultDateDiff.getElapsedMinutes() >= 30)) {
                String elapsed = resultDateDiff.getElapsedHours() + " ساعت "
                        + resultDateDiff.getElapsedMinutes() + " دقیقه ";
                tvElapsed.setText(elapsed);
                headerBar.showMessageBar("در حال دريافت كارت حرکت");
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
                headerBar.showMessageBar("قطار از " + purchasesTrain.getFrom_persian() + " به سمت  " + purchasesTrain.getTo_persian() + " حرکت کرد ");
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
            PastPurchasesListTrainPassengerAdapter mAdapter = new PastPurchasesListTrainPassengerAdapter(this, results, passengerFlightDomesticSelectItemList);
            rvResult.setAdapter(mAdapter);
        } else {
            //messageBar.showMessageBar(R.string.msgErrorNoFlight);
            //messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
            //headerBar.hideMessageBar();
        }
    }

    //-----------------------------------------------
    private SelectItemList<PassengerFlightDomestic> passengerFlightDomesticSelectItemList = new SelectItemList<PassengerFlightDomestic>() {
        @Override
        public void onSelectItem(PassengerFlightDomestic object, int index) {

        }
    };

    //-----------------------------------------------
    private void getListPassenger() {
        //rvResult.setVisibility(View.GONE);
        //messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
        new UserApi(this).getPassengerListTicketTrain(purchasesTrain.getId(), new ResultSearchPresenter<ArrayList<PassengerFlightDomestic>>() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.setVisibility(View.VISIBLE);
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
