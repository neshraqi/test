package hami.hamibelit.Activity.ServiceHotel.Domestic;

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

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;

import java.util.ArrayList;
import java.util.List;

import hami.hamibelit.Activity.ServiceHotel.Domestic.Adapter.NewDomesticHotelListAdapter;
import hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.DomesticHotelApi;
import hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotel;
import hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelResponse;
import hami.hamibelit.Activity.ServiceHotel.Domestic.Controller.Model.DomesticHotelSearchRequest;
import hami.hamibelit.Activity.ServiceHotel.Domestic.dialog.NewDesignFilterDomesticHotelFragmentDialog;
import hami.hamibelit.BaseController.DividerItemDecoration;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.BaseController.SelectItemList;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilFragment;
import hami.hamibelit.Util.UtilVibrator;
import hami.hamibelit.View.HeaderBar;
import hami.hamibelit.View.MessageBar;


public class FragmentListDomesticHotel extends Fragment {


    private RecyclerView rvResult;
    private View view;
    private DomesticHotelSearchRequest domesticHotelSearchRequest;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private NewDesignFilterDomesticHotelFragmentDialog dialogFragmentFilter;
    private NewDomesticHotelListAdapter newDomesticHotelListAdapter;
    private DomesticHotelApi hotelApi;
    private DomesticHotelResponse domesticHotels;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            domesticHotelSearchRequest = (DomesticHotelSearchRequest) getArguments().getSerializable(DomesticHotelSearchRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            domesticHotelSearchRequest = savedInstanceState.getParcelable(DomesticHotelSearchRequest.class.getName());
        }
    }


    //-----------------------------------------------
    public static FragmentListDomesticHotel newInstance(DomesticHotelSearchRequest domesticHotelSearchRequest) {
        Bundle args = new Bundle();
        FragmentListDomesticHotel fragment = new FragmentListDomesticHotel();
        args.putSerializable(DomesticHotelSearchRequest.class.getName(), domesticHotelSearchRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(DomesticHotelSearchRequest.class.getName(), domesticHotelSearchRequest);
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
    }


    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        hotelApi = new DomesticHotelApi(getActivity());
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        searchHotel();
    }
    //-----------------------------------------------

    private void setupRecyclerViewNew(ArrayList<DomesticHotel> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingDomesticHotel);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            newDomesticHotelListAdapter = new NewDomesticHotelListAdapter(getActivity(), results, domesticHotelSelectItemList);
            rvResult.setAdapter(newDomesticHotelListAdapter);
            //setupFilterFab(true);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoDomesticHotel);
            messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
            headerBar.hideMessageBar();
        }

    }

    //-----------------------------------------------
    private void setupFilterFab(Boolean visibility) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        if (!visibility) {
            fab.setVisibility(View.GONE);
            return;
        }
        fab.setVisibility(View.VISIBLE);
        dialogFragmentFilter = NewDesignFilterDomesticHotelFragmentDialog.newInstance(callbacks);
        dialogFragmentFilter.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentFilter.setClassTypeHotel(domesticHotels.getFilterRate());
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
                        ArrayList<DomesticHotel> outBounds = newDomesticHotelListAdapter.filterAll(applied_filters);
                        if (outBounds.size() == 0) {
                            messageBar.showMessageBar(R.string.msgErrorNoHotelByFilter);
                            messageBar.setTitleButton(R.string.showAllHotels);
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
                            headerBar.showMessageBar(R.string.validateSelectRoutingDomesticHotel);
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
        newDomesticHotelListAdapter.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingDomesticHotel);
    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    //-----------------------------------------------
    public void searchHotel() {
        hotelApi.searchHotels(domesticHotelSearchRequest.getCityNameEng(), searchPresenterDomestic);
    }

    //-----------------------------------------------
    ResultSearchPresenter<DomesticHotelResponse> searchPresenterDomestic = new ResultSearchPresenter<DomesticHotelResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupFilterFab(false);
                        messageBar.showProgress(getString(R.string.searchingDomesticHotel));
                        headerBar.showMessageBar(R.string.searchingDomesticHotel);
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
                        headerBar.showMessageBar(R.string.error);
                        messageBar.showMessageBar(R.string.msgErrorServer);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgain);
                        setupFilterFab(false);
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
                        setupFilterFab(false);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final DomesticHotelResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        domesticHotels = result;
                        messageBar.hideMessageBar();
                        setupRecyclerViewNew(result.getDomesticHotels());
                        setupFilterFab(true);
                        headerBar.showMessageBar(R.string.validateSelectRoutingDomesticHotel);
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
                        messageBar.showMessageBar(R.string.msgErrorNoDomesticHotel);
                        headerBar.showMessageBar(R.string.error);
                        messageBar.setTitleButton(R.string.tryAgainSearch);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        setupFilterFab(false);

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
                        setupFilterFab(false);
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
                        messageBar.hideProgress();
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
            searchHotel();
        }
    };
    //-----------------------------------------------
    SelectItemList<DomesticHotel> domesticHotelSelectItemList = new SelectItemList<DomesticHotel>() {
        @Override
        public void onSelectItem(DomesticHotel object, int index) {
            domesticHotelSearchRequest.setHotelName(object.getHotelNameFa());
            domesticHotelSearchRequest.setHotelId(object.getHotelId());
            domesticHotelSearchRequest.setApiType(String.valueOf(object.getHotelApiType()));
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                    FragmentDetailDomesticHotel.newInstance(domesticHotelSearchRequest));
        }
    };
    //-----------------------------------------------
}
