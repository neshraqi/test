package hami.aniticket.Activity.ServiceSearch.ServiceBus.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import hami.aniticket.BaseController.AppliedFilters;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.CheckBox;


public class NewDesignFilterBusFragmentDialog extends AAH_FabulousFragment {


    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    private AppCompatSeekBar seekBarPrice;
    private DisplayMetrics metrics;
    private static Callbacks callbacks;
    public final static String FILTER_CATEGORY_Bus_TYPE = "fcbt";
    public final static String FILTER_CATEGORY_TIME_TAKE_OFF = "fctto";
    public final static String FILTER_CATEGORY_SORT = "fcs";
    public final static String FILTER_CATEGORY_PRICE = "fcp";
    private AppCompatSpinner spSort;
    private AppliedFilters appliedFilters;
    private View contentView;
    public final static int STEP_SIZE = 5;
    private static final String TAG = "NewDesignFilterBusFragmentDialog";
    //-----------------------------------------------
    public static NewDesignFilterBusFragmentDialog newInstance(Callbacks callbacks) {
        NewDesignFilterBusFragmentDialog.callbacks = callbacks;
        NewDesignFilterBusFragmentDialog mff = new NewDesignFilterBusFragmentDialog();

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
            appliedFilters = new AppliedFilters(applied_filters);
            outState.putSerializable(AppliedFilters.class.getName(), appliedFilters);
        }
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    @Override

    public void setupDialog(Dialog dialog, int style) {

        //if (contentView == null)
        contentView = View.inflate(getContext(), R.layout.fragment_bus_dialog_filter_view, null);
        resetFilterView();
        super.setupDialog(dialog, style); //call super at last
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
        price(rl_content);
        setTypeBus(rl_content);
        setupTimeTakeOff(rl_content);
        setAnimationDuration(400); //optional; default 500ms
        //setPeekHeight(300); // optional; default 400dp
        setCallbacks(callbacks); //optional; to get back result
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView);
    }

    //-----------------------------------------------
    private void sort(View contentView) {
        spSort = (AppCompatSpinner) contentView.findViewById(R.id.spSort);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.sortBus, R.layout.row_item_spinner_train_white);
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
    private void price(View contentView) {
        final TextView tvMaxValue = (TextView) contentView.findViewById(R.id.tvMaxValue);
        seekBarPrice = (AppCompatSeekBar) contentView.findViewById(R.id.seekBarPrice);
        seekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 0) {
                    String res = "همه قیمت ها";
                    tvMaxValue.setText(res);
                } else {

                    progress = (progress == 0 || progress == 1) ? 50 : (progress * STEP_SIZE);
                    String res = "کمتر از " + NumberFormat.getNumberInstance(Locale.US).format(progress * 1000) + " تومان";
                    tvMaxValue.setText(res);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                clearFromSelectedMap(FILTER_CATEGORY_PRICE);
                if (progress != 0) {
                    addToSelectedMap(FILTER_CATEGORY_PRICE, String.valueOf(progress));
                }

            }
        });
        if (applied_filters != null && applied_filters.get(FILTER_CATEGORY_PRICE) != null) {
            int value = Integer.valueOf(applied_filters.get(FILTER_CATEGORY_PRICE).get(0));
            seekBarPrice.setProgress(value);
        } else {
            seekBarPrice.setProgress(0);
        }
    }

    //-----------------------------------------------
    private void setTypeBus(View contentView) {
        LinearLayout linearLayout = (LinearLayout) contentView.findViewById(R.id.layoutTrainType);
        final ArrayList<String> listTypeBus = new ArrayList<>();
        listTypeBus.add("معمولی");
        listTypeBus.add("وی آی پی(VIP)");
        for (int index = 0; index < listTypeBus.size(); index++) {
            final CheckBox checkBox = new CheckBox(getActivity());
            checkBox.setTag(index);
            Integer tag = (Integer) checkBox.getTag();
            if (applied_filters != null &&
                    applied_filters.get(FILTER_CATEGORY_Bus_TYPE) != null &&
                    applied_filters.get(FILTER_CATEGORY_Bus_TYPE).contains(String.valueOf(tag))) {
                checkBox.setCheck(true);
            } else {
                checkBox.setCheck(false);
            }
            checkBox.setCallBack(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        addToSelectedMap(FILTER_CATEGORY_Bus_TYPE, String.valueOf(checkBox.getTag()));
                    else
                        removeFromSelectedMap(FILTER_CATEGORY_Bus_TYPE, String.valueOf(checkBox.getTag()));


                }
            });
            checkBox.setTitle(listTypeBus.get(index));
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