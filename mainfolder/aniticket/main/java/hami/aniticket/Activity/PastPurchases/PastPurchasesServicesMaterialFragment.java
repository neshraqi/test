package hami.nasimbehesht724.Activity.PastPurchases;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hami.nasimbehesht724.Activity.Authentication.Controller.UserApi;
import hami.nasimbehesht724.Activity.Authentication.Controller.UserResponse;
import hami.nasimbehesht724.Activity.Authentication.SignUpActivity;
import hami.nasimbehesht724.Activity.PastPurchases.Adapter.PastPurchasesListBusAdapter;
import hami.nasimbehesht724.Activity.PastPurchases.Adapter.PastPurchasesListFlightDomesticAdapter;
import hami.nasimbehesht724.Activity.PastPurchases.Adapter.PastPurchasesListFlightInternationalAdapter;
import hami.nasimbehesht724.Activity.PastPurchases.Adapter.PastPurchasesListTrainAdapter;
import hami.nasimbehesht724.Activity.PastPurchases.Bus.ShowDetailsTicketBusActivity;
import hami.nasimbehesht724.Activity.PastPurchases.DomesticFlight.ShowDetailsTicketFlightDomesticActivity;
import hami.nasimbehesht724.Activity.PastPurchases.InternationalFlight.ShowDetailsTicketFlightInternationalActivity;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesBus;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesBusResponse;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightDomesticResponse;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightInternational;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightInternationalResponse;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesTrain;
import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesTrainResponse;
import hami.nasimbehesht724.Activity.PastPurchases.Train.ShowDetailsTicketTrainActivity;
import hami.nasimbehesht724.Activity.ServiceSearch.MainServicesSearchMaterialFragment;
import hami.nasimbehesht724.BaseController.AccessApi;
import hami.nasimbehesht724.BaseController.AccessStatusPresenter;
import hami.nasimbehesht724.BaseController.AccessStatusResponse;
import hami.nasimbehesht724.BaseController.DividerItemDecoration;
import hami.nasimbehesht724.BaseController.ResultSearchPresenter;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.Database.DataSaver;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.PaginationScrollListener;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.View.HeaderBar;
import hami.nasimbehesht724.View.MessageBar;

public class PastPurchasesServicesMaterialFragment extends Fragment {

    private TabLayout navigationService;
    private RecyclerView rvResult;
    private MessageBar messageBar;
    protected final static int REQUEST_SIGN_UP = 145654;
    private HeaderBar headerBar;
    private LinearLayoutManager mLayoutManager;
    public final static int PAGE_COUNT = 20;
    private PastPurchasesListFlightDomesticAdapter mAdapterFlightDomestic;
    private PastPurchasesListTrainAdapter mAdapterTrainAdapter;
    private PastPurchasesListBusAdapter mAdapterListBusAdapter;
    private PastPurchasesListFlightInternationalAdapter mAdapterFlightInternational;
    private static final int PAGE_START = 0;
    private int currentPage = PAGE_START;
    private Boolean isLoading;
    private CoordinatorLayout coordinatorLayout;
    private View view;
    private AccessStatusResponse accessStatusResponse;
    private int flightDomesticIndex = -1, flightInternationalIndex = -1, trainIndex = -1, busIndex = -1;
    private AccessApi accessApi;

