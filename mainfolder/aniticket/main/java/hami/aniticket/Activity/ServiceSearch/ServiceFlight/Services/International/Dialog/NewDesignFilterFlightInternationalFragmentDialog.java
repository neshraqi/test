package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hami.hamibelit.BaseController.AppliedFilters;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.View.CheckBox;


public class NewDesignFilterFlightInternationalFragmentDialog extends AAH_FabulousFragment {


    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private List<TextView> textViews = new ArrayList<>();
    private AppCompatSeekBar seekBarPrice;
    private DisplayMetrics metrics;
    private static Callbacks callbacks;
    public final static String FILTER_CATEGORY_TIME_TAKE_OFF = "fctto";
    public final static String FILTER_CATEGORY_TIME_TAKE_OFF_RETURN = "fcttor";
    public final static String FILTER_CATEGORY_STOPS = "fct";
    public final static String FILTER_CATEGORY_AIRLINE = "fca";
    public final static String FILTER_CATEGORY_SORT = "fcs";
    public final static String SELECTED = "SELECTED";
    public final static String UNSELECTED = "UNSELECTED";
    public final static int STEP_SIZE = 100;
    private LinkedTreeMap<String, String> objectAirline;
    private AppCompatSpinner spSort;
    private AppliedFilters appliedFilters;
    private View contentView;
    private Boolean twoWays;

    //-----------------------------------------------
    public static NewDesignFilterFlightInternationalFragmentDialog newInstance(Callbacks callbacks) {
        NewDesignFilterFlightInternationalFragmentDialog.callbacks = callbacks;
        NewDesignFilterFlightInternationalFragmentDialog mff = new NewDesignFilterFlightInternationalFragmentDialog();
        return mff;
    }

    //-----------------------------------------------
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            objectAirline = (LinkedTreeMap<String, String>) savedInstanceState.getSerializable("objectAirline");
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
            outState.putSerializable("objectAirline", objectAirline);
            appliedFilters = new AppliedFilters(applied_filters);
            outState.putSerializable(AppliedFilters.class.getName(), appliedFilters);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    public void setAirline(Object airlineIati, Object airlineParto, Boolean twoWays) {
        try {
            this.twoWays = twoWays;
            LinkedTreeMap<String, String> objectIati = (LinkedTreeMap<String, String>) airlineIati;
            LinkedTreeMap<String, String> objectParto = (LinkedTreeMap<String, String>) airlineParto;
            if (objectIati == null)
                objectIati = new LinkedTreeMap<>();
            if (objectParto == null)
                objectParto = new LinkedTreeMap<>();
            objectIati.putAll(objectParto);
            objectAirline = objectIati;
        } catch (Exception e) {
            e.getMessage();
        }


    }

    //-----------------------------------------------
    @Override

    public void setupDialog(Dialog dialog, int style) {
        contentView = View.inflate(getContext(), R.layout.new_fragment_dialog_filter_view, null);
        resetFilterView();
        super.setupDialog(dialog, style); //call super at last
    }

