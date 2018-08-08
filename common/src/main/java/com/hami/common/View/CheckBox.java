package com.hami.common.View;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hami.common.R;
import com.hami.common.Util.UtilFonts;



public class CheckBox extends RelativeLayout {

    private TextView txtTitle, txtCounter;
    private AppCompatCheckBox checkBox;
    private View view;

    //-----------------------------------------------
    public CheckBox(Context context) {
        super(context);
        ini(context);
    }

    public CheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_checkbox, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtCounter = (TextView) findViewById(R.id.txtCounter);
        checkBox = (AppCompatCheckBox) findViewById(R.id.checkBox);
    }

    //-----------------------------------------------
    public void setTitle(int title) {
        txtTitle.setText(title);
    }

    //-----------------------------------------------
    public void setTitle(String title) {
        txtTitle.setText(title);
    }

    //-----------------------------------------------
    public void setCounter(int title) {
        txtCounter.setText(title);
    }

    //-----------------------------------------------
    public void setCounter(String title) {
        txtCounter.setText(title);
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
