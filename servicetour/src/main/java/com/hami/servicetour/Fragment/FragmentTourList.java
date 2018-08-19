package com.hami.servicetour.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.hami.common.BaseController.DividerItemDecoration;
import com.hami.common.BaseController.ResultSearchPresenter;
import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.common.View.HeaderBar;
import com.hami.common.View.MessageBar;
import com.hami.servicetour.Adapter.TourListAdapter;
import com.hami.servicetour.Controller.Model.SearchTourRequest;
import com.hami.servicetour.Controller.Model.TourItem;
import com.hami.servicetour.Controller.Model.TourItemsResponse;
import com.hami.servicetour.Controller.TourApi;
import com.hami.servicetour.R;

import java.util.ArrayList;


public class FragmentTourList extends Fragment {

    private RecyclerView rvResult;
    private RelativeLayout coordinator;
    private View view;
    private FloatingActionButton fab;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private TourApi tourApi;
    private SearchTourRequest searchTourRequest;
    private TourItemsResponse tourItemsResponse;
    private TourListAdapter tourListAdapter;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            searchTourRequest = getArguments().getParcelable(SearchTourRequest.class.getName());
        }

    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {

        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {

        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public static FragmentTourList newInstance(SearchTourRequest searchTourRequest) {
        Bundle args = new Bundle();
        FragmentTourList fragment = new FragmentTourList();
        args.putParcelable(SearchTourRequest.class.getName(), searchTourRequest);
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
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_WEB);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        tourApi = new TourApi(getActivity());
        searchTour();

    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };


    //-----------------------------------------------
    private void searchTour() {
        tourApi.searchTour(searchTourRequest, new ResultSearchPresenter<TourItemsResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //setupFilterFab(false);
                            messageBar.showProgress(getString(R.string.searchingTour));
                            headerBar.showMessageBar(R.string.searchingTour);
                        }
                    });
                }
            }

            @Override
            public void onErrorServer(int type) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.msgErrorServer);
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
                            headerBar.showMessageBar(R.string.error);
                        }
                    });

                }
            }


            @Override
            public void onSuccessResultSearch(final TourItemsResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tourItemsResponse = result;
                            messageBar.hideMessageBar();
                            setupRecyclerView(result.getTourItems());
                        }
                    });
                }
            }

            @Override
            public void noResult(int type) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showMessageBar(R.string.msgErrorNoTour);
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
                            messageBar.hideProgress();
                        }
                    });
                }
            }
        });
    }

    //-----------------------------------------------
    private void setupRecyclerView(ArrayList<TourItem> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingWentTour);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            tourListAdapter = new TourListAdapter(getActivity(), results, tourItemSelectItemList);
            rvResult.setAdapter(tourListAdapter);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoTour);
            messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
            headerBar.hideMessageBar();
        }
    }

    //-----------------------------------------------
    private SelectItemList<TourItem> tourItemSelectItemList = new SelectItemList<TourItem>() {
        @Override
        public void onSelectItem(TourItem object, int position) {
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentTourDetail.newInstance(object.getId(), object.getName()), R.id.frame_Layout);
        }
    };
}
