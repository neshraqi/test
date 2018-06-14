package hami.aniticket.Activity.PastPurchases.DomesticFlight;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



import hami.aniticket.Activity.Authentication.Controller.UserApi;
import hami.aniticket.Activity.Authentication.Controller.UserResponse;
import hami.aniticket.Activity.PastPurchases.Adapter.ReFoundMoneyFlightDomesticPassengerAdapter;
import hami.aniticket.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.aniticket.Activity.PastPurchases.Model.ReFoundResponseFlightDomestic;
import hami.aniticket.BaseController.DividerItemDecoration;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.HeaderBar;
import hami.aniticket.View.MessageBar;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class RefundMoneyFlightDomesticFragment extends Fragment {

    private HeaderBar headerBar;
    private PurchasesFlightDomestic registerFlightResponse;
    private ShimmerLayout shimmerLayout;
    private ReFoundMoneyFlightDomesticPassengerAdapter mAdapter;
    private MessageBar messageBar;
    private CardView cardViewNumberPassenger;
    private RelativeLayout layoutButtonRefundMoney;
    private ReFoundResponseFlightDomestic reFoundResponseFlightDomestic;
    private View view;
    private static final String TAG = "RefundMoneyFlightDomesticFragment";
    //-----------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            registerFlightResponse = (PurchasesFlightDomestic) getArguments().getSerializable(PurchasesFlightDomestic.class.getName());
        }

    }
    //-----------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            registerFlightResponse = (PurchasesFlightDomestic) savedInstanceState.getSerializable(PurchasesFlightDomestic.class.getName());
        }
        super.onActivityCreated(savedInstanceState);
    }
    //-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(PurchasesFlightDomestic.class.getName(), registerFlightResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static RefundMoneyFlightDomesticFragment newInstance(PurchasesFlightDomestic purchasesFlightDomestic) {

        Bundle args = new Bundle();
        RefundMoneyFlightDomesticFragment fragment = new RefundMoneyFlightDomesticFragment();
        args.putSerializable(PurchasesFlightDomestic.class.getName(), purchasesFlightDomestic);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level2_refund, container, false);
        initialComponentFragment();
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view.findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        layoutButtonRefundMoney = (RelativeLayout) view.findViewById(R.id.layoutButtonRefundMoney);
        layoutButtonRefundMoney.setVisibility(View.GONE);
        cardViewNumberPassenger = (CardView) view.findViewById(R.id.cardViewNumberPassenger);
        cardViewNumberPassenger.setVisibility(View.INVISIBLE);
        shimmerLayout = (ShimmerLayout) view.findViewById(R.id.shimmer_layout);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        headerBar.showMessageBar(R.string.whichRefundPassenger);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        layoutButtonRefundMoney.setOnClickListener(onClickListener);
        checkReFound();
    }

    //-----------------------------------------------
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.layoutButtonRefundMoney) {
                finalRefund();
            }
        }
    };

    //-----------------------------------------------
    private void setupRecyclerViewDomestic(ReFoundResponseFlightDomestic results) {
        try {
            RecyclerView rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
            rvResult.setVisibility(View.VISIBLE);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mAdapter = new ReFoundMoneyFlightDomesticPassengerAdapter(getActivity(), results);
            rvResult.setAdapter(mAdapter);
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    private void reSignIn() {
        new UserApi(getActivity()).reSignIn(new ResultSearchPresenter<UserResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //headerBar.showMessageBar(R.string.authenticating);
                            //messageBar.hideMessageBar();
                            //shimmerLayout.setVisibility(View.VISIBLE);
                            //shimmerLayout.startShimmerAnimation();
                        }
                    });

            }

            @Override
            public void onErrorServer(int type) {

                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //messageBar.showMessageBar(R.string.msgErrorServer);
                            //headerBar.showMessageBar(R.string.error);
                        }
                    });

            }

            @Override
            public void onErrorInternetConnection() {

                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                            //headerBar.showMessageBar(R.string.error);
                        }
                    });


            }

            @Override
            public void noResult(final int type) {

                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //messageBar.showMessageBar(R.string.msgErrorNoFlight);
                            //headerBar.showMessageBar(R.string.error);
                        }
                    });
            }


            @Override
            public void onSuccessResultSearch(UserResponse result) {

                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //getListPassenger();
                        }
                    });
            }


            @Override
            public void onError(final String msg) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //messageBar.showMessageBar(msg);
                            //headerBar.showMessageBar(R.string.error);
                        }
                    });

            }

            @Override
            public void onFinish() {

                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
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
    private void checkReFound() {
        new UserApi(getActivity()).checkReFoundTicket(registerFlightResponse.getId(), new ResultSearchPresenter<ReFoundResponseFlightDomestic>() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shimmerLayout.setVisibility(View.VISIBLE);
                            shimmerLayout.startShimmerAnimation();
                            headerBar.showMessageBar(R.string.gettingInfoReّound);
                        }
                    });
            }

            @Override
            public void onErrorServer(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.error);
                            messageBar.showMessageBar(R.string.msgErrorPassenger);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkReFound();
                                }
                            });
                        }
                    });
            }

            @Override
            public void onErrorInternetConnection() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.error);
                            messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkReFound();
                                }
                            });
                        }
                    });
            }

            @Override
            public void onSuccessResultSearch(final ReFoundResponseFlightDomestic result) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            reFoundResponseFlightDomestic = result;
                            setupRecyclerViewDomestic(result);
                            hasTicketRefundCompleted();
                        }
                    });

            }

            @Override
            public void noResult(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.error);
                            messageBar.showMessageBar(R.string.msgErrorPassenger);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkReFound();
                                }
                            });
                        }
                    });
            }

            @Override
            public void onError(final String msg) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.error);
                            messageBar.showMessageBar(R.string.noRefund);
                            //btnReFound.setText(R.string.noRefund);
                            //ToastMessageBar.show(RefundTicketFlightDomesticActivity.this, msg);
                        }
                    });
            }

            @Override
            public void onFinish() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shimmerLayout.stopShimmerAnimation();
                            shimmerLayout.setVisibility(View.GONE);
                            //progressGenerator.stopProgress();
                        }
                    });
            }
        });
    }

    //-----------------------------------------------
    private void finalRefund() {
        new UserApi(getActivity()).refundFinal(mAdapter.getPassengerId(), registerFlightResponse.getId(), new ResultSearchPresenter<ReFoundResponseFlightDomestic>() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setEnabled(false);
                        }
                    });
            }

            @Override
            public void onErrorServer(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.noRefund);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finalRefund();
                                }
                            });
                        }
                    });
            }


            @Override
            public void onErrorInternetConnection() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finalRefund();
                                }
                            });
                        }
                    });
            }

            @Override
            public void onSuccessResultSearch(final ReFoundResponseFlightDomestic result) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            mAdapter.update(result);
                            hasTicketRefundCompleted();
                            //UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), RefundMoneyFlightDomesticFragment.newInstance(registerFlightResponse));
                        }
                    });

            }

            @Override
            public void noResult(final int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (type == -100) {
                                reSignIn();

                            } else {
                                messageBar.showMessageBar(R.string.noRefund);
                                messageBar.setTitleButton(R.string.tryAgain);
                                messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        finalRefund();
                                    }
                                });
                            }
                        }
                    });
            }

            @Override
            public void onError(final String msg) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.noRefund);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finalRefund();
                                }
                            });

                        }
                    });
            }

            @Override
            public void onFinish() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setEnabled(true);
                        }
                    });
            }
        });
    }

    //-----------------------------------------------
    private void hasTicketRefundCompleted() {
        if (mAdapter == null)
            return;
        Boolean statusTicketPassenger = mAdapter.hasTicketRefundPassenger();
        if (statusTicketPassenger && registerFlightResponse.getRefund() == 1) {
            headerBar.showMessageBar(R.string.msgErrorRefunded);
            layoutButtonRefundMoney.setVisibility(View.GONE);
        } else {
            headerBar.showMessageBar(R.string.clickOnCompletedOperation);
            layoutButtonRefundMoney.setVisibility(View.VISIBLE);
        }
    }
}
