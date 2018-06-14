package hami.aniticket.Activity.ServiceTour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import hami.aniticket.Activity.ServiceSearch.ConstService.ServiceID;
import hami.aniticket.Activity.ServiceTour.Controller.Model.DateTour;
import hami.aniticket.Activity.ServiceTour.Controller.Model.InitialTourResponse;
import hami.aniticket.Activity.ServiceTour.Controller.Model.NameValue;
import hami.aniticket.Activity.ServiceTour.Controller.Model.SearchTourRequest;
import hami.aniticket.Activity.ServiceTour.Controller.TourApi;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.Const.TourRules;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.MessageBar;
import hami.aniticket.View.ToastMessageBar;

public class MainTourServicesMaterialFragment extends Fragment {

    private TourApi tourApi;
    private MessageBar messageBar;
    private TabLayout navigationService;
    private EditText edtPlace, edtDate;
    private InitialTourResponse initialTourResponse;
    private SearchTourRequest searchTourRequest;
    private AppCompatButton fabSearch;
    private ImageView imgService;

    //-----------------------------------------------
    public static MainTourServicesMaterialFragment newInstance() {
        Bundle args = new Bundle();
        MainTourServicesMaterialFragment fragment = new MainTourServicesMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_tour_material, container, false);
        initialComponentFragment(view);
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        initialBlackAndWhiteImage(view);
        messageBar = (MessageBar) view.findViewById(R.id.messageBar);
        messageBar.setCallbackButtonNewSearch(callBackMessageBarListener);
        searchTourRequest = new SearchTourRequest();
        tourApi = new TourApi(getActivity());
        fabSearch = (AppCompatButton) view.findViewById(R.id.btnSearch);
        fabSearch.setOnClickListener(onClickListener);
        edtPlace = (EditText) view.findViewById(R.id.edtPlace);
        edtDate = (EditText) view.findViewById(R.id.edtDate);
        edtPlace.setFocusable(false);
        edtPlace.setCursorVisible(false);
        edtPlace.setOnClickListener(onClickListener);
        edtDate.setFocusable(false);
        edtDate.setCursorVisible(false);
        edtDate.setOnClickListener(onClickListener);
        setupTab(view);
        getInitialTourConfig();
    }

    //-----------------------------------------------
    private void initialBlackAndWhiteImage(View view) {
//        ImageView imgService = (ImageView) view.findViewById(R.id.imgServiceShimmer);
//        new UtilImage().convertToBlackAndWhite(imgService);
    }

    //-----------------------------------------------
    private void initialTour() {
        try {
            DateTour date = initialTourResponse.getDateTourCalendar().get(0);
            String dateStr = date.getJdate() != null && date.getJdate().length() > 0 ? date.getJdate() : date.getJday();
            NameValue place = initialTourResponse.getFromList().get(0);
            searchTourRequest = new SearchTourRequest("allTour", getString(R.string.allTour), place.getName(), place.getValue(), date.getUnix(), dateStr);
            edtPlace.setText(place.getValue());
            edtDate.setText(date.getJday() + " " + date.getJmonth());
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    private void setupTab(final View view) {
        navigationService = (TabLayout) view.findViewById(R.id.tabsService);
        UtilFonts.applyFontTabServicesTour(getActivity(), navigationService);
        navigationService.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //resetMainUi();
                switch (tab.getPosition()) {
                    case 0:
                        searchTourRequest.setKind(TourRules.TOUR_INTERNATIONAL);
                        searchTourRequest.setKindFa(getString(R.string.international));
                        break;
                    case 1:
                        searchTourRequest.setKind(TourRules.TOUR_DOMESTIC);
                        searchTourRequest.setKindFa(getString(R.string.domestic));
                        break;
                    case 2:
                        searchTourRequest.setKind(TourRules.TOUR_ONE_DAY);
                        searchTourRequest.setKindFa(getString(R.string.dayTour));
                        break;
                    case 4:
                        searchTourRequest.setKind(TourRules.TOUR_ALL);
                        searchTourRequest.setKindFa(getString(R.string.allTour));
                        break;
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
    View.OnClickListener callBackMessageBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getInitialTourConfig();
        }
    };
    //-----------------------------------------------
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.edtPlace) {
                Intent intent = new Intent(getActivity(), SearchPlaceAndDateTourActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_TOUR_PLACE);
                intent.putExtra(InitialTourResponse.class.getName(), initialTourResponse);
                startActivityForResult(intent, 0);
            } else if (v.getId() == R.id.edtDate) {
                Intent intent = new Intent(getActivity(), SearchPlaceAndDateTourActivity.class);
                intent.putExtra(ServiceID.INTENT_SERVICE_ID, ServiceID.SERVICE_ID_TOUR_DATE);
                intent.putExtra(InitialTourResponse.class.getName(), initialTourResponse);
                startActivityForResult(intent, 0);
            } else if (v.getId() == R.id.btnSearch) {
                search();
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (data != null && data.getExtras() != null) {
                if (resultCode == ServiceID.SERVICE_ID_TOUR_PLACE) {
                    NameValue item = data.getExtras().getParcelable(NameValue.class.getName());
                    edtPlace.setText(item.getValue());
                    searchTourRequest.setFrom(item.getName());
                    searchTourRequest.setFromFa(item.getValue());
                } else if (resultCode == ServiceID.SERVICE_ID_TOUR_DATE) {
                    DateTour item = data.getExtras().getParcelable(DateTour.class.getName());
                    edtDate.setText(item.getJday() + " " + item.getJmonth());
                    searchTourRequest.setDate(item.getUnix());
                    searchTourRequest.setDateFa(item.getJdate());
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //-----------------------------------------------
    private void getInitialTourConfig() {
        tourApi.initialTour(new ResultSearchPresenter<InitialTourResponse>() {
            @Override
            public void onStart() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageBar.showProgress(getString(R.string.gettingInfo));
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
                        }
                    });
                }
            }

            @Override
            public void onSuccessResultSearch(final InitialTourResponse result) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initialTourResponse = result;
                            messageBar.hideMessageBar();
                            initialTour();
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
    private void search() {
        Intent intent = null;
        if (searchTourRequest.getKind() == null && searchTourRequest.getKind().length() == 0) {
            ToastMessageBar.show(getActivity(), R.string.msgErrorPayment);
            return;
        } else if (searchTourRequest.getDate() == null && searchTourRequest.getDate().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.validateSelectFromPlaceTour);
            return;
        } else if (searchTourRequest.getFrom() == null && searchTourRequest.getFrom().length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
            edtPlace.startAnimation(vibrateAnimation);
            ToastMessageBar.show(getActivity(), R.string.validateSelectFromDateTour);
            return;
        } else {
            intent = new Intent(getActivity(), ActivityMainTour.class);
            intent.putExtra(SearchTourRequest.class.getName(), searchTourRequest);
            startActivity(intent);
        }

    }
}
