package hami.mainapp.View;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;


public class CheckBoxLtr extends RelativeLayout {

    private TextView txtTitle;
    private AppCompatCheckBox checkBox;
    private View view;

    //-----------------------------------------------
    public CheckBoxLtr(Context context) {
        super(context);
        ini(context);
    }

    public CheckBoxLtr(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_checkbox_ltr, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        checkBox = (AppCompatCheckBox) findViewById(R.id.checkBox);
        txtTitle.setSelected(true);
    }
    //-----------------------------------------------
    public void setCallBackTitle(OnClickListener onClickListener) {
        txtTitle.setOnClickListener(onClickListener);
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
    public void setTitleWithUnderLine(int title) {
        txtTitle.setText(title);
        setUnderLineText();
    }

    //-----------------------------------------------
    public void setTitleWithUnderLine(String title) {
        txtTitle.setText(title);
        setUnderLineText();
    }

    //-----------------------------------------------
    public void setCheck(Boolean checked) {
        checkBox.setChecked(checked);
    }

    //-----------------------------------------------
    public void setUnderLineText() {
        txtTitle.setPaintFlags(txtTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    //-----------------------------------------------
    public void setCallBack(CompoundButton.OnCheckedChangeListener callBack) {
        checkBox.setOnCheckedChangeListener(callBack);
    }

    public Boolean hasCheck() {
        return checkBox.isChecked();
    }
}
