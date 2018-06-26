package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Fragment;

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
import java.util.HashMap;
import java.util.List;

import hami.nasimbehesht724.Activity.ServiceSearch.SearchTopSheetDialogFragment;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.NewDesignFilterTrainFragmentDialog;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Adapter.TrainListAdapter;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainDataResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainResponse;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter.SelectItemTrainSearchListener;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter.TrainApi;
import hami.nasimbehesht724.BaseController.CallBackRequestSearch;
import hami.nasimbehesht724.BaseController.DividerItemDecoration;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.Util.UtilVibrator;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.MessageBar;
import hami.nasimbehesht724.View.ToastMessageBar;


public class FragmentListWentTrain extends Fragment {

    //-----------------------------------------------
    private TrainListAdapter mAdapter;
    private RecyclerView rvResult;
    private RelativeLayout coordinator;
    private View view;
    private TrainRequest trainRequest;
    private MessageBar messageBar;
    private HeaderBar headerBar;
    private NewDesignFilterTrainFragmentDialog newDesignFilterTrainFragmentDialog;
    private SearchTopSheetDialogFragment searchTopSheetDialogFragment;
    private HashMap<String, String> trainCompanyHashMap;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            trainRequest = (TrainRequest) getArguments().getSerializable(TrainRequest.class.getName());
        }

    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            trainRequest = (TrainRequest) savedInstanceState.getSerializable(TrainRequest.class.getName());
        }
    }

    //-----------------------------------------------
    public static FragmentListWentTrain newInstance(TrainRequest trainRequest) {
        Bundle args = new Bundle();
        FragmentListWentTrain fragment = new FragmentListWentTrain();
        args.putSerializable(TrainRequest.class.getName(), trainRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(TrainRequest.class.getName(), trainRequest);
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
            newDesignFilterTrainFragmentDialog.dismiss();
        } catch (Exception e) {
        }
    }

    //-----------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
    }


    //-----------------------------------------------
    private void initialComponentFragment() {
        coordinator = (RelativeLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), coordinator, UtilFonts.IRAN_SANS_WEB);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        searchTrain();
    }

    //-----------------------------------------------
    View.OnClickListener callbackMessageBaClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().finish();
        }
    };

    //-----------------------------------------------
    private void setupRecyclerView(ArrayList<TrainResponse> results) {
        if (results != null && results.size() > 0) {
            headerBar.showMessageBar(R.string.validateSelectRoutingWentTrain);
            rvResult.setHasFixedSize(true);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
            mAdapter = new TrainListAdapter(getActivity(), results, selectItemTrainSearchListener);
            setupFilterFab(true);
            rvResult.setAdapter(mAdapter);
        } else {
            messageBar.showMessageBar(R.string.msgErrorNoTrain);
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
        newDesignFilterTrainFragmentDialog = NewDesignFilterTrainFragmentDialog.newInstance(callbacks);
        newDesignFilterTrainFragmentDialog.setParentFab(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDesignFilterTrainFragmentDialog.setTrain(trainCompanyHashMap);
                newDesignFilterTrainFragmentDialog.show(getActivity().getSupportFragmentManager(), newDesignFilterTrainFragmentDialog.getTag());
            }
        });
    }

    //-----------------------------------------------
    public void searchTrain() {
        if (mAdapter != null)
            mAdapter.clearList();
        new TrainApi(getActivity()).searchTrain(trainRequest, resultSearchPresenter);
    }

    //-----------------------------------------------
    AAH_FabulousFragment.Callbacks callbacks = new AAH_FabulousFragment.Callbacks() {
        @Override
        public void onResult(Object result) {
            try {
                if (result != null) {
                    ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                    if (applied_filters.size() > 0) {
                        ArrayList<TrainResponse> outBounds = mAdapter.filterAll(applied_filters);
                        if (outBounds.size() == 0) {
                            messageBar.showMessageBar(R.string.msgErrorNoTrainByFilter);
                            messageBar.setTitleButton(R.string.showAllTrains);
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
            }
            catch (Exception e){
                resetFilter();
            }


        }
    };
    //-----------------------------------------------
    ResultSearchPresenter<TrainDataResponse> resultSearchPresenter = new ResultSearchPresenter<TrainDataResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupFilterFab(false);
                        messageBar.hideMessageBar();
                        headerBar.showMessageBar(R.string.searchingTrain);
                        headerBar.showProgress();
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
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
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
                        messageBar.showMessageBar(R.string.msgErrorNoTrain);
                        headerBar.showMessageBar(R.string.error);
                        messageBar.setTitleButton(R.string.tryAgainSearch);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
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
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(final TrainDataResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupRecyclerView(result.getTrainResponseList());
                        trainCompanyHashMap = result.getTrainCompany();
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
                        messageBar.setTitleButton(R.string.tryAgain);
                        messageBar.setCallbackButtonNewSearch(callbackRetryMessageBarClickListener);
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
            searchTrain();
        }
    };
    //-----------------------------------------------
    SelectItemTrainSearchListener selectItemTrainSearchListener = new SelectItemTrainSearchListener() {
        @Override
        public void onSelectItemTrain(TrainResponse trainResponse) {
            if (trainRequest.isCope() && !trainResponse.getIsCompartment().contentEquals("1")) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorTrainIsHall);
                return;
            }
            int capacity = Integer.valueOf(trainResponse.getCapacity().replace("+", ""));
            if (capacity == 0) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorFullCapacity);
                return;
            }
            if (capacity < Integer.valueOf(trainRequest.getCountPassengerTrain())) {
                ToastMessageBar.show(getActivity(), R.string.msgErrorByCapacity);
                return;
            }
            if (trainRequest.isCope() && trainResponse.getIsCope().contentEquals("1")) {
                int compartmentCapicity = Integer.valueOf(trainResponse.getCompartmentCapicity());
                if (capacity < compartmentCapicity) {
                    //messageBar.showMessageBar(R.string.msgErrorFullCapacityCope);
                    ToastMessageBar.show(getActivity(), R.string.msgErrorFullCapacityCope);
                    return;
                }
            }
            UtilFragment.addNewFragment(getActivity().getSupportFragmentManager(), FragmentTrainDetails.newInstance(trainResponse, trainRequest));

        }
    };


    //-----------------------------------------------
    CallBackRequestSearch<TrainRequest> trainRequestCallBackRequestSearch = new CallBackRequestSearch<TrainRequest>() {
        @Override
        public void getResponse(TrainRequest request) {
            if (!searchTopSheetDialogFragment.isHidden())
                searchTopSheetDialogFragment.dismiss();
            FragmentListWentTrain.this.trainRequest = request;
            searchTrain();
        }
    };

    //-----------------------------------------------
    private void resetFilter() {
        mAdapter.resetFilter();
        newDesignFilterTrainFragmentDialog.resetFilter();
        messageBar.hideMessageBar();
        messageBar.setCallbackButtonNewSearch(callbackMessageBaClickListener);
        headerBar.showMessageBar(R.string.validateSelectRoutingWentTrain);
    }
}