    //-----------------------------------------------
    public static PastPurchasesServicesMaterialFragment newInstance() {
        Bundle args = new Bundle();
        PastPurchasesServicesMaterialFragment fragment = new PastPurchasesServicesMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            accessStatusResponse = (AccessStatusResponse) savedInstanceState.getSerializable(AccessStatusResponse.class.getName());
            flightInternationalIndex = savedInstanceState.getInt("flightInternationalIndex");
            flightDomesticIndex = savedInstanceState.getInt("flightDomesticIndex");
            trainIndex = savedInstanceState.getInt("trainIndex");
            busIndex = savedInstanceState.getInt("busIndex");
        }
        super.onActivityCreated(savedInstanceState);
    }

    //-----------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putSerializable(AccessStatusResponse.class.getName(), accessStatusResponse);
            outState.putInt("flightDomesticIndex", flightDomesticIndex);
            outState.putInt("flightInternationalIndex", flightInternationalIndex);
            outState.putInt("trainIndex", trainIndex);
            outState.putInt("busIndex", busIndex);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.past_purchases_main_material, container, false);
        initialComponentFragment(view);
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator);
        accessApi = new AccessApi(getActivity());
        navigationService = (TabLayout) view.findViewById(R.id.tabsService);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        headerBar = (HeaderBar) view.findViewById(R.id.headerBar);
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        mLayoutManager = new LinearLayoutManager(getActivity());
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setTitleButton(R.string.loginPanel);
        Keyboard.closeKeyboard(getActivity());
        getAccess();
    }

    //-----------------------------------------------
    public void getAccess() {
        accessApi.getAccessStatus(new AccessStatusPresenter() {
            @Override
            public void onStart() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            navigationService.setVisibility(View.GONE);
                            headerBar.showMessageBar(R.string.authenticating);
                            headerBar.showProgress();
                            messageBar.hideMessageBar();
                        }
                    });
            }

            @Override
            public void onErrorServer() {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.msgErrorServer);
                            headerBar.hideProgress();
                            messageBar.showMessageBar(R.string.msgErrorServer);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getAccess();
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
                            headerBar.showMessageBar(R.string.msgErrorInternetConnection);
                            headerBar.hideProgress();
                            messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getAccess();
                                }
                            });

                        }
                    });
            }

            @Override
            public void onSuccessGetAccessStatus(final AccessStatusResponse accessStatusResponse) {
                if (getActivity() != null)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.hideProgress();
                            messageBar.hideMessageBar();
                            PastPurchasesServicesMaterialFragment.this.accessStatusResponse = accessStatusResponse;
                            setupTab(view);

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
                            headerBar.hideProgress();
                            messageBar.showMessageBar(msg);
                            messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getAccess();
                                }
                            });

                        }
                    });
            }

            @Override
            public void onFinish() {

            }
        });
    }

    //-----------------------------------------------
    private void setupTab(final View view) {
        navigationService.setVisibility(View.VISIBLE);
        int index = 0;
        Integer[] tabIcons = new Integer[0];
        Integer[] tabTitle = new Integer[0];
        ArrayList<Integer> integersIcon = new ArrayList<Integer>();
        ArrayList<Integer> integersTitle = new ArrayList<Integer>();
        if (accessStatusResponse.getBus()) {
            busIndex = index;
            integersIcon.add(R.mipmap.ic_bus);
            integersTitle.add(R.string.bus);
            //tabIcons[index] = R.drawable.bus;
            //tabTitle[index] = R.string.bus;
            //index++;
        }
        if (accessStatusResponse.getTrain()) {
            trainIndex = index;
            integersIcon.add(R.mipmap.ic_train);
            integersTitle.add(R.string.train);
//            tabIcons[index] = R.drawable.train;
//            tabTitle[index] = R.string.train;
            //index++;
        }
        if (accessStatusResponse.getInternational()) {
            flightDomesticIndex = index;
            integersIcon.add(R.mipmap.ic_airplan_top);
            integersTitle.add(R.string.airPlanInternational);
//            tabIcons[index] = R.drawable.flight;
//            tabTitle[index] = R.string.airPlanInternational;
//            index++;
        }
        if (accessStatusResponse.getFlight()) {
            flightInternationalIndex = index;
            integersIcon.add(R.mipmap.ic_airplan_top);
            integersTitle.add(R.string.airPlanDomestic);
//            tabIcons[index] = R.drawable.flight;
//            tabTitle[index] = R.string.airPlanDomestic;
            //index++;
        }

        UtilFonts.applyFontTabServices(getActivity(), navigationService, integersTitle, integersIcon);
        navigationService.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                resetMainUi();
                if (!new DataSaver(getActivity()).hasLogin()) {
                    headerBar.setVisibility(View.GONE);
                    messageBar.showMessageBar(R.string.msgErrorNeedLogin);
                    messageBar.setTitleButton(R.string.loginPanel);
                    messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getActivity(), SignUpActivity.class), 100);
                        }
                    });
                    return;
                } else {
                    messageBar.setTitleButton(R.string.searchFlight);
                }
                if (tab.getPosition() == busIndex) {
                    resetBusUi(currentPage);
                } else if (tab.getPosition() == trainIndex) {
                    resetTrainUi(currentPage);
                } else if (tab.getPosition() == flightDomesticIndex) {
                    resetFlightDomesticUi(currentPage);
                } else if (tab.getPosition() == flightInternationalIndex) {
                    resetFlightInternationalUi(currentPage);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        navigationService.getTabAt(navigationService.getTabCount() - 1).select();
    }

    //-----------------------------------------------
    private void resetMainUi() {
        rvResult.setVisibility(View.GONE);
        currentPage = PAGE_START;
    }

    //-----------------------------------------------
    private void resetBusUi(int pageNumber) {
        messageBar.setCallbackButtonNewSearch(callbackMessageBarBusClickListener);
        headerBar.showMessageBar(R.string.validateSelectTikcet);
        rvResult.setVisibility(View.VISIBLE);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapterListBusAdapter = new PastPurchasesListBusAdapter(getActivity(), new ArrayList<PurchasesBus>(), purchasesBusSelectItemList);
        rvResult.setAdapter(mAdapterListBusAdapter);
        rvResult.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading) {
                    isLoading = true;
                    getListPurchasesBus(currentPage);
                }
            }

            @Override
            public int getTotalPageCount() {
                return PAGE_COUNT;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        getListPurchasesBus(pageNumber);
    }

    //-----------------------------------------------
    private void resetTrainUi(int pageNumber) {
        messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
        headerBar.showMessageBar(R.string.validateSelectTikcet);
        rvResult.setVisibility(View.VISIBLE);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapterTrainAdapter = new PastPurchasesListTrainAdapter(getActivity(), new ArrayList<PurchasesTrain>(), purchasesTrainSelectItemList);
        rvResult.setAdapter(mAdapterTrainAdapter);
        rvResult.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading) {
                    isLoading = true;
                    getListPurchasesTrain(currentPage);
                }
            }

            @Override
            public int getTotalPageCount() {
                return PAGE_COUNT;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        getListPurchasesTrain(pageNumber);
    }


    //-----------------------------------------------
    private void resetFlightInternationalUi(int pageNumber) {
        messageBar.setCallbackButtonNewSearch(callbackMessageBarInternationalFlightClickListener);
        headerBar.showMessageBar(R.string.validateSelectTikcet);
        rvResult.setVisibility(View.VISIBLE);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapterFlightInternational = new PastPurchasesListFlightInternationalAdapter(getActivity(), new ArrayList<PurchasesFlightInternational>(), ticketInternationalSelectItemList);
        rvResult.setAdapter(mAdapterFlightInternational);
        rvResult.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading) {
                    isLoading = true;
                    getListPurchasesFlightInternational(currentPage);
                }
            }

            @Override
            public int getTotalPageCount() {
                return PAGE_COUNT;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        getListPurchasesFlightInternational(pageNumber);
    }


    //-----------------------------------------------
    private void setupRecyclerViewDomestic(ArrayList<PurchasesFlightDomestic> results) {
        mAdapterFlightDomestic.addData(results);
    }

    //-----------------------------------------------
    private void setupRecyclerViewTrain(ArrayList<PurchasesTrain> results) {
        mAdapterTrainAdapter.addData(results);
    }

    //-----------------------------------------------
    private void setupRecyclerViewBus(ArrayList<PurchasesBus> results) {
        mAdapterListBusAdapter.addData(results);
    }

    //-----------------------------------------------
    private void setupRecyclerViewInternational(ArrayList<PurchasesFlightInternational> results) {
        mAdapterFlightInternational.addData(results);
    }

    //-----------------------------------------------
    private SelectItemList<PurchasesFlightInternational> ticketInternationalSelectItemList = new SelectItemList<PurchasesFlightInternational>() {
        @Override
        public void onSelectItem(PurchasesFlightInternational object, int index) {
            Intent intent = new Intent(getActivity(), ShowDetailsTicketFlightInternationalActivity.class);
            intent.putExtra(PurchasesFlightInternational.class.getName(), object);
            startActivity(intent);
        }
    };
    //-----------------------------------------------
    private SelectItemList<PurchasesFlightDomestic> selectItemList = new SelectItemList<PurchasesFlightDomestic>() {
        @Override
        public void onSelectItem(PurchasesFlightDomestic object, int index) {
            Intent intent = new Intent(getActivity(), ShowDetailsTicketFlightDomesticActivity.class);
            intent.putExtra(PurchasesFlightDomestic.class.getName(), object);
            startActivity(intent);
        }
    };
    //-----------------------------------------------
    private SelectItemList<PurchasesTrain> purchasesTrainSelectItemList = new SelectItemList<PurchasesTrain>() {
        @Override
        public void onSelectItem(PurchasesTrain object, int index) {
            Intent intent = new Intent(getActivity(), ShowDetailsTicketTrainActivity.class);
            intent.putExtra(PurchasesTrain.class.getName(), object);
            startActivity(intent);
        }
    };
    //-----------------------------------------------
    private SelectItemList<PurchasesBus> purchasesBusSelectItemList = new SelectItemList<PurchasesBus>() {
        @Override
        public void onSelectItem(PurchasesBus object, int index) {
            Intent intent = new Intent(getActivity(), ShowDetailsTicketBusActivity.class);
            intent.putExtra(PurchasesBus.class.getName(), object);
            startActivity(intent);
        }
    };

    //-----------------------------------------------
    private void resetFlightDomesticUi(int pageNumber) {
        messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
        headerBar.showMessageBar(R.string.validateSelectTikcet);
        rvResult.setVisibility(View.VISIBLE);
        rvResult.setHasFixedSize(true);
        rvResult.setLayoutManager(mLayoutManager);
        rvResult.setItemAnimator(new DefaultItemAnimator());
        rvResult.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mAdapterFlightDomestic = new PastPurchasesListFlightDomesticAdapter(getActivity(), new ArrayList<PurchasesFlightDomestic>(), selectItemList);
        rvResult.setAdapter(mAdapterFlightDomestic);
        rvResult.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading) {
                    isLoading = true;
                    getListPurchasesFlightDomestic(currentPage);
                }
            }

            @Override
            public int getTotalPageCount() {
                return PAGE_COUNT;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        getListPurchasesFlightDomestic(pageNumber);
    }

    //-----------------------------------------------
    private void getListPurchasesBus(final int pageNumber) {
        isLoading = true;
        new UserApi(getActivity()).getListPurchasesBus(pageNumber, PAGE_COUNT, new ResultSearchPresenter<PurchasesBusResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.gettingTicket);
                            headerBar.showProgress();
                            if (pageNumber == 0) {
                                messageBar.showProgress(getString(R.string.gettingTicket));
                            }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorServer);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarBusClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorServer, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesBus(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarTrainClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorInternetConnection, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesBus(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onSuccessResultSearch(final PurchasesBusResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            if (result != null && result.getPurchasesBusList() != null && result.getPurchasesBusList().size() > 0) {
                                currentPage += 1;
                                setupRecyclerViewBus(result.getPurchasesBusList());
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
                            if (type == -100) {
                                //messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
                                new UserApi(getActivity()).reSignIn(userResponseResultSearchPresenter);

                            } else {
                                if (pageNumber == 0) {
                                    messageBar.showMessageBar(R.string.msgErrorNoLang);
                                    messageBar.setCallbackButtonNewSearch(callbackMessageBarSearchClickListener);
                                    headerBar.showMessageBar(R.string.validateSelectTikcet);
                                }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(msg);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarBusClickListener);
                                headerBar.showMessageBar(R.string.validateSelectTikcet);
                            }
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
                            isLoading = false;
                            if (pageNumber == 0) {
                                messageBar.hideProgress();
                            }
                            headerBar.showMessageBar(R.string.validateSelectTikcet);
                            headerBar.hideProgress();


                        }
                    });
                }
            }
        });
    }

    //-----------------------------------------------
    private void getListPurchasesTrain(final int pageNumber) {
        isLoading = true;
        new UserApi(getActivity()).getListPurchasesTrain(pageNumber, PAGE_COUNT, new ResultSearchPresenter<PurchasesTrainResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.gettingTicket);
                            headerBar.showProgress();
                            messageBar.hideMessageBar();
                            if (pageNumber == 0) {
                                messageBar.showProgress(getString(R.string.gettingTicket));
                            }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorServer);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarTrainClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorServer, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesTrain(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarTrainClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorInternetConnection, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesTrain(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onSuccessResultSearch(final PurchasesTrainResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            if (result != null && result.getPurchasesTrainList() != null && result.getPurchasesTrainList().size() > 0) {
                                currentPage += 1;
                                setupRecyclerViewTrain(result.getPurchasesTrainList());
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
                            if (type == -100) {
                                //messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
                                new UserApi(getActivity()).reSignIn(userResponseResultSearchPresenter);

                            } else {
                                if (pageNumber == 0) {
                                    messageBar.showMessageBar(R.string.msgErrorNoLang);
                                    messageBar.setCallbackButtonNewSearch(callbackMessageBarSearchClickListener);
                                    headerBar.showMessageBar(R.string.validateSelectTikcet);
                                }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(msg);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarTrainClickListener);
                                headerBar.showMessageBar(R.string.validateSelectTikcet);
                            }
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
                            isLoading = false;
                            if (pageNumber == 0) {
                                messageBar.hideProgress();
                            }
                            headerBar.showMessageBar(R.string.validateSelectTikcet);
                            headerBar.hideProgress();


                        }
                    });
                }
            }
        });
    }

    //-----------------------------------------------
    private void getListPurchasesFlightDomestic(final int pageNumber) {
        isLoading = true;
        new UserApi(getActivity()).getListPurchasesFlightDomestic(pageNumber, PAGE_COUNT, new ResultSearchPresenter<PurchasesFlightDomesticResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.gettingTicket);
                            headerBar.showProgress();
                            messageBar.hideMessageBar();
                            if (pageNumber == 0) {
                                messageBar.showProgress(getString(R.string.gettingTicket));
                            }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorServer);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorServer, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesFlightDomestic(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorInternetConnection, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesFlightDomestic(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onSuccessResultSearch(final PurchasesFlightDomesticResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            if (result != null && result.getPurchasesFlightsList() != null && result.getPurchasesFlightsList().size() > 0) {
                                currentPage += 1;
                                setupRecyclerViewDomestic(result.getPurchasesFlightsList());
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
                            if (type == -100) {
                                //messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
                                new UserApi(getActivity()).reSignIn(userResponseResultSearchPresenter);

                            } else {
                                if (pageNumber == 0) {
                                    messageBar.showMessageBar(R.string.msgErrorNoLang);
                                    messageBar.setCallbackButtonNewSearch(callbackMessageBarSearchClickListener);
                                    headerBar.showMessageBar(R.string.validateSelectTikcet);
                                }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(msg);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarDomesticFlightClickListener);
                                headerBar.showMessageBar(R.string.validateSelectTikcet);
                            }
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
                            isLoading = false;
                            if (pageNumber == 0) {
                                messageBar.hideProgress();
                            }
                            headerBar.showMessageBar(R.string.validateSelectTikcet);
                            headerBar.hideProgress();


                        }
                    });
                }
            }
        });
    }

    //-----------------------------------------------
    private void getListPurchasesFlightInternational(final int pageNumber) {
        isLoading = true;
        new UserApi(getActivity()).getListPurchasesFlightInternational(pageNumber, PAGE_COUNT, new ResultSearchPresenter<PurchasesFlightInternationalResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            headerBar.showMessageBar(R.string.gettingTicket);
                            headerBar.showProgress();
                            messageBar.hideMessageBar();
                            if (pageNumber == 0) {
                                messageBar.showProgress(getString(R.string.gettingTicket));
                            }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorServer);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarInternationalFlightClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorServer, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesFlightInternational(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarInternationalFlightClickListener);
                                headerBar.showMessageBar(R.string.error);
                            } else {
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, R.string.msgErrorInternetConnection, Snackbar.LENGTH_INDEFINITE).setAction(R.string.tryAgain, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getListPurchasesFlightInternational(currentPage);
                                    }
                                });
                                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                                UtilFonts.overrideFonts(getActivity(), snackbar, UtilFonts.IRAN_SANS_BOLD);
                                snackbar.show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onSuccessResultSearch(final PurchasesFlightInternationalResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.hideMessageBar();
                            if (result != null && result.getPurchasesFlightsList() != null && result.getPurchasesFlightsList().size() > 0) {
                                currentPage += 1;
                                setupRecyclerViewInternational(result.getPurchasesFlightsList());
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
                            if (type == -100) {
                                new UserApi(getActivity()).reSignIn(userResponseResultSearchPresenter);
                            } else {
                                if (pageNumber == 0) {
                                    messageBar.showMessageBar(R.string.msgErrorNoLang);
                                    messageBar.setCallbackButtonNewSearch(callbackMessageBarSearchClickListener);
                                    headerBar.showMessageBar(R.string.error);
                                }
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
                            if (pageNumber == 0) {
                                messageBar.showMessageBar(msg);
                                messageBar.setCallbackButtonNewSearch(callbackMessageBarInternationalFlightClickListener);
                                headerBar.showMessageBar(R.string.error);
                            }
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
                            isLoading = false;
                            if (pageNumber == 0) {
                                messageBar.hideProgress();
                            }
                            headerBar.showMessageBar(R.string.validateSelectTikcet);
                            headerBar.hideProgress();


                        }
                    });
                }
            }
        });
    }

    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarClickListenerReSignIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
            new UserApi(getActivity()).reSignIn(userResponseResultSearchPresenter);
        }
    };
    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarDomesticFlightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetFlightDomesticUi(currentPage);
        }
    };
    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarBusClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetBusUi(currentPage);
        }
    };
    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarTrainClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetTrainUi(currentPage);
        }
    };
    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarInternationalFlightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetFlightInternationalUi(currentPage);
        }
    };
    //-----------------------------------------------
    private View.OnClickListener callbackMessageBarSearchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showSearchPage();
        }
    };
    //-----------------------------------------------
    ResultSearchPresenter<UserResponse> userResponseResultSearchPresenter = new ResultSearchPresenter<UserResponse>() {
        @Override
        public void onStart() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        headerBar.showMessageBar(R.string.authenticating);
                        messageBar.showProgress(getString(R.string.authenticating));
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
                        messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
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
                        messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
                        headerBar.showMessageBar(R.string.error);
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
                        messageBar.showMessageBar(R.string.msgErrorNoLang);
                        messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
                        headerBar.showMessageBar(R.string.validateSelectTikcet);
                    }
                });
            }
        }

        @Override
        public void onSuccessResultSearch(UserResponse result) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageBar.hideMessageBar();
                        switch (navigationService.getSelectedTabPosition()) {
                            case 0:
                                resetBusUi(currentPage);
                            case 1:
                                resetTrainUi(currentPage);
                            case 2:
                                resetFlightInternationalUi(currentPage);
                            case 3:
                                resetFlightDomesticUi(currentPage);
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
                        messageBar.setCallbackButtonNewSearch(callbackMessageBarClickListenerReSignIn);
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


    };

    //-----------------------------------------------
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (new DataSaver(getActivity()).hasLogin()) {
            navigationService.getTabAt(navigationService.getTabCount() - 1).select();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void showSearchPage() {
        UtilFragment.changeFragment(getActivity().getSupportFragmentManager(), MainServicesSearchMaterialFragment.newInstance(accessStatusResponse));
        //txtSubTitleMenu.setText(R.string.pastPurchases);
    }

}
