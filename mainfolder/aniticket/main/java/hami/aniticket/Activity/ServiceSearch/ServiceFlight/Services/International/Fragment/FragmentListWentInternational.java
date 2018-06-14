package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Fragment;

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

import java.util.ArrayList;
import java.util.List;

import hami.hamibelit.Activity.ServiceSearch.SearchTopSheetDialogFragment;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.NewInternationalListAdapter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.FlightInternationalRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalParto;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.FinalFlightInternationalIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.PackageCompletedFlightInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi2;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Dialog.NewDesignFilterFlightInternationalFragmentDialog;
import hami.hamibelit.BaseController.CallBackRequestSearch;
import hami.hamibelit.BaseController.DividerItemDecoration;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.BaseController.SelectItemList;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilFragment;
import hami.hamibelit.Util.UtilVibrator;
import hami.hamibelit.View.HeaderBar;
import hami.hamibelit.View.MessageBar;


public class FragmentListWentInternational extends Fragment {


    private RecyclerView rvResult;
    private RelativeLayout coordinator;
    private View view;
    private FlightInternationalRequest flightRequest;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private SearchTopSheetDialogFragment searchTopSheetDialogFragment;
    private NewDesignFilterFlightInternationalFragmentDialog dialogFragmentFilter;
    private NewInternationalListAdapter newInternationalListAdapter;
    private Boolean finishParto = false, finishIati = false;
    private Boolean errorParto = false, errorIati = false;
    private PackageCompletedFlightInternational packageCompletedFlightInternational;
    private InternationalApi2 internationalApi2;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            flightRequest = (FlightInternationalRequest) getArguments().getSerializable(FlightInternationalRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            flightRequest = (FlightInternationalRequest) savedInstanceState.getSerializable(FlightInternationalRequest.class.getName());
            finishParto = savedInstanceState.getBoolean("finishParto");
            finishIati = savedInstanceState.getBoolean("finishIati");
            packageCompletedFlightInternational = (PackageCompletedFlightInternational) savedInstanceState.getSerializable(PackageCompletedFlightInternational.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentListWentInternational newInstance(FlightInternationalRequest flightRequest) {

        Bundle args = new Bundle();
        FragmentListWentInternational fragment = new FragmentListWentInternational();
        args.putSerializable(FlightInternationalRequest.class.getName(), flightRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(FlightInternationalRequest.class.getName(), flightRequest);
            outState.putBoolean("finishIati", finishIati);
            outState.putBoolean("finishParto", finishParto);
            outState.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        }
        super.onSaveInstanceState(outState);
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
    public void onPause() {
        super.onPause();
        try {
            dialogFragmentFilter.dismiss();
        } catch (Exception e) {
        }
        //setupHeaderToolbar(false);
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
        //setupHeaderToolbar(true);
    }

    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        internationalApi2 = new InternationalApi2(getActivity());
        packageCompletedFlightInternational = new PackageCompletedFlightInternational();
        packageCompletedFlightInternational.setFlightInternationalRequest(flightRequest);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_WEB);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        setupRecyclerViewNew();
        searchFlight();
    }
    //-----------------------------------------------

    private void setupRecyclerViewNew() {
        rvResult.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        newInternationalListAdapter = new NewInternationalListAdapter(getActivity(), selectItemListFlightInternational);
        rvResult.setAdapter(newInternationalListAdapter);
    }

    //-----------------------------------------------
    private void setupFilterFab(Boolean visibility) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (!visibility) {
            fab.setVisibility(View.GONE);
            return;
        }
        fab.setVisibility(View.VISIBLE);
        dialogFragmentFilter = NewDesignFilterFlightInternationalFragmentDialog.newInstance(callbacks);

        dialogFragmentFilter.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialogFragmentFilter.setInternationalFlights(mAdapter.getFullItems());
                Boolean hasTwoWays = (flightRequest.getDate2() != null && flightRequest.getDate2().length() > 0) ? true : false;
                dialogFragmentFilter.setAirline(packageCompletedFlightInternational.getLinkedTreeMapAirLineIati(), packageCompletedFlightInternational.getLinkedTreeMapAirLineParto(), hasTwoWays);
                dialogFragmentFilter.show(getActivity().getSupportFragmentManager(), dialogFragmentFilter.getTag());
            }
        });
    }

    //-----------------------------------------------
    AAH_FabulousFragment.Callbacks callbacks = new AAH_FabulousFragment.Callbacks() {
        @Override
        public void onResult(Object result) {
            try {
                if (result != null) {
                    ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                    if (applied_filters.size() > 0) {
                        ArrayList<Object> outBounds = newInternationalListAdapter.filterAll(applied_filters);
                        if (outBounds.size() == 0) {
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
                    //setupFilterTextHeader((ArrayMap<String, List<String>>) result);
                }
            } catch (Exception e) {
                resetFilter();
            }


        }
    };

    //-----------------------------------------------
    private void resetFilter() {
        dialogFragmentFilter.resetFilter();
        newInternationalListAdapter.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingWentFlight);
    }

    //-----------------------------------------------
    private Boolean hasReturn() {
        return (flightRequest != null && flightRequest.getDate2() != null && flightRequest.getDate2().length() > 0);
    }

    //-----------------------------------------------
    CallBackRequestSearch<FlightInternationalRequest> internationalRequestCallBackRequestSearch = new CallBackRequestSearch<FlightInternationalRequest>() {
        @Override
        public void getResponse(FlightInternationalRequest request) {
            if (!searchTopSheetDialogFragment.isHidden())
                searchTopSheetDialogFragment.dismiss();
            FragmentListWentInternational.this.flightRequest = request;
            packageCompletedFlightInternational.setFlightInternationalRequest(flightRequest);
            searchFlight();
        }
    };
    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    //-----------------------------------------------
    public void searchFlight() {
        if (newInternationalListAdapter != null)
            newInternationalListAdapter.clearList();
        flightRequest.setSearchType(FlightInternationalRequest.SEARCH_TYPE_PARTO);
        internationalApi2.searchFlightParto(flightRequest.toString(), searchPresenterInternational);
        flightRequest.setSearchType(FlightInternationalRequest.SEARCH_TYPE_IAEI);
        internationalApi2.searchFlightIati(flightRequest.toString(), searchPresenterInternational);
    }

    //-----------------------------------------------
    ResultSearchPresenter<AllFlightInternationalResponse> searchPresenterInternational = new ResultSearchPresenter<AllFlightInternationalResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupFilterFab(false);
                        messageBar.hideMessageBar();
                        finishParto = finishIati = false;
                        headerBar.showMessageBar(R.string.searchingFlightInternational);
                        headerBar.showProgress();
                    }
                });
            }
        }


        @Override
        public void onErrorServer(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (type == AllFlightInternationalResponse.PARTO_FLIGHT) {
                            errorParto = true;
                            finishParto = true;
                        } else if (type == AllFlightInternationalResponse.IATI_FLIGHT) {
                            errorIati = true;
                            finishIati = true;
                        }
                        if (finishParto && finishIati && errorParto && errorIati) {
                            headerBar.showMessageBar(R.string.error);
                            messageBar.showMessageBar(R.string.msgErrorServer);
                            messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                            messageBar.setTitleButton(R.string.tryAgain);
                        }
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final AllFlightInternationalResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (result.getAllFlightInternationalParto() != null) {
                            finishParto = true;
                            packageCompletedFlightInternational.setSearchIdParto(result.getSearchId());
                            packageCompletedFlightInternational.setLinkedTreeMapAirLineParto(result.getJsonObjectAirlines());
                            packageCompletedFlightInternational.setLinkedTreeMapCityParto(result.getJsonObjectCities());
                            newInternationalListAdapter.addParto(result.getAllFlightInternationalParto(), result.getJsonObjectAirlines());
                        } else if (result.getAllFlightInternationalIati() != null) {
                            finishIati = true;
                            packageCompletedFlightInternational.setSearchIdIati(result.getSearchId());
                            packageCompletedFlightInternational.setLinkedTreeMapAirLineIati(result.getJsonObjectAirlines());
                            packageCompletedFlightInternational.setLinkedTreeMapCityIati(result.getJsonObjectCities());
                            newInternationalListAdapter.addIati(result.getAllFlightInternationalIati(), result.getJsonObjectAirlines(), hasReturn());

                        }
                        if (finishIati && finishParto) {
                            setupFilterFab(true);
                            newInternationalListAdapter.sortByMoney();
                            headerBar.showMessageBar(R.string.validateSelectRoutingWentFlight);
                        }
                    }
                });
            }
        }

        @Override
        public void noResult(final int type) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (type == AllFlightInternationalResponse.PARTO_FLIGHT) {
                            finishParto = true;
                            errorParto = true;
                        } else if (type == AllFlightInternationalResponse.IATI_FLIGHT) {
                            finishIati = true;
                            errorIati = true;
                        }
                        if (finishParto && finishIati && errorParto && errorIati) {
                            messageBar.showMessageBar(R.string.msgErrorNoFlight);
                            headerBar.showMessageBar(R.string.error);
                            messageBar.setTitleButton(R.string.tryAgainSearch);
                            messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                            setupFilterFab(false);
                        }
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
                        if (finishParto && finishIati) {
                            headerBar.hideProgress();
                            new UtilVibrator(getActivity()).vibrateBySound();
                        }
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
    SelectItemList<Object> selectItemListFlightInternational = new SelectItemList<Object>() {
        @Override
        public void onSelectItem(Object object, int index) {
            if (object instanceof AllFlightInternationalParto) {
                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                        FragmentInternationalDetails.newInstance((AllFlightInternationalParto) object, packageCompletedFlightInternational));
            } else if (object instanceof FinalFlightInternationalIati) {
                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                        FragmentInternationalDetails.newInstance((FinalFlightInternationalIati) object, packageCompletedFlightInternational));
            } else if (object instanceof AllFlightInternationalIati) {
                UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                        FragmentInternationalDetails.newInstance((AllFlightInternationalIati) object, packageCompletedFlightInternational));
            }

        }
    };
    //-----------------------------------------------
}
