package com.hami.serviceflight.Services.Domestic.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.hami.common.BaseController.CallBackRequestSearch;
import com.hami.common.BaseController.DividerItemDecoration;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.Util.UtilVibrator;
import com.hami.common.View.HeaderBar;
import com.hami.common.View.MessageBar;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.Domestic.Adapter.NewDomesticListAdapter;
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticFlight;
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticFlightResponse;
import com.hami.serviceflight.Services.Domestic.Controller.Model.DomesticRequest;
import com.hami.serviceflight.Services.Domestic.Controller.Presenter.DomesticApi;
import com.hami.serviceflight.Services.Domestic.Controller.Presenter.ResultSearchDomesticPresenter;
import com.hami.serviceflight.Services.Domestic.Controller.Presenter.SelectItemFlightDomestic;
import com.hami.serviceflight.Services.Domestic.Dialog.NewDesignFilterFlightDomesticFragmentDialog;
import com.hami.serviceflight.Services.International.Controller.Model.SearchInternational;

import java.util.ArrayList;
import java.util.List;


public class FragmentListWentDomestic extends Fragment {

    private NewDomesticListAdapter mAdapter;
    private RecyclerView rvResult;
    private RelativeLayout coordinator;
    private View view;
    private DomesticRequest domesticRequest;
    private DomesticFlightResponse domesticFlightResponse;
    private FloatingActionButton fab;
    private NewDesignFilterFlightDomesticFragmentDialog dialogFragmentFilter;
    private DomesticApi domesticApi;
    private MessageBar messageBar;
    private HeaderBar headerBar;
  //  private SearchTopSheetDialogFragment searchTopSheetDialogFragment;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            domesticRequest = (DomesticRequest) getArguments().getSerializable(DomesticRequest.class.getName());
        }

    }

    //-----------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            domesticRequest = (DomesticRequest) savedInstanceState.getSerializable(DomesticRequest.class.getName());
            domesticFlightResponse = (DomesticFlightResponse) savedInstanceState.getParcelable(DomesticFlightResponse.class.getName());
        }
    }

    //-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (outState != null) {
            outState.putSerializable(DomesticRequest.class.getName(), domesticRequest);
            outState.putParcelable(DomesticFlightResponse.class.getName(), domesticFlightResponse);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------

    public static FragmentListWentDomestic newInstance(DomesticRequest domesticRequest, SearchInternational fromSearchInternational, SearchInternational toSearchInternational) {
        Bundle args = new Bundle();
        FragmentListWentDomestic fragment = new FragmentListWentDomestic();
        args.putSerializable(DomesticRequest.class.getName(), domesticRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------

    public static FragmentListWentDomestic newInstance(DomesticRequest domesticRequest) {
        Bundle args = new Bundle();
        FragmentListWentDomestic fragment = new FragmentListWentDomestic();
        args.putSerializable(DomesticRequest.class.getName(), domesticRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main_result_search, container, false);
            initialComponentFragment();
        }
        return view;
    }
    //-----------------------------------------------

    @Override
    public void onResume() {
        super.onResume();
    }

    //-----------------------------------------------

    @Override
    public void onPause() {
        super.onPause();
        try {
            dialogFragmentFilter.dismiss();
        } catch (Exception e) {
        }
    }


    //-----------------------------------------------

    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_WEB);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        domesticApi = new DomesticApi(getActivity());
        searchFlight();

    }

    //-----------------------------------------------

    private void setupFilterFab(Boolean visibility) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (!visibility) {
            fab.setVisibility(View.GONE);
            return;
        }
        fab.setVisibility(View.VISIBLE);
        dialogFragmentFilter = NewDesignFilterFlightDomesticFragmentDialog.newInstance(callbacks);
        dialogFragmentFilter.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentFilter.setFlights(domesticFlightResponse.getAirlineList());
                dialogFragmentFilter.show(getActivity().getSupportFragmentManager(), dialogFragmentFilter.getTag());
            }
        });
    }

    //-----------------------------------------------

