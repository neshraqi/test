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

import hami.hamibelit.Activity.ServiceHotel.International.Controller.Model.InternationalHotelSearchRequest;
import hami.hamibelit.Const.FlightRules;
import hami.hamibelit.R;

/**
 * Created by renjer on 1/11/2017.
 */

public class ToolsHotelRoomCountOption extends LinearLayout {

    private TextView txtTitleRoom;
    public int countPassenger = FlightRules.MAX_PASSENGER;
    private Button btnAccept;
    private AppCompatSpinner spRoomCount;
    private LinearLayout layoutRooms;
    private Context context;

    //-----------------------------------------------
    public ToolsHotelRoomCountOption(Context context) {
        super(context);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    public ToolsHotelRoomCountOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_room_layout, this);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        txtTitleRoom = (TextView) findViewById(R.id.txtTitleRoom);
        layoutRooms = (LinearLayout) findViewById(R.id.layoutRooms);
        spRoomCount = (AppCompatSpinner) findViewById(R.id.spRoomCount);
        spRoomCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createRooms(position + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnAccept.setText(R.string.acceptEng);
    }
    InternationalHotelSearchRequest internationalHotelSearchRequest;
    //-----------------------------------------------
    public void setRoomIni(InternationalHotelSearchRequest internationalHotelSearchRequest) {
        this.internationalHotelSearchRequest = internationalHotelSearchRequest;
        Integer roomCount = Integer.valueOf(internationalHotelSearchRequest.getRooms());
        spRoomCount.setSelection(roomCount - 1, true);
    }

    //-----------------------------------------------
    public String getRoomCount() {
        try {
            return spRoomCount.getSelectedItem().toString();
        } catch (Exception e) {
            return "1";
        }

    }

    //-----------------------------------------------
    private void createRooms(int count) {
        int currentRoomCount = layoutRooms.getChildCount();
        int newRoomCount = count;
        if (newRoomCount > currentRoomCount) {
            for (currentRoomCount = layoutRooms.getChildCount(); currentRoomCount < count; currentRoomCount++) {
                ToolsHotelRoomOption toolsHotelRoomOption = new ToolsHotelRoomOption(context);
                toolsHotelRoomOption.setRoomTitle(currentRoomCount + 1);
                layoutRooms.addView(toolsHotelRoomOption);
            }
        } else if (newRoomCount < currentRoomCount) {
            for (currentRoomCount = layoutRooms.getChildCount(); currentRoomCount > newRoomCount; ) {
                currentRoomCount--;
                layoutRooms.removeViewAt(currentRoomCount);
            }
        }


    }

    //-----------------------------------------------
    public ArrayList<String> getAdultsList() {
        ArrayList<String> results = new ArrayList<>();
        //JSONArray jsonArray = new JSONArray();
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                results.add(toolsHotelRoomOption.getAdults());
                //jsonArray.put(index, toolsHotelRoomOption.getAdults());
            }
        } catch (Exception e) {
        }
        return results;

    }

    //-----------------------------------------------
    public String getAllAdultCount() {
        Integer value = 0;
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                value += Integer.valueOf(toolsHotelRoomOption.getAdults());
            }
        } catch (Exception e) {
        }
        return String.valueOf(value);

    }

    //-----------------------------------------------
    public String getAllChildCount() {
        Integer value = 0;
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                value += Integer.valueOf(toolsHotelRoomOption.getChild());
            }
        } catch (Exception e) {
        }
        return String.valueOf(value);
    }

    //-----------------------------------------------
    public ArrayList<String> getChildAge() {
        ArrayList<String> results = new ArrayList<>();
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                results.add(toolsHotelRoomOption.getAllChildCount());
            }
        } catch (Exception e) {
        }
        return results;
    }

    //-----------------------------------------------
    public ArrayList<ArrayList<String>> getChildAges() {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        //JSONArray jsonArray = new JSONArray();
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                results.add(toolsHotelRoomOption.getChildAge());
            }
        } catch (Exception e) {
        }
        return results;
    }

    //-----------------------------------------------
    public String getChildAgesJson() {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                jsonArray.put(toolsHotelRoomOption.getChildAgeJson());
                //results.add(toolsHotelRoomOption.getChildAgeJson());
            }
        } catch (Exception e) {
        }
        return jsonArray.toString();
    }

    //-----------------------------------------------
    public ArrayList<String> getChildList() {
        ArrayList<String> results = new ArrayList<>();
        //JSONArray jsonArray = new JSONArray();
        try {
            for (int index = 0; index < layoutRooms.getChildCount(); index++) {
                ToolsHotelRoomOption toolsHotelRoomOption = (ToolsHotelRoomOption) layoutRooms.getChildAt(index);
                results.add(toolsHotelRoomOption.getChild());
                //jsonArray.put(index, toolsHotelRoomOption.getChild());
            }
        } catch (Exception e) {

        }
        return results;
    }

    //-----------------------------------------------
    public void setCallbackAcceptButton(OnClickListener callbackAcceptButton) {
        btnAccept.setOnClickListener(callbackAcceptButton);
    }
    //-----------------------------------------------
}
