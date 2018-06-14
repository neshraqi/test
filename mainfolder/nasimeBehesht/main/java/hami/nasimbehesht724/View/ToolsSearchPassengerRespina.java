package hami.nasimbehesht724.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hami.nasimbehesht724.R;


public class ToolsSearchPassengerRespina extends LinearLayout {

    private TextView txtCountPassenger, txtTypePassenger;
    private LinearLayout layoutPassengerCount;
    //-----------------------------------------------
    public ToolsSearchPassengerRespina(Context context) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public ToolsSearchPassengerRespina(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_tools_service_search, this);
        layoutPassengerCount = (LinearLayout) findViewById(R.id.layoutPassengerCount);
        txtCountPassenger = (TextView) findViewById(R.id.txtCountPassenger);
        txtTypePassenger = (TextView) findViewById(R.id.txtTypePassenger);
        layoutPassengerCount.setOnClickListener(onClickListener);
        txtTypePassenger.setOnClickListener(onClickListener);
    }
    //-----------------------------------------------
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.layoutPassengerCount:
                    break;
                case R.id.txtTypePassenger:
                    break;
            }
        }
    };
    //-----------------------------------------------
    private void showDialogPassengerFlightInternational(){

    }
}
