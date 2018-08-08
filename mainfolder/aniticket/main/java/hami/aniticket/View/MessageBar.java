package hami.mainapp.View;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import hami.mainapp.R;


public class MessageBar extends RelativeLayout {

    private TextView tvTitleCenter;
    private AppCompatButton tvButtonRetry;
    private View view;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    //-----------------------------------------------
    public MessageBar(Context context) {
        super(context);
        ini(context);
    }

    public MessageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.include_layout_error_message, this);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.progress);
        tvTitleCenter = (TextView) findViewById(R.id.tvTitleCenter);
        tvButtonRetry = (AppCompatButton) findViewById(R.id.tvButtonRetry);
        avLoadingIndicatorView.setVisibility(GONE);
//        tvButtonRetry.setPaintFlags(tvButtonRetry.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    //-----------------------------------------------
    public void setMainBackground(int color) {
        setBackgroundColor(getResources().getColor(color));
    }

    //-----------------------------------------------
    public void setTitleButton(int titleButton) {
        tvButtonRetry.setText(titleButton);
    }

    //-----------------------------------------------
    public void setTitleButton(String titleButton) {
        tvButtonRetry.setText(titleButton);
    }

    //-----------------------------------------------
    public void showMessageBar(int titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleCenter.setText(titleResource);
        tvTitleCenter.setVisibility(VISIBLE);
        tvTitleCenter.setSelected(true);
    }

    //-----------------------------------------------
    public void showMessageBar(String titleResource) {
        view.setVisibility(VISIBLE);
        tvTitleCenter.setText(titleResource);
        tvTitleCenter.setVisibility(VISIBLE);
        tvTitleCenter.setSelected(true);
    }

    //-----------------------------------------------
    public void setCallbackButtonNewSearch(OnClickListener onClickListener) {
        tvButtonRetry.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    public void setVibrator() {
        Animation vibrateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        tvButtonRetry.startAnimation(vibrateAnimation);
    }

    //-----------------------------------------------
    public void showProgress(String title) {
        view.setVisibility(VISIBLE);
        tvTitleCenter.setText(title);
        avLoadingIndicatorView.setVisibility(VISIBLE);
        tvButtonRetry.setVisibility(INVISIBLE);
    }

    //-----------------------------------------------
    public void hideProgress() {
        avLoadingIndicatorView.setVisibility(GONE);
        tvButtonRetry.setVisibility(INVISIBLE);
    }

    //-----------------------------------------------
    public void hideMessageBar() {
        view.setVisibility(GONE);
    }
}
