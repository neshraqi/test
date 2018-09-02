package com.hami.servicehotel.International;

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
import com.hami.common.BaseController.DividerItemDecoration;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.Util.UtilVibrator;
import com.hami.common.View.HeaderBarLtr;
import com.hami.common.View.MessageBar;
import com.hami.servicehotel.International.Adapter.NewInternationalHotelListAdapter;
import com.hami.servicehotel.International.Controller.InternationalHotelApi;
import com.hami.servicehotel.International.Controller.Model.InternationalHotel;
import com.hami.servicehotel.International.Controller.Model.InternationalHotelSearchRequest;
import com.hami.servicehotel.International.Controller.Model.InternationalHotelsResponse;
import com.hami.servicehotel.International.Dialog.NewDesignFilterInternationalHotelFragmentDialog;
import com.hami.servicehotel.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentListInternationalHotel extends Fragment {


    private RecyclerView rvResult;
    private View view;
    private String hotelSearchRequestJson;
    private InternationalHotelSearchRequest hotelSearchRequest;
    private MessageBar messageBar;
    private HeaderBarLtr headerBar;
    private NewDesignFilterInternationalHotelFragmentDialog dialogFragmentFilter;
    private NewInternationalHotelListAdapter newHotelListAdapter;
    private InternationalHotelApi hotelApi;
    private InternationalHotelsResponse hotelsResponse;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            hotelSearchRequest = getArguments().getParcelable(InternationalHotelSearchRequest.class.getName());
            hotelSearchRequestJson = getArguments().getString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hotelSearchRequest = savedInstanceState.getParcelable(InternationalHotelSearchRequest.class.getName());
            hotelSearchRequestJson = savedInstanceState.getString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST);
        }
    }


    //-----------------------------------------------
    public static FragmentListInternationalHotel newInstance(String hotelSearchRequestJson, InternationalHotelSearchRequest hotelSearchRequest) {
        Bundle args = new Bundle();
        FragmentListInternationalHotel fragment = new FragmentListInternationalHotel();
        args.putParcelable(InternationalHotelSearchRequest.class.getName(), hotelSearchRequest);
        args.putString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, hotelSearchRequestJson);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(InternationalHotelSearchRequest.class.getName(), hotelSearchRequest);
            outState.putString(InternationalHotelSearchRequest.INTENT_HOTEL_SEARCH_REQUEST, hotelSearchRequestJson);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main_result_search_ltr, container, false);
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
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.TAHOMA);
        hotelApi = new InternationalHotelApi(getActivity());
        headerBar = (HeaderBarLtr) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        searchHotel();
    }
    //-----------------------------------------------

    private void setupRecyclerViewNew(ArrayList<InternationalHotel> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingHotelEng);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            newHotelListAdapter = new NewInternationalHotelListAdapter(getActivity(), results, selectItemListHotelInternational);
            rvResult.setAdapter(newHotelListAdapter);
            //setupFilterFab(true);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoHotelInternationalEng);
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
        dialogFragmentFilter = NewDesignFilterInternationalHotelFragmentDialog.newInstance(callbacks);
        dialogFragmentFilter.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentFilter.setClassOffer(hotelsResponse.getToolsHotelFilter().getFilterOffer());
                dialogFragmentFilter.setClassTypeHotel(hotelsResponse.getToolsHotelFilter().getFilterRate());
                dialogFragmentFilter.show(getActivity().getSupportFragmentManager(), dialogFragmentFilter.getTag());
            }
        });
    }

    //-----------------------------------------------
    AAH_FabulousFragment.Callbacks callbacks = new AAH_FabulousFragment.Callbacks() {
        @Override
        public void onResult(Object result) {
            if (result != null) {
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if (applied_filters.size() > 0) {
                    ArrayList<InternationalHotel> outBounds = newHotelListAdapter.filterAll(applied_filters);
                    if (outBounds.size() == 0) {
                        messageBar.showMessageBar(R.string.msgErrorNoFlightByFilterEng);
                        messageBar.setTitleButton(R.string.showAllHotelsEng);
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
                        headerBar.showMessageBar(R.string.validateSelectRoutingHotelEng);
                    }
                } else {
                    resetFilter();
                }
                //setupFilterTextHeader((ArrayMap<String, List<String>>) result);
            }

        }
    };

    //-----------------------------------------------
    private void resetFilter() {
        dialogFragmentFilter.resetFilter();
        newHotelListAdapter.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingHotelEng);
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
        hotelApi.searchHotels(hotelSearchRequestJson, searchPresenterInternational);
    }

    //-----------------------------------------------
    ResultSearchPresenter<InternationalHotelsResponse> searchPresenterInternational = new ResultSearchPresenter<InternationalHotelsResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //setupFilterFab(false);
                        messageBar.showProgress(getString(R.string.searchingHotelInternationalEng));
                        headerBar.showMessageBar(R.string.searchingHotelInternationalEng);
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
                        headerBar.showMessageBar(R.string.errorEng);
                        messageBar.showMessageBar(R.string.msgErrorServerEng);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgainEng);
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
                        headerBar.showMessageBar(R.string.errorEng);
                        messageBar.showMessageBar(R.string.msgErrorInternetConnectionEng);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                        messageBar.setTitleButton(R.string.tryAgainEng);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final InternationalHotelsResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotelsResponse = result;
                        messageBar.hideMessageBar();
                        setupRecyclerViewNew(result.getHotels());
                        setupFilterFab(true);
                        headerBar.showMessageBar(R.string.validateSelectRoutingHotelEng);
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
                        messageBar.showMessageBar(R.string.msgErrorNoHotelInternationalEng);
                        headerBar.showMessageBar(R.string.errorEng);
                        messageBar.setTitleButton(R.string.tryAgainSearchEng);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
                        //setupFilterFab(false);

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
                        headerBar.showMessageBar(R.string.errorEng);
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
    SelectItemList<InternationalHotel> selectItemListHotelInternational = new SelectItemList<InternationalHotel>() {
        @Override
        public void onSelectItem(InternationalHotel object, int index) {
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(),
                    FragmentDetailInternationalHotel.newInstance(hotelSearchRequestJson, hotelSearchRequest, object), R.id.frame_Layout);
        }
    };
    //-----------------------------------------------
}
