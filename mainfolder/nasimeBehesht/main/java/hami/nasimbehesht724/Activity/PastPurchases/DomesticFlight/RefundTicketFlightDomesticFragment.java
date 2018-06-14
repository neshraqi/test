package hami.nasimbehesht724.Activity.PastPurchases.DomesticFlight;

import android.app.ProgressDialog;
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
import android.widget.Spinner;
import android.widget.TextView;



import hami.nasimbehesht724.Activity.Authentication.Controller.UserApi;
import hami.nasimbehesht724.Activity.Authentication.Controller.UserResponse;
import hami.nasimbehesht724.Activity.PastPurchases.Adapter.ReFoundListFlightDomesticPassengerAdapter;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.nasimbehesht724.Activity.PastPurchases.Model.ReFoundResponseFlightDomestic;
import hami.nasimbehesht724.Activity.PastPurchases.Model.SplitResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.nasimbehesht724.BaseController.DividerItemDecoration;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.MessageBar;
import hami.nasimbehesht724.View.ToastMessageBar;
import io.supercharge.shimmerlayout.ShimmerLayout;

public class RefundTicketFlightDomesticFragment extends Fragment {

    private HeaderBar headerBar;
    private PurchasesFlightDomestic registerFlightResponse;
    private ShimmerLayout shimmerLayout;
    private ReFoundListFlightDomesticPassengerAdapter mAdapter;
    private MessageBar messageBar;
    private CardView cardViewNumberPassenger;
    private TextView txtCountPassenger;
    private RelativeLayout layoutButtonRefund;
    private ReFoundResponseFlightDomestic reFoundResponseFlightDomestic;
    private Spinner spinnerDesc;
    private SplitResponse splitResponse;
    private View view;
    private ProgressDialog progressDialog;
    private static final String TAG = "RefundTicketFlightDomesticFragment";
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
    public static RefundTicketFlightDomesticFragment newInstance(PurchasesFlightDomestic purchasesFlightDomestic) {

        Bundle args = new Bundle();
        RefundTicketFlightDomesticFragment fragment = new RefundTicketFlightDomesticFragment();
        args.putSerializable(PurchasesFlightDomestic.class.getName(), purchasesFlightDomestic);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_level1_refund, container, false);
        initialComponentFragment();
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view.findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        layoutButtonRefund = (RelativeLayout) view.findViewById(R.id.layoutButtonRefund);
        cardViewNumberPassenger = (CardView) view.findViewById(R.id.cardViewNumberPassenger);
        cardViewNumberPassenger.setVisibility(View.INVISIBLE);
        spinnerDesc = (Spinner) view.findViewById(R.id.spinnerDesc);
        txtCountPassenger = (TextView) view.findViewById(R.id.txtCountPassenger);
        shimmerLayout = (ShimmerLayout) view.findViewById(R.id.shimmer_layout);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        headerBar.showMessageBar(R.string.whichRefundPassenger);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        layoutButtonRefund.setOnClickListener(onClickListener);
        checkReFound();
    }

    //-----------------------------------------------
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnGetTicket) {
            } else if (v.getId() == R.id.layoutButtonRefund) {
                if (mAdapter.getCountItemChecked() != 0) {
                    if (mAdapter.getItemCount() == 1 || mAdapter.getCountItemChecked() == mAdapter.getItemCount()) {
                        refundUsers(registerFlightResponse.getId());
                    } else {
                        splitPassenger();
                    }
                } else {
                    ToastMessageBar.show(getActivity(), R.string.whichRefundPassenger);
                }
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
            mAdapter = new ReFoundListFlightDomesticPassengerAdapter(getActivity(), results, selectItemList);
            rvResult.setAdapter(mAdapter);
        } catch (Exception e) {

            //messageBar.showMessageBar(R.string.msgErrorNoFlight);
            //messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
            //headerBar.hideMessageBar();
        }
    }

    //-----------------------------------------------
    SelectItemList<Integer> selectItemList = new SelectItemList<Integer>() {
        @Override
        public void onSelectItem(Integer object , int index) {
            String counter = object > 9 ? "+9" : String.valueOf(object);
            if (cardViewNumberPassenger.getVisibility() == View.INVISIBLE)
                cardViewNumberPassenger.setVisibility(View.VISIBLE);
            txtCountPassenger.setText(counter);
        }
    };

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
                        }
                    });
            }

            @Override
            public void onErrorServer(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
    private void splitPassenger() {
        String passengerId = mAdapter.getCheckedPassenger();
        new UserApi(getActivity()).splitPassenger(registerFlightResponse.getTicketId(), passengerId, new ResultSearchPresenter<SplitResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shimmerLayout.setVisibility(View.VISIBLE);
                            shimmerLayout.startShimmerAnimation();
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
                                    splitPassenger();
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
                                    checkReFound();
                                }
                            });
                        }
                    });
            }

            @Override
            public void onSuccessResultSearch(final SplitResponse result) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            splitResponse = result;
                            registerFlightResponse.setId(String.valueOf(result.getNewTicketId()));
                            refundUsers(String.valueOf(result.getNewTicketId()));
                        }
                    });

            }

            @Override
            public void noResult(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.noRefund);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    splitPassenger();
                                }
                            });
                            //ToastMessageBar.show(RefundTicketFlightDomesticActivity.this, R.string.msgErrorServer);
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
                                    splitPassenger();
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
                            shimmerLayout.stopShimmerAnimation();
                            shimmerLayout.setVisibility(View.GONE);
                            //progressGenerator.stopProgress();
                        }
                    });
            }
        });
    }

    //-----------------------------------------------
    private void refundUsers(final String newID) {
        String passengerId = mAdapter.getCheckedPassenger();
        int percent = Integer.parseInt(reFoundResponseFlightDomestic.getReFoundResponseDataFlightDomestic().getReFoundPassengerResponse().getReFoundPassengers().get(0).getJarimerefund());
        String percentType = percent == 0 ? "ziro" : "select";
        new UserApi(getActivity()).refundUser(newID, String.valueOf(percent), percentType, passengerId, spinnerDesc.getSelectedItem().toString(), new ResultSearchPresenter<BaseResult>() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog = new ProgressDialog(getActivity());
                            progressDialog.setMessage(getString(R.string.refunding));
                            progressDialog.setCancelable(false);
                            progressDialog.show();
                        }
                    });
            }

            @Override
            public void onErrorServer(int type) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.refundBySupport);
                            messageBar.setTitleButton(R.string.tryAgain);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    refundUsers(newID);
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
                                    refundUsers(newID);
                                }
                            });
                        }
                    });
            }

            @Override
            public void onSuccessResultSearch(final BaseResult result) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), RefundMoneyFlightDomesticFragment.newInstance(registerFlightResponse));
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
                                        refundUsers(newID);
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
                                    splitPassenger();
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
                            progressDialog.dismiss();
                            //progressGenerator.stopProgress();
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
            UtilFragment.changeFragment(getActivity().getSupportFragmentManager(), RefundMoneyFlightDomesticFragment.newInstance(registerFlightResponse));
//            cardViewNumberPassenger.setVisibility(View.GONE);
//            TextView btnRefund = (TextView) view.findViewById(R.id.btnRefund);
//            btnRefund.setText(R.string.msgErrorRefunded);
//            cardViewNumberPassenger.setBackgroundColor(getResources().getColor(R.color.material_green_700));
//            cardViewNumberPassenger.setOnClickListener(null);
        } else {
            layoutButtonRefund.setVisibility(View.VISIBLE);
        }
    }
}
