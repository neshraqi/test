package hami.aniticket.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.aniticket.Const.HotelRules;
import hami.aniticket.R;


public class ToolsHotelChildAge extends RelativeLayout {

    private ImageView btnAddChild, btnMinusChild;
    private TextView titleAge, txtNumberChildAge;
    public int countPassenger = HotelRules.MAX_CHILD_AGE;

    //-----------------------------------------------
    public ToolsHotelChildAge(Context context) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public ToolsHotelChildAge(Context context, String childAge) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public ToolsHotelChildAge(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    public void visibilityCrowLowerLine(int visibility) {
        findViewById(R.id.crowLowerLine).setVisibility(visibility);
    }

    //-----------------------------------------------
    public void visibilityCrowUpperLine(int visibility) {
        findViewById(R.id.crowUpperLine).setVisibility(visibility);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        LayoutInflater.from(context).inflate(R.layout.row_child_age_hotel_layout, this);
        txtNumberChildAge = (TextView) findViewById(R.id.txtNumberChildAge);
        btnAddChild = (ImageView) findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(onClickAddListener);
        btnMinusChild = (ImageView) findViewById(R.id.btnMinusChild);
        btnMinusChild.setOnClickListener(onClickMinusListener);
        //countPassenger--;
    }

    //-----------------------------------------------
    OnClickListener onClickAddListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer currentValue = 0;
            currentValue = Integer.valueOf(txtNumberChildAge.getText().toString());
            if (currentValue < HotelRules.MAX_CHILD_AGE) {
                currentValue++;
                txtNumberChildAge.setText(String.valueOf(currentValue));
            }
        }
    };
    //-----------------------------------------------
    OnClickListener onClickMinusListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer currentValue = 0;
            currentValue = Integer.valueOf(txtNumberChildAge.getText().toString());
            if (currentValue > 0) {
                currentValue--;
                txtNumberChildAge.setText(String.valueOf(currentValue));
            }
        }
    };

    //-----------------------------------------------
    public String getChildAge() {
        try {
            String age = String.valueOf(txtNumberChildAge.getText().toString());
            return String.valueOf(age);
        } catch (Exception e) {
            return "0";
        }
    }
}