    //-----------------------------------------------
    private void resetFilterView() {
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
        setupStops(rl_content);
        setupTimeTakeOff(rl_content);
        setupTimeTakeOffReturn(rl_content);
        setupAirLine(rl_content);
        setAnimationDuration(300); //optional; default 500ms
        setCallbacks(callbacks); //optional; to get back result
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView);
    }

    //-----------------------------------------------
    private void sort(View contentView) {
        spSort = (AppCompatSpinner) contentView.findViewById(R.id.spSort);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortInternationalFlight, R.layout.row_item_spinner_train_white);
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
    private void setupStops(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutStops);
        ArrayList<String> listStops = new ArrayList<>();
        listStops.add("بدون توقف");
        listStops.add("1 توقف");
        listStops.add("+ 2 توقف");
        for (int index = 0; index < listStops.size(); index++) {
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(index);
            Integer tag = (Integer) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_STOPS) != null &&
                    applied_filters.get(FILTER_CATEGORY_STOPS).contains(String.valueOf(tag))) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_STOPS, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_STOPS, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(listStops.get(index));
            linearLayout.addView(checkBox);
        }
    }

    //-----------------------------------------------
    private void setupTimeTakeOff(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutTimeTakeOff);
        ArrayList<String> listStops = new ArrayList<>();
        listStops.add(getString(R.string.sortByTimeTitleMorning) + "(" + getString(R.string.sortByTimeValueMorning) + ")");
        listStops.add(getString(R.string.sortByTimeTitleNoon) + "(" + getString(R.string.sortByTimeValueNoon) + ")");
        listStops.add(getString(R.string.sortByTimeTitleAfternoon) + "(" + getString(R.string.sortByTimeValueAfternoon) + ")");
        listStops.add(getString(R.string.sortByTimeTitleEvening) + "(" + getString(R.string.sortByTimeValueEvening) + ")");
        listStops.add(getString(R.string.sortByTimeTitleSoonMorning) + "(" + getString(R.string.sortByTimeValueSoonMorning) + ")");
        for (int index = 0; index < listStops.size(); index++) {
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(index);
            Integer tag = (Integer) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_TIME_TAKE_OFF) != null &&
                    applied_filters.get(FILTER_CATEGORY_TIME_TAKE_OFF).contains(String.valueOf(tag))) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_TIME_TAKE_OFF, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_TIME_TAKE_OFF, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(listStops.get(index));
            linearLayout.addView(checkBox);
        }
    }

    //-----------------------------------------------
    private void setupTimeTakeOffReturn(View contentView) {
        LinearLayout layoutReturn = (LinearLayout) contentView.findViewById(R.id.layoutReturn);
        if (!twoWays) {
            layoutReturn.setVisibility(View.GONE);
            return;
        }
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutTimeTakeOffReturn);
        layoutReturn.setVisibility(View.VISIBLE);
        ArrayList<String> listStops = new ArrayList<>();
        listStops.add(getString(R.string.sortByTimeTitleMorning) + "(" + getString(R.string.sortByTimeValueMorning) + ")");
        listStops.add(getString(R.string.sortByTimeTitleNoon) + "(" + getString(R.string.sortByTimeValueNoon) + ")");
        listStops.add(getString(R.string.sortByTimeTitleAfternoon) + "(" + getString(R.string.sortByTimeValueAfternoon) + ")");
        listStops.add(getString(R.string.sortByTimeTitleEvening) + "(" + getString(R.string.sortByTimeValueEvening) + ")");
        listStops.add(getString(R.string.sortByTimeTitleSoonMorning) + "(" + getString(R.string.sortByTimeValueSoonMorning) + ")");
        for (int index = 0; index < listStops.size(); index++) {
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(index);
            Integer tag = (Integer) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_TIME_TAKE_OFF_RETURN) != null &&
                    applied_filters.get(FILTER_CATEGORY_TIME_TAKE_OFF_RETURN).contains(String.valueOf(tag))) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_TIME_TAKE_OFF_RETURN, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_TIME_TAKE_OFF_RETURN, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(listStops.get(index));
            linearLayout.addView(checkBox);
        }
    }

    //-----------------------------------------------
    private void setupAirLine(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutAirLine);
        Iterator<Map.Entry<String, String>> iterator = objectAirline.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> item = iterator.next();
            String value = item.getValue();
            String key = item.getKey();
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(key);
            String tag = (String) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_AIRLINE) != null &&
                    applied_filters.get(FILTER_CATEGORY_AIRLINE).contains(tag)) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_AIRLINE, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_AIRLINE, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(value);
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
    private void clearFromSelectedMap(String key) {
        try {
            applied_filters.remove(key);
        } catch (Exception e) {

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
    public void resetFilter() {
        applied_filters = new ArrayMap<>();
    }
}