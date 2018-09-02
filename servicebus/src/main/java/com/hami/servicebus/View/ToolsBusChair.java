package com.hami.servicebus.View;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.servicebus.R;


public class ToolsBusChair extends RelativeLayout {

    //-----------------------------------------------
    private TextView txtChairNumber;
    private FragmentActivity context;
    private ImageView imgChair;
    //-----------------------------------------------
    public final static int CHAIR_TYPE_CAN_SELECT = 0;
    public final static int CHAIR_TYPE_FOR_MEN = 2;
    public final static int CHAIR_TYPE_FOR_WOMEN = 1;
    public final static int CHAIR_TYPE_SELECTED = 3;
    //-----------------------------------------------
    public int currentChairType = 0;
    private SelectChairListener selectChair;

    //-----------------------------------------------
    public ToolsBusChair(FragmentActivity context, @Nullable int chairType, String chairNumber) {
        super(context);
        currentChairType = chairType;
        this.context = context;
        ini(chairNumber);
    }
    //-----------------------------------------------
    private void ini(String chairNumber) {
        LayoutInflater.from(getContext()).inflate(R.layout.row_service_bus_chair, this);
        if (currentChairType < 1 && currentChairType > 4) {
            findViewById(R.id.layoutChair).setVisibility(INVISIBLE);
            return;
        }
        this.setOnClickListener(onClickListener);
        imgChair = (ImageView) findViewById(R.id.imgChair);
        txtChairNumber = (TextView) findViewById(R.id.txtChairNumber);
        setImageByTypeChair();
        setChairNumber(chairNumber);
    }

    public void setSelectChairCallBack(SelectChairListener selectChair) {
        this.selectChair = selectChair;
    }

    //-----------------------------------------------
    private void setImageByTypeChair() {
        switch (currentChairType) {
            case CHAIR_TYPE_CAN_SELECT:
                //imgChair.setColorFilter(ContextCompat.getColor(context,R.color.white));
                break;
            case CHAIR_TYPE_FOR_MEN:
                imgChair.setColorFilter(ContextCompat.getColor(context, R.color.blueForMan));
                break;
            case CHAIR_TYPE_FOR_WOMEN:
                imgChair.setColorFilter(ContextCompat.getColor(context, R.color.redForWoman));
                break;
            default:

        }
    }

    //-----------------------------------------------
    private void setChairNumber(String chairNumber) {
        txtChairNumber.setText(chairNumber);
    }

    //-----------------------------------------------
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentChairType == CHAIR_TYPE_CAN_SELECT) {
                imgChair.setColorFilter(ContextCompat.getColor(context, R.color.greenSelectedChair));
                currentChairType = CHAIR_TYPE_SELECTED;
                selectChair.onSelectChair(txtChairNumber.getText().toString());
            } else if (currentChairType == CHAIR_TYPE_SELECTED) {
                imgChair.setColorFilter(ContextCompat.getColor(context, android.R.color.transparent));
                currentChairType = CHAIR_TYPE_CAN_SELECT;
                selectChair.onDeSelectChair(txtChairNumber.getText().toString());
            }
        }
    };
    //-----------------------------------------------
}
