package com.hami.common.View;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.R;
import com.hami.common.Util.UtilFonts;


/**
 * Created by renjer on 1/11/2017.
 */

public class AddToolsTourOption extends LinearLayout {

    private ImageView btnAddAdults, btnMinusAdults, btnAddChildren, btnMinusChildren, btnAddInfant, btnMinusInfant;
    private TextView txtNumberInfant, txtNumberChildren, txtNumberAdults;
    private int maxPassenger = 0;
    public int countPassenger = maxPassenger;
    private TextView txtAdultPrice, txtInfantPrice, txtChildPrice;


    //-----------------------------------------------
    public AddToolsTourOption(Context context) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public AddToolsTourOption(Context context, String adults, String child, String infant) {
        super(context);
        ini(context);
        setPassenger(adults, child, infant);
    }

    //-----------------------------------------------
    public AddToolsTourOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tools_tour_passenger, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        txtAdultPrice = (TextView) findViewById(R.id.txtAdultPrice);
        txtChildPrice = (TextView) findViewById(R.id.txtChildPrice);
        txtInfantPrice = (TextView) findViewById(R.id.txtInfantPrice);

        btnAddAdults = (ImageView) findViewById(R.id.btnAddAdults);
        btnAddInfant = (ImageView) findViewById(R.id.btnAddInfant);
        btnAddChildren = (ImageView) findViewById(R.id.btnAddChildren);
        btnMinusAdults = (ImageView) findViewById(R.id.btnMinusAdults);
        btnMinusChildren = (ImageView) findViewById(R.id.btnMinusChildren);
        btnMinusInfant = (ImageView) findViewById(R.id.btnMinusInfant);


        btnAddInfant.setOnClickListener(onClickAddListener);
        btnAddAdults.setOnClickListener(onClickAddListener);
        btnAddChildren.setOnClickListener(onClickAddListener);

        btnMinusAdults.setOnClickListener(onClickMinusListener);
        btnMinusChildren.setOnClickListener(onClickMinusListener);
        btnMinusInfant.setOnClickListener(onClickMinusListener);

        txtNumberAdults = (TextView) findViewById(R.id.txtNumberAdults);
        txtNumberChildren = (TextView) findViewById(R.id.txtNumberChildren);
        txtNumberInfant = (TextView) findViewById(R.id.txtNumberInfant);
        countPassenger--;

    }

    public void setMaxPassenger(int maxPassenger) {
        this.maxPassenger = maxPassenger;
    }

    //-----------------------------------------------
    public int getMaxPassenger() {
        return maxPassenger;
    }

    //-----------------------------------------------
    public void setPassengerPrice(String adults, String child, String infant) {
        txtAdultPrice.setText(adults);
        txtChildPrice.setText(child);
        txtInfantPrice.setText(infant);
    }

    //-----------------------------------------------
    public void setPassenger(String adults, String child, String infant) {
        txtNumberAdults.setText(adults);
        txtNumberInfant.setText(infant);
        txtNumberChildren.setText(child);
    }

    //-----------------------------------------------
    OnClickListener onClickAddListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer currentValue = 0;
            int i = v.getId();
            if (i == R.id.btnAddAdults) {
                currentValue = Integer.valueOf(txtNumberAdults.getText().toString());
                if (getRemainingPassenger() >= 1) {
                    currentValue++;
                    txtNumberAdults.setText(String.valueOf(currentValue));
                }

            } else if (i == R.id.btnAddInfant) {
                currentValue = Integer.valueOf(txtNumberInfant.getText().toString());
                int currentValueAdults = Integer.valueOf(txtNumberAdults.getText().toString());
                if (getRemainingPassenger() >= 1 && currentValue < currentValueAdults) {
                    currentValue++;
                    txtNumberInfant.setText(String.valueOf(currentValue));
                }

            } else if (i == R.id.btnAddChildren) {
                currentValue = Integer.valueOf(txtNumberChildren.getText().toString());
                if (getRemainingPassenger() >= 1) {
                    currentValue++;
                    txtNumberChildren.setText(String.valueOf(currentValue));
                }

            }
        }
    };
    //-----------------------------------------------
    OnClickListener onClickMinusListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            Integer currentValue = 0;
            int i = v.getId();
            if (i == R.id.btnMinusAdults) {
                currentValue = Integer.valueOf(txtNumberAdults.getText().toString());
                if (currentValue != 1) {
                    currentValue--;
                    txtNumberAdults.setText(String.valueOf(currentValue));
                }

            } else if (i == R.id.btnMinusInfant) {
                currentValue = Integer.valueOf(txtNumberInfant.getText().toString());
                if (currentValue != 0) {
                    currentValue--;
                    txtNumberInfant.setText(String.valueOf(currentValue));
                }

            } else if (i == R.id.btnMinusChildren) {
                currentValue = Integer.valueOf(txtNumberChildren.getText().toString());
                if (currentValue != 0) {
                    currentValue--;
                    txtNumberChildren.setText(String.valueOf(currentValue));
                }

            }
        }
    };

    //-----------------------------------------------
    public Boolean validate(RelativeLayout coordinator) {
        int adults = Integer.valueOf(txtNumberAdults.getText().toString());
        int child = Integer.valueOf(txtNumberChildren.getText().toString());
        int infant = Integer.valueOf(txtNumberInfant.getText().toString());
        if (infant > 0 && adults != infant) {
            Snackbar.make(coordinator, R.string.validateInfant, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (child > 0 && adults == 0) {
            Snackbar.make(coordinator, R.string.validateChild, Snackbar.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //-----------------------------------------------
    public int getRemainingPassenger() {
        int remainingPassenger = Integer.valueOf(txtNumberAdults.getText().toString()) + Integer.valueOf(txtNumberInfant.getText().toString()) + Integer.valueOf(txtNumberChildren.getText().toString());
        return maxPassenger - remainingPassenger;
    }

    //-----------------------------------------------
    public int getCountPassenger() {
        int remainingPassenger = Integer.valueOf(txtNumberAdults.getText().toString()) + Integer.valueOf(txtNumberInfant.getText().toString()) + Integer.valueOf(txtNumberChildren.getText().toString());
        return remainingPassenger;
    }

    //-----------------------------------------------
    public String getNumberAdults() {
        return (txtNumberAdults.getText().toString().equals("1")) ? "1" : txtNumberAdults.getText().toString();
    }

    //-----------------------------------------------
    public String getNumberChildren() {
        return (txtNumberChildren.getText().toString().equals("0")) ? "0" : txtNumberChildren.getText().toString();
    }

    //-----------------------------------------------
    public String getNumberInfant() {
        return (txtNumberInfant.getText().toString().equals("0")) ? "0" : txtNumberInfant.getText().toString();
    }
    //public void btnAddAdults
}