//    CallBackRequestSearch<DomesticRequest> domesticRequestCallBackRequestSearch = new CallBackRequestSearch<DomesticRequest>() {
//        @Override
//        public void getResponse(DomesticRequest request) {
//            if (!searchTopSheetDialogFragment.isHidden())
//                searchTopSheetDialogFragment.dismiss();
//            FragmentListWentDomestic.this.domesticRequest = request;
//            searchFlight();
//        }
//    };

    //-----------------------------------------------
    private void setupRecyclerView(ArrayList<DomesticFlight> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingWentFlight);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mAdapter = new NewDomesticListAdapter(getActivity(), results, selectItemFlightDomestic);
            setupFilterFab(true);
            rvResult.setAdapter(mAdapter);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoFlight);
            messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
            headerBar.hideMessageBar();
        }
    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    //-----------------------------------------------
    public void searchFlight() {
        if (mAdapter != null)
            mAdapter.clearList();
        domesticApi.searchFlight(domesticRequest, resultSearchPresenter);
    }

    //-----------------------------------------------
    ResultSearchDomesticPresenter resultSearchPresenter = new ResultSearchDomesticPresenter() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupFilterFab(false);
                        messageBar.hideMessageBar();
                        headerBar.showMessageBar(R.string.searchingFlightDomestic);
                        headerBar.showProgress();
                    }
                });
            }
        }

        @Override
        public void onErrorServer() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                        headerBar.showMessageBar(R.string.error);
                    }
                });
            }
        }

        @Override
        public void onErrorInternetConnection() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        headerBar.showMessageBar(R.string.error);
                    }
                });

            }
        }

        @Override
        public void onSuccessResultSearch(final DomesticFlightResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        domesticFlightResponse = result;
                        setupRecyclerView(result.getDomesticFlights());
                    }
                });
            }
        }

        @Override
        public void noFlight() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorNoFlight);
                        messageBar.setTitleButton(R.string.tryAgainSearch);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        headerBar.showMessageBar(R.string.error);
                    }
                });
            }
        }

        @Override
        public void onError(final String msg) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(msg);
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        headerBar.showMessageBar(R.string.error);
                    }
                });
            }
        }

        @Override
        public void onFinish() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.hideMessageBar();
                        new UtilVibrator(getActivity()).vibrateBySound();
                    }
                });
            }
        }
    };
    //-----------------------------------------------
    View.OnClickListener callbackRetryMessageBarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            searchFlight();
        }
    };
    //-----------------------------------------------
    SelectItemFlightDomestic selectItemFlightDomestic = new SelectItemFlightDomestic() {
        @Override
        public void onSelectItemFlight(DomesticFlight domesticFlight) {
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentDomesticDetails.newInstance(domesticFlight, domesticRequest), R.id.frame_Layout);
        }
    };

    //-----------------------------------------------
    AAH_FabulousFragment.Callbacks callbacks = new AAH_FabulousFragment.Callbacks() {
        @Override
        public void onResult(Object result) {
            try {
                if (result != null) {
                    ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                    if (applied_filters.size() > 0) {
                        ArrayList<DomesticFlight> domesticFlights = mAdapter.filterAll(applied_filters);
                        if (domesticFlights.size() == 0) {
                            messageBar.showMessageBar(R.string.msgErrorNoFlightByFilter);
                            messageBar.setTitleButton(R.string.showAllFlights);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    resetFilter();
                                }
                            });
                            headerBar.hideMessageBar();
                        } else {
                            messageBar.hideMessageBar();
                            messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                            headerBar.showMessageBar(R.string.validateSelectRoutingWentFlight);
                        }
                    } else {
                        resetFilter();
                    }
                }
            } catch (Exception e) {
                resetFilter();
            }
        }
    };

    //-----------------------------------------------
    private void resetFilter() {
        dialogFragmentFilter.resetFilter();
        mAdapter.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingWentFlight);
    }


}
