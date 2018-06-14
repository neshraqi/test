package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.CapacityInternationalListAdapter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.IatiRulesInternationalListAdapter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter.PartoRulesInternationalListAdapter;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaggaegeResponse;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.AllFlightInternationalParto;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.LegsIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.LegsParto;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.PackageCompletedFlightInternational;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesRequest;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseIati;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesResponseParto;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.InternationalApi;
import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.RulesInternationalPresenter;
import hami.hamibelit.R;
import hami.hamibelit.Util.CustomeChrome.CustomTabsPackages;
import hami.hamibelit.Util.TimeDate;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.View.MessageBar;
import hami.hamibelit.View.ToastMessageBar;


public class FragmentInternationalRouting extends Fragment {

    private RulesRequest rulesRequest;
    private Boolean hasShowRules = true;
    private RulesResponseParto rulesResponseParto;
    private RulesResponseIati rulesResponseIati;
    private BaggaegeResponse baggaegeResponse;
    private RecyclerView rvResult;
    private LinearLayout layoutListRouting;
    private MessageBar messageBar;
    private AllFlightInternationalParto allFlightInternationalParto;
    private AllFlightInternationalIati allFlightInternationalIati;
    private Boolean hasReturn_;
    private PackageCompletedFlightInternational packageCompletedFlightInternational;
    private View view;
    private static final String TAG = "FragmentInternationalRouting";
    private ProgressDialog progressDialog;
    private InternationalApi internationalApi;

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            allFlightInternationalIati = getArguments().getParcelable(AllFlightInternationalIati.class.getName());
            allFlightInternationalParto = getArguments().getParcelable(AllFlightInternationalParto.class.getName());
            packageCompletedFlightInternational = (PackageCompletedFlightInternational) getArguments().getSerializable(PackageCompletedFlightInternational.class.getName());
            rulesRequest = (RulesRequest) getArguments().getSerializable(RulesRequest.class.getName());
            hasReturn_ = getArguments().getBoolean("HasReturn_");
        }
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            allFlightInternationalIati = savedInstanceState.getParcelable(AllFlightInternationalIati.class.getName());
            allFlightInternationalParto = savedInstanceState.getParcelable(AllFlightInternationalParto.class.getName());
            rulesRequest = (RulesRequest) savedInstanceState.getSerializable(RulesRequest.class.getName());
            hasReturn_ = savedInstanceState.getBoolean("HasReturn_");
            hasShowRules = savedInstanceState.getBoolean("hasShowRules");
            packageCompletedFlightInternational = (PackageCompletedFlightInternational) savedInstanceState.getSerializable(PackageCompletedFlightInternational.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
            outState.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
            outState.putSerializable(RulesRequest.class.getName(), rulesRequest);
            outState.putBoolean("hasShowRules", hasShowRules);
            outState.putBoolean("HasReturn_", hasReturn_);
            outState.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        }
        super.onSaveInstanceState(outState);
    }


    //-----------------------------------------------
    public static FragmentInternationalRouting newInstance(
            AllFlightInternationalParto allFlightInternationalParto,
            Boolean hasReturn,
            PackageCompletedFlightInternational packageCompletedFlightInternational,
            RulesRequest rulesRequest) {
        Bundle args = new Bundle();
        FragmentInternationalRouting fragment = new FragmentInternationalRouting();
        args.putParcelable(AllFlightInternationalParto.class.getName(), allFlightInternationalParto);
        args.putBoolean("HasReturn_", hasReturn);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        args.putSerializable(RulesRequest.class.getName(), rulesRequest);
        fragment.setArguments(args);
        return fragment;
    }


    //-----------------------------------------------
    public static FragmentInternationalRouting newInstance(
            AllFlightInternationalIati allFlightInternationalIati,
            PackageCompletedFlightInternational packageCompletedFlightInternational,
            RulesRequest rulesRequest) {
        Bundle args = new Bundle();
        FragmentInternationalRouting fragment = new FragmentInternationalRouting();
        args.putParcelable(AllFlightInternationalIati.class.getName(), allFlightInternationalIati);
        args.putSerializable(PackageCompletedFlightInternational.class.getName(), packageCompletedFlightInternational);
        args.putSerializable(RulesRequest.class.getName(), rulesRequest);
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_service_flight_details_layout, container, false);
            initialComponentFragment();
        }
        return view;
    }


    //-----------------------------------------------
    private void initialComponentFragment() {
        UtilFonts.overrideFonts(getActivity(), view.findViewById(R.id.mainRoot), UtilFonts.IRAN_SANS_NORMAL);
        internationalApi = new InternationalApi(getActivity());
        rvResult = (RecyclerView) view.findViewById(R.id.rvResult);
        layoutListRouting = (LinearLayout) view.findViewById(R.id.layoutListRouting);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setTitleButton(R.string.contactSupport);
        setupTab();
        setupListRouting();
    }

    //-----------------------------------------------
    private void setupTab() {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        UtilFonts.applyFontTabRouting(getActivity(), tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                layoutListRouting.removeAllViews();
                if (tab.getPosition() == 0) {
                    view.findViewById(R.id.nestedScrollViewRouting).setVisibility(View.GONE);
                    rvResult.setVisibility(View.VISIBLE);
                    messageBar.hideMessageBar();
                    showCapacity();
                } else if (tab.getPosition() == 1) {
                    view.findViewById(R.id.nestedScrollViewRouting).setVisibility(View.GONE);
                    rvResult.setVisibility(View.VISIBLE);
                    showRulesDetails();
                } else {
                    view.findViewById(R.id.nestedScrollViewRouting).setVisibility(View.VISIBLE);
                    rvResult.setVisibility(View.GONE);
                    setupListRouting();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //-----------------------------------------------
    private void showRulesDetails() {
        hasShowRules = true;
        getRules();
    }

    //-----------------------------------------------
    private void getRules() {
        if (rulesResponseParto != null) {
            rulesInternationalPresenter.onStart();
            showRulesParto(rulesResponseParto);
            rulesInternationalPresenter.onFinish();
        } else if (rulesResponseIati != null) {
            rulesInternationalPresenter.onStart();
            showRulesIati(rulesResponseIati);
            rulesInternationalPresenter.onFinish();
        } else
            internationalApi.getAirRules(rulesRequest, hasShowRules, rulesInternationalPresenter);
    }

    //-----------------------------------------------
    private void setupListRouting() {
        try {
            if (allFlightInternationalParto != null) {
                setupListRoutingParto();
            } else if (allFlightInternationalIati != null) {
                setupListRoutingIati();
            }
        } catch (Exception e) {

            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            getActivity().onBackPressed();
        }

    }

    //-----------------------------------------------
    private void setupListRoutingParto() {
        final ArrayList<LegsParto> legsPartos = hasReturn_ ? allFlightInternationalParto.getLegs().get(1).getListLegs() : allFlightInternationalParto.getLegs().get(0).getListLegs();

        messageBar.setCallbackButtonNewSearch(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://telegram.me/";
                if (legsPartos.get(0).getCharter())
                    url += "respinaflight";
                else
                    url += "Respina24com";
                new CustomTabsPackages(getActivity()).showUrl(url);
            }
        });
        messageBar.hideMessageBar();
        layoutListRouting.removeAllViews();
        int indexWaiting = 0;
        for (int index = 0; index < legsPartos.size(); index++) {
            LegsParto legs = legsPartos.get(index);
            if (legsPartos.size() > 1 && layoutListRouting.getChildCount() >= 1) {
                View viewWaiting = getActivity().getLayoutInflater().inflate(R.layout.row_service_flight_routing_international_waiting, layoutListRouting, false);
                UtilFonts.overrideFonts(getActivity(), viewWaiting, UtilFonts.IRAN_SANS_NORMAL);
                TextView txtAirportWaiting = (TextView) viewWaiting.findViewById(R.id.txtAirportWating);
                TextView txtTimeWaiting = (TextView) viewWaiting.findViewById(R.id.txtTimeWating);
                String arrivalTime = legsPartos.get(index - 1).getArrivalDateTime();
                String departureTime = legsPartos.get(index).getDepartureDateTime();
                TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(arrivalTime, departureTime);
                if (resultDateDiff != null) {
                    txtTimeWaiting.setText("زمان انتظار " + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
                }
                String airport = getCityInfo(legs.getDepartureAirportLocationCode(), packageCompletedFlightInternational.getLinkedTreeMapCityParto()).getAirportF();
                txtAirportWaiting.setText("توقف در فرودگاه " + airport);
                indexWaiting++;
                layoutListRouting.addView(viewWaiting);
            }
            View view = getActivity().getLayoutInflater().inflate(R.layout.row_service_flight_international_routing_new, layoutListRouting, false);
            UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
            TextView txtFlightTime = (TextView) view.findViewById(R.id.txtFlightTime);
            TextView txtFlightYata = (TextView) view.findViewById(R.id.txtFlightYata);
            TextView txtFlightAirLine = (TextView) view.findViewById(R.id.txtFlightAirLine);
            TextView txtFlyTime = (TextView) view.findViewById(R.id.txtDetails);
            String timeDepArrival = "";
            if (legsPartos.get(0).getCharter())
                timeDepArrival = "پرواز چارتری-" + "ساعت حرکت:" + TimeDate.getTime(null, legs.getDepartureDateTime());
            else
                timeDepArrival = "ساعت رسیدن:" + TimeDate.getTime(null, legs.getDepartureDateTime()) + " - " + "زمان رسیدن:" + TimeDate.getTime(null, legs.getArrivalDateTime());
            txtFlightTime.setText(timeDepArrival);

            String yataCity = "از " + legs.getDepartureAirportLocationCode() + "(" + getCityInfo(legs.getDepartureAirportLocationCode(), packageCompletedFlightInternational.getLinkedTreeMapCityParto()).getDataF() + ")";
            yataCity += " به " + legs.getArrivalAirportLocationCode() + "(" + getCityInfo(legs.getArrivalAirportLocationCode(), packageCompletedFlightInternational.getLinkedTreeMapCityParto()).getDataF() + ")";
            txtFlightYata.setText(yataCity);
            LinkedTreeMap<String, String> airlines = (LinkedTreeMap<String, String>) packageCompletedFlightInternational.getLinkedTreeMapAirLineParto();
            String airlineName = airlines.get(allFlightInternationalParto.getOutboundAirline());
            txtFlightAirLine.setText(airlineName + "(" + legs.getOperatingAirline().getCode() + legs.getOperatingAirline().getFlightNumber() + "-" + legs.getOperatingAirline().getEquipment() + ")");
            TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(legs.getDepartureDateTime(), legs.getArrivalDateTime());
            if (resultDateDiff != null)
                txtFlyTime.setText("مدت زمان پرواز:" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
            else
                txtFlyTime.setText("مدت زمان پرواز:---");
            //-----------------------------------------------
            layoutListRouting.addView(view);
        }
    }

    //-----------------------------------------------
    private void setupListRoutingIati() {
        messageBar.hideMessageBar();
        layoutListRouting.removeAllViews();
        int indexWaiting = 0;
        ArrayList<LegsIati> legsIati = allFlightInternationalIati.getLegsList();
        for (int index = 0; index < legsIati.size(); index++) {
            LegsIati legs = legsIati.get(index);
            if (legsIati.size() > 1 && layoutListRouting.getChildCount() >= 1) {
                View viewWaiting = getActivity().getLayoutInflater().inflate(R.layout.row_service_flight_routing_international_waiting, layoutListRouting, false);
                UtilFonts.overrideFonts(getActivity(), viewWaiting, UtilFonts.IRAN_SANS_NORMAL);
                TextView txtAirportWaiting = (TextView) viewWaiting.findViewById(R.id.txtAirportWating);
                TextView txtTimeWaiting = (TextView) viewWaiting.findViewById(R.id.txtTimeWating);
                String arrivalTime = legsIati.get(index - 1).getArrivalTime();
                String departureTime = legsIati.get(index).getDepartureTime();
                TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(arrivalTime, departureTime);
                if (resultDateDiff != null) {
                    txtTimeWaiting.setText("زمان انتظار " + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
                }
                txtAirportWaiting.setText("توقف در فرودگاه " + legs.getDepartureAirportName());
                indexWaiting++;
                layoutListRouting.addView(viewWaiting);
            }
            View view = getActivity().getLayoutInflater().inflate(R.layout.row_service_flight_international_routing_new, layoutListRouting, false);
            UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
            TextView txtFlightTime = (TextView) view.findViewById(R.id.txtFlightTime);
            TextView txtFlightYata = (TextView) view.findViewById(R.id.txtFlightYata);
            TextView txtFlightAirLine = (TextView) view.findViewById(R.id.txtFlightAirLine);
            TextView txtFlyTime = (TextView) view.findViewById(R.id.txtDetails);
            String timeDepArrival = "ساعت حرکت:" + TimeDate.getTime(null, legs.getDepartureTime()) + " - " + "ساعت رسیدن:" + TimeDate.getTime(null, legs.getArrivalTime());
            txtFlightTime.setText(timeDepArrival);

            String yataCity = "از " + legs.getDepartureAirport() + "(" + getCityInfo(legs.getDepartureAirport(), packageCompletedFlightInternational.getLinkedTreeMapCityIati()).getDataF() + ")";
            yataCity += " به " + legs.getArrivalAirport() + "(" + getCityInfo(legs.getArrivalAirport(), packageCompletedFlightInternational.getLinkedTreeMapCityIati()).getDataF() + ")";
            txtFlightYata.setText(yataCity);
            String airlineName = legs.getOperatorName();
            txtFlightAirLine.setText(airlineName + "(" + legs.getFlightNo() + "-" + legs.getAircraft() + ")");
            TimeDate.ResultDateDiff resultDateDiff = TimeDate.dateDiff(legs.getDepartureTime(), legs.getArrivalTime());
            txtFlyTime.setText("مدت زمان پرواز:" + resultDateDiff.getElapsedHours() + "  ساعت" + " و " + resultDateDiff.getElapsedMinutes() + " دقیقه");
            //-----------------------------------------------

            layoutListRouting.addView(view);
        }

    }

    //-----------------------------------------------
    private SearchInternational getCityInfo(String key, Object city) {
        SearchInternational searchInternational = new SearchInternational();
        try {
            LinkedTreeMap<String, Object> master = (LinkedTreeMap<String, Object>) city;
            LinkedTreeMap<String, String> object = (LinkedTreeMap<String, String>) master.get(key);
            searchInternational.setYata(key);
            searchInternational.setData(object.get("data"));
            searchInternational.setDataF(object.get("dataf"));
            searchInternational.setAirport(object.get("airport"));
            searchInternational.setAirportF(object.get("airportf"));
            return searchInternational;
        } catch (Exception e) {
        }
        return searchInternational;
    }


    //-----------------------------------------------
    private void showRulesIati(RulesResponseIati rules) {
        if (rules != null && rules.getRules() != null && rules.getRules().size() > 0) {
            rvResult.setHasFixedSize(true);
            rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            IatiRulesInternationalListAdapter mAdapter = new IatiRulesInternationalListAdapter(getActivity(), rules.getRules());
            rvResult.setAdapter(mAdapter);
        } else {
            rvResult.setVisibility(View.GONE);
            messageBar.showMessageBar(R.string.msgErrorNoRules);
        }
    }

    //----------------------------------------------- //-----------------------------------------------
    private void showRulesParto(RulesResponseParto rules) {
        if (rules != null && rules.getRules() != null && rules.getRules().getFareRulesList().size() > 0) {
            rvResult.setHasFixedSize(true);
            rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            rvResult.setLayoutManager(mLayoutManager);
            rvResult.setItemAnimator(new DefaultItemAnimator());
            PartoRulesInternationalListAdapter mAdapter = new PartoRulesInternationalListAdapter(getActivity(), rules.getRules().getFareRulesList());
            rvResult.setAdapter(mAdapter);
        } else {
            rvResult.setVisibility(View.GONE);
            messageBar.showMessageBar(R.string.msgErrorNoRules);
        }
    }

    //-----------------------------------------------
    private void showCapacity() {
        hasShowRules = false;
        if (baggaegeResponse != null) {
            rulesInternationalPresenter.onStart();
            if (baggaegeResponse != null && baggaegeResponse.getBaggageInfoList() != null && baggaegeResponse.getBaggageInfoList().size() > 0) {
                rvResult.setHasFixedSize(true);
                rvResult.setLayoutManager(new LinearLayoutManager(getActivity()));
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                rvResult.setLayoutManager(mLayoutManager);
                rvResult.setItemAnimator(new DefaultItemAnimator());
                CapacityInternationalListAdapter mAdapter = new CapacityInternationalListAdapter(getActivity(), baggaegeResponse.getBaggageInfoList());
                rvResult.setAdapter(mAdapter);

            } else {
                rvResult.setVisibility(View.GONE);
                rvResult.setVisibility(View.GONE);
                messageBar.showMessageBar(R.string.msgErrorNoCapacity);
                //progress.endShow();
            }
            rulesInternationalPresenter.onFinish();
        } else {
            internationalApi.getAirRules(rulesRequest, hasShowRules, rulesInternationalPresenter);
        }
    }


    //-----------------------------------------------

    RulesInternationalPresenter rulesInternationalPresenter = new RulesInternationalPresenter() {
        @Override
        public void onStart() {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage(getString(R.string.gettingInfo));
                        progressDialog.setCancelable(true);
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                internationalApi.cancelRequest();
                            }
                        });
                        progressDialog.show();
                    }
                });

        }

        @Override
        public void onErrorServer() {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvResult.setVisibility(View.GONE);
                        messageBar.showMessageBar(R.string.msgErrorServer);
                    }
                });

        }

        @Override
        public void onErrorInternetConnection() {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvResult.setVisibility(View.GONE);
                        messageBar.showMessageBar(R.string.msgErrorInternetConnection);
                    }
                });
        }

        @Override
        public void onSuccessGetRulesParto(final RulesResponseParto rulesResponse, final BaggaegeResponse baggaegeResponse) {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentInternationalRouting.this.rulesResponseParto = rulesResponse;
                        FragmentInternationalRouting.this.baggaegeResponse = baggaegeResponse;
                        showRulesParto(rulesResponseParto);
                    }
                });
        }

        @Override
        public void onSuccessGetRulesIati(final RulesResponseIati rulesResponse, final BaggaegeResponse baggaegeResponse) {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentInternationalRouting.this.rulesResponseIati = rulesResponse;
                        FragmentInternationalRouting.this.baggaegeResponse = baggaegeResponse;
                        showRulesIati(rulesResponseIati);
                    }
                });
        }

        @Override
        public void onSuccessGetCapacity(final BaggaegeResponse baggaegeResponse) {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentInternationalRouting.this.baggaegeResponse = baggaegeResponse;
                        showCapacity();
                    }
                });
        }


        @Override
        public void onError(final String msg) {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvResult.setVisibility(View.GONE);
                        messageBar.showMessageBar(msg);
                    }
                });
        }

        @Override
        public void onFinish() {
            if (getActivity() != null)
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.hide();
                    }
                });
        }
    };
}

