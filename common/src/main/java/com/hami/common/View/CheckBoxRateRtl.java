package com.hami.common.View;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.R;
import com.hami.common.Util.UtilFonts;

public class CheckBoxRateRtl extends RelativeLayout {

    private AppCompatCheckBox checkBox;
    private View view;
    private RatingBar ratingBar;
    private TextView txtTitle;

    //-----------------------------------------------
    public CheckBoxRateRtl(Context context) {
        super(context);
        ini(context);
    }

    public CheckBoxRateRtl(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_checkbox_rate_rtl, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        ratingBar = (RatingBar) findViewById(R.id.rbHotelRate);
        checkBox = (AppCompatCheckBox) findViewById(R.id.checkBox);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
    }

    //-----------------------------------------------
    public void setRatingBar(int numRate, int currentRate) {
        ratingBar.setVisibility(VISIBLE);
        txtTitle.setVisibility(INVISIBLE);
        ratingBar.setNumStars(numRate);
        ratingBar.setRating(currentRate);
    }

    //-----------------------------------------------
    public void showText(String title) {
        ratingBar.setVisibility(INVISIBLE);
        txtTitle.setVisibility(VISIBLE);
        txtTitle.setText(title);
    }

    //-----------------------------------------------
    public void setCheck(Boolean checked) {
        checkBox.setChecked(checked);
    }

    //-----------------------------------------------
    public void setCallBack(CompoundButton.OnCheckedChangeListener callBack) {
        checkBox.setOnCheckedChangeListener(callBack);
    }

    public Boolean hasCheck() {
        return checkBox.isChecked();
    }
}
