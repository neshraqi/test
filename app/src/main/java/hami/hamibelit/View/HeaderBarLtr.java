package hami.hamibelit.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.hamibelit.R;


public class HeaderBarLtr extends RelativeLayout {

    private TextView tvTitleHeader;
    private View view;
    private ProgressBar progressBarRespina;

    //-----------------------------------------------
    public HeaderBarLtr(Context context) {
        super(context);
        ini(context);
    }

    public HeaderBarLtr(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_tools_header_bar_layout_ltr, this);
        tvTitleHeader = (TextView) findViewById(R.id.tvTitleHeader);
        progressBarRespina = (ProgressBar) findViewById(R.id.progressBarRespina);
        progressBarRespina.getIndeterminateDrawable().setColorFilter(Color.parseColor("#f47a4d"), PorterDuff.Mode.MULTIPLY);
        tvTitleHeader.setSelected(true);
    }

    //-----------------------------------------------
    public void showMessageBar(int titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleHeader.setText(titleResource);

    }

    //-----------------------------------------------
    public void hideProgress() {
        progressBarRespina.setVisibility(INVISIBLE);
    }

    public void showProgress() {
        progressBarRespina.setVisibility(VISIBLE);
    }

    //-----------------------------------------------
    public void showMessageBar(String titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleHeader.setText(titleResource);
    }

    //-----------------------------------------------
    public void hideMessageBar() {
        progressBarRespina.setVisibility(GONE);
    }
}
