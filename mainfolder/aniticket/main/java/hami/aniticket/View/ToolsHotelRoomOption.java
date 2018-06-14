package hami.hamibelit.View;

import android.content.Context;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

import hami.hamibelit.Const.FlightRules;
import hami.hamibelit.R;

/**
 * Created by renjer on 1/11/2017.
 */

public class ToolsHotelRoomOption extends LinearLayout {

    private TextView txtTitleRoom;
    public int countPassenger = FlightRules.MAX_PASSENGER;
    private Button btnAccept;
    private AppCompatSpinner spAdultCount;
    private AppCompatSpinner spChildCount;
    private LinearLayout layoutChildAge;
    private Context context;

    //-----------------------------------------------
    public ToolsHotelRoomOption(Context context) {
        super(context);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
//    public ToolsHotelRoomOption(Context context, String adults, String child) {
//        super(context);
//        ini(context);
//        setPassenger(adults, child, infant);
//    }

    //-----------------------------------------------
    public ToolsHotelRoomOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_child_room_layout, this);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        txtTitleRoom = (TextView) findViewById(R.id.txtTitleRoom);
        txtTitleRoom.setText(context.getString(R.string.roomEng) + " " + 1);
        layoutChildAge = (LinearLayout) findViewById(R.id.layoutChildAge);
        spAdultCount = (AppCompatSpinner) findViewById(R.id.spAdultCount);
        spChildCount = (AppCompatSpinner) findViewById(R.id.spChildCount);

        spChildCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    createChild(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //-----------------------------------------------
    public void setAdultCountIni(int count){

    }
    //-----------------------------------------------
    public void setChildCountIni(int count){

    }
    //-----------------------------------------------
    public void setRoomTitle(int index) {
        txtTitleRoom.setText(context.getString(R.string.roomEng) + " " + index);
    }

    //-----------------------------------------------
    public String getAdults() {
        try {
            String countAdults = (String) spAdultCount.getSelectedItem();
            return countAdults;
        } catch (Exception e) {
            return "1";
        }
    }

    //-----------------------------------------------
    public String getChild() {
        try {
            String countChild = (String) spChildCount.getSelectedItem();
            return countChild;
        } catch (Exception e) {
            return "0";
        }
    }

    //-----------------------------------------------
    public ArrayList<String> getChildAge() {
        ArrayList<String> results = new ArrayList<>();
        try {
            for (int index = 0; index < layoutChildAge.getChildCount(); index++) {
                ToolsHotelChildAge toolsHotelChildAge = (ToolsHotelChildAge) layoutChildAge.getChildAt(index);
                results.add(toolsHotelChildAge.getChildAge());
            }
        } catch (Exception e) {
        }
        return results;
    }

    //-----------------------------------------------
    public String getChildAgeJson() {
        ArrayList<String> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int index = 0; index < layoutChildAge.getChildCount(); index++) {
                ToolsHotelChildAge toolsHotelChildAge = (ToolsHotelChildAge) layoutChildAge.getChildAt(index);
                jsonArray.put(toolsHotelChildAge.getChildAge());
                //results.add(toolsHotelChildAge.getChildAge());
            }
        } catch (Exception e) {
        }
        return jsonArray.toString();
    }

    //-----------------------------------------------
    public String getAllChildCount() {
        Integer value = 0;
        try {
            for (int index = 0; index < layoutChildAge.getChildCount(); index++) {
                ToolsHotelChildAge toolsHotelChildAge = (ToolsHotelChildAge) layoutChildAge.getChildAt(index);
                value += Integer.valueOf(toolsHotelChildAge.getChildAge());
            }
        } catch (Exception e) {
        }
        return String.valueOf(value);
    }

    //-----------------------------------------------
    private void createChild(int count) {

        layoutChildAge.removeAllViews();
        for (int index = 0; index < count; index++) {
            ToolsHotelChildAge toolsHotelChildAge = new ToolsHotelChildAge(context);
            if (index == count - 1)
                toolsHotelChildAge.visibilityCrowLowerLine(View.INVISIBLE);
            layoutChildAge.addView(toolsHotelChildAge);
        }
    }

    //-----------------------------------------------
    public void setCallbackAcceptButton(OnClickListener callbackAcceptButton) {
        btnAccept.setOnClickListener(callbackAcceptButton);
    }
    //-----------------------------------------------
}
