package hami.mainapp.Activity.ServiceHotel.International.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;


import java.util.ArrayList;
import java.util.List;

import hami.mainapp.BaseController.AppliedFilters;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;
import hami.mainapp.View.CheckBoxLtr;
import hami.mainapp.View.CheckBoxRateLtr;


public class NewDesignFilterInternationalHotelFragmentDialog extends AAH_FabulousFragment {


    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private DisplayMetrics metrics;
    private static Callbacks callbacks;
    public final static String FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE = "fciht";
    public final static String FILTER_CATEGORY_SORT = "fcs";
    public final static String FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER = "fcpiho";
    private AppCompatSpinner spSort;
    private AppliedFilters appliedFilters;
    private View contentView;
    private ArrayList<String> classTypeHotel;
    private ArrayList<String> listOffer;
    private static final String TAG = "NewDesignFilterInternationalHotelFragmentDialog";

    //-----------------------------------------------
    public static NewDesignFilterInternationalHotelFragmentDialog newInstance(Callbacks callbacks) {
        NewDesignFilterInternationalHotelFragmentDialog.callbacks = callbacks;
        NewDesignFilterInternationalHotelFragmentDialog mff = new NewDesignFilterInternationalHotelFragmentDialog();

        return mff;
    }

    //-----------------------------------------------
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        metrics = this.getResources().getDisplayMetrics();
    }
    //-----------------------------------------------

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            classTypeHotel = savedInstanceState.getStringArrayList("classTypeHotel");
            listOffer = savedInstanceState.getStringArrayList("listOffer");
            appliedFilters = (AppliedFilters) savedInstanceState.getSerializable(AppliedFilters.class.getName());
            if (appliedFilters != null) {
                applied_filters = appliedFilters.getApplied_filters();
            }
        }
    }

    //-----------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putStringArrayList("classTypeHotel", classTypeHotel);
            outState.putStringArrayList("listOffer", listOffer);
            appliedFilters = new AppliedFilters(applied_filters);
            outState.putSerializable(AppliedFilters.class.getName(), appliedFilters);
        }
        super.onSaveInstanceState(outState);
    }


    //-----------------------------------------------
    @Override

    public void setupDialog(Dialog dialog, int style) {

        //if (contentView == null)
        contentView = View.inflate(getContext(), R.layout.fragment_international_hotel_dialog_filter_view, null);
        resetFilterView();
        super.setupDialog(dialog, style); //call super at last
    }

    //-----------------------------------------------
    public void setClassTypeHotel(ArrayList<String> classTypeHotel) {
        try {
            this.classTypeHotel = classTypeHotel;
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    public void setClassOffer(ArrayList<String> listOffer) {
        try {
            this.listOffer = listOffer;
        } catch (Exception e) {

        }
    }

    //-----------------------------------------------
    private void resetFilterView() {
        //appliedFilters = new AppliedFilters();
        //applied_filters = new ArrayMap<>();

        UtilFonts.overrideFonts(getActivity(), contentView, UtilFonts.IRAN_SANS_BOLD);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        TextView btnApplyFilter = (TextView) contentView.findViewById(R.id.btnApplyFilter);
        TextView txtClearFilter = (TextView) contentView.findViewById(R.id.txtClearFilter);
        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter(applied_filters);
            }
        });
        txtClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFilter();
                closeFilter(applied_filters);
            }
        });
        sort(rl_content);
        setupHotelClass(rl_content);
        setupOffer(rl_content);
        setAnimationDuration(400); //optional; default 500ms
        //setPeekHeight(300); // optional; default 400dp
        setCallbacks(callbacks); //optional; to get back result
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView);
    }

    //-----------------------------------------------
    private void sort(View contentView) {
        spSort = (AppCompatSpinner) contentView.findViewById(R.id.spSort);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortInternationalHotel, R.layout.row_item_spinner_train_white);
        adapter.setDropDownViewResource(R.layout.row_simple_spinner_dropdown_item);
        spSort.setAdapter(adapter);
        if (applied_filters != null &&
                applied_filters.get(FILTER_CATEGORY_SORT) != null &&
                applied_filters.get(FILTER_CATEGORY_SORT).size() == 1) {
            String index = applied_filters.get(FILTER_CATEGORY_SORT).get(0);
            spSort.setSelection(Integer.parseInt(index));
            //checkBox.setCheck(true);
        } else {
            spSort.setSelection(0);
        }
        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clearFromSelectedMap(FILTER_CATEGORY_SORT);
                addToSelectedMap(FILTER_CATEGORY_SORT, String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //-----------------------------------------------
    private void setupHotelClass(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutClassType);

        for (int index = 0; index < classTypeHotel.size(); index++) {
            final CheckBoxRateLtr checkBox = new CheckBoxRateLtr(getActivity());
            checkBox.setTag(index);
            Integer tag = (Integer) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE) != null &&
                    applied_filters.get(FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE).contains(String.valueOf(tag))) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_INTERNATIONAL_HOTEL_TYPE, String.valueOf(checkBox.getTag()));


                }
            });
            int rate = Integer.parseInt(classTypeHotel.get(index));
            if (rate == 0) {
                checkBox.showText(getString(R.string.noStarsEng));
            } else {
                checkBox.setRatingBar(rate, rate);
            }

            linearLayout.addView(checkBox);
        }
    }

    //-----------------------------------------------
    private void setupOffer(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutOffer);
        for (int index = 0; index < listOffer.size(); index++) {
            final CheckBoxLtr checkBox = new CheckBoxLtr(getActivity());
            checkBox.setTag(listOffer.get(index));
            String tag = (String) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER) != null &&
                    applied_filters.get(FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER).contains(tag)) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_INTERNATIONAL_HOTEL_OFFER, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(listOffer.get(index));
            linearLayout.addView(checkBox);
        }
    }


    //-----------------------------------------------
    private void addToSelectedMap(String key, String value) {
        if (applied_filters.get(key) != null && !applied_filters.get(key).contains(value)) {
            applied_filters.get(key).add(value);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(value);
            applied_filters.put(key, temp);
        }
    }

    //-----------------------------------------------
    private void removeFromSelectedMap(String key, String value) {
        try {
            if (applied_filters.get(key).size() == 1) {
                applied_filters.remove(key);
            } else {
                applied_filters.get(key).remove(value);
            }
        } catch (Exception e) {


        }

    }

    //-----------------------------------------------
    private void clearFromSelectedMap(String key) {
        try {
            applied_filters.remove(key);
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    public void resetFilter() {
        applied_filters = new ArrayMap<>();
    }
}