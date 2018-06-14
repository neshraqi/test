package hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Fragment;

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

import hami.nasimbehesht724.Activity.ServiceSearch.SearchTopSheetDialogFragment;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Dialog.NewDesignFilterBusFragmentDialog;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Adapter.ResultSearchBusListAdapter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusDataResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.SearchBusResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.BusApi;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.BusWarehouse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.ResultSearchBusPresenter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Presenter.SelectItemBus;
import hami.nasimbehesht724.BaseController.CallBackRequestSearch;
import hami.nasimbehesht724.BaseController.DividerItemDecoration;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.Util.UtilVibrator;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.MessageBar;


public class FragmentListWentBus extends Fragment {


    private ResultSearchBusListAdapter mAdapter;
    private RecyclerView rvResult;
    private RelativeLayout coordinator;
    private View view;
    private SearchBusRequest searchBusRequest;
    private BusApi busApi;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private NewDesignFilterBusFragmentDialog dialogFrag1;
    private FloatingActionButton fab;
    private SearchTopSheetDialogFragment searchTopSheetDialogFragment;
    //-----------------------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            searchBusRequest = (SearchBusRequest) getArguments().getSerializable(SearchBusRequest.class.getName());
        }

    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            searchBusRequest = (SearchBusRequest) savedInstanceState.getSerializable(SearchBusRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentListWentBus newInstance(SearchBusRequest searchBusRequest) {
        Bundle args = new Bundle();
        FragmentListWentBus fragment = new FragmentListWentBus();
        args.putSerializable(SearchBusRequest.class.getName(), searchBusRequest);
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
            dialogFrag1.dismiss();
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
        busApi = new BusApi(getActivity());
        searchBus();
    }

    //-----------------------------------------------
    private void setupFilterFab(Boolean visibility) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (!visibility) {
            fab.setVisibility(View.GONE);
            return;
        }
        fab.setVisibility(View.VISIBLE);
        dialogFrag1 = NewDesignFilterBusFragmentDialog.newInstance(callbacks);
        dialogFrag1.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrag1.show(getActivity().getSupportFragmentManager(), dialogFrag1.getTag());
            }
        });
    }

    //-----------------------------------------------
    CallBackRequestSearch<SearchBusRequest> searchBusRequestCallBackRequestSearch = new CallBackRequestSearch<SearchBusRequest>() {
        @Override
        public void getResponse(SearchBusRequest request) {
            if (!searchTopSheetDialogFragment.isHidden())
                searchTopSheetDialogFragment.dismiss();
            FragmentListWentBus.this.searchBusRequest = request;
            searchBus();
        }
    };

    //-----------------------------------------------
    private void setupRecyclerView(ArrayList<SearchBusResponse> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingWentBus);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mAdapter = new ResultSearchBusListAdapter(getActivity(), results, selectItemBus);
            setupFilterFab(true);
            rvResult.setAdapter(mAdapter);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoBus);
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
    SelectItemBus selectItemBus = new SelectItemBus() {
        @Override
        public void onSelectItemBus(SearchBusResponse searchBusResponse) {
            BusWarehouse.setCurrentSelectedSearchBusResponse(searchBusResponse);
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentListChairBus.newInstance(searchBusResponse, searchBusRequest));
        }
    };

    //-----------------------------------------------
    public void searchBus() {
        if (mAdapter != null)
            mAdapter.clearList();
        busApi.searchBus(searchBusRequest, resultSearchBusPresenter);
    }

    //-----------------------------------------------
    ResultSearchBusPresenter resultSearchBusPresenter = new ResultSearchBusPresenter() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupFilterFab(false);
                        messageBar.hideMessageBar();
                        headerBar.showProgress();
                        headerBar.showMessageBar(R.string.searchingBus);
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final SearchBusDataResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupRecyclerView(result.getListSearchBusResponse());
                        mAdapter.sortByMoney();

                    }
                });
            }
        }

        @Override
        public void noBus() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.showMessageBar(R.string.msgErrorNoBus);
                        headerBar.showMessageBar(R.string.error);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
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
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
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
                        headerBar.hideProgress();
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
            searchBus();
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
                        ArrayList<SearchBusResponse> searchBusResponses = mAdapter.filterAll(applied_filters);
                        if (searchBusResponses.size() == 0) {
                            messageBar.showMessageBar(R.string.msgErrorNoBusByFilter);
                            messageBar.setTitleButton(R.string.showAllBus);
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
        mAdapter.resetFilter();
        dialogFrag1.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingWentBus);
    }
    //-----------------------------------------------
}
