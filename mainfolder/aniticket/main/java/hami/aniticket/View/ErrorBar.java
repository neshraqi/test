package hami.hamibelit.View;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.hamibelit.R;


public class ErrorBar extends RelativeLayout {

    private TextView tvTitleCenter;
    private TextView tvButtonRetry;
    private View view;

    //-----------------------------------------------
    public ErrorBar(Context context) {
        super(context);
        ini(context);
    }

    public ErrorBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_toast_msg_background, this);
        tvTitleCenter = (TextView) findViewById(R.id.txtTitleMessage);
        tvButtonRetry = (TextView) findViewById(R.id.txtReTry);
        tvButtonRetry.setPaintFlags(tvButtonRetry.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }



    //-----------------------------------------------
    public void showMessageBar(int titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleCenter.setText(titleResource);
    }

    //-----------------------------------------------
    public void showMessageBar(String titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleCenter.setText(titleResource);
    }

    //-----------------------------------------------
    public void setCallbackButtonNewSearch(OnClickListener onClickListener) {
        tvButtonRetry.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    public void hideMessageBar() {
        view.setVisibility(INVISIBLE);
    }
}
