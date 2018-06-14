package hami.aniticket.View.Progressbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;

/**
 * Created by renjer on 2017-07-17.
 */

public class ButtonWithProgress extends LinearLayout {

    private ProgressBar smoothProgressBar;
    private AppCompatButton buttonProgress;
    private String normalTitle, startTitleProgress, finishTitleProgress;
    private Context context;

    //-----------------------------------------------
    public ButtonWithProgress(Context context) {
        super(context);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    public ButtonWithProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_button_with_progress, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        buttonProgress = findViewById(R.id.buttonProgress);
        smoothProgressBar = findViewById(R.id.progressBar);
    }

    //-----------------------------------------------
    public void setCallBack(OnClickListener onClickListener) {
        buttonProgress.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    public void setEnableButton(Boolean status) {
        buttonProgress.setEnabled(status);
    }

    //-----------------------------------------------
    public void setConfig(String normalTitle, String startTitleProgress, String finishTitleProgress) {
        this.normalTitle = normalTitle;
        this.startTitleProgress = startTitleProgress;
        this.finishTitleProgress = finishTitleProgress;
        buttonProgress.setText(normalTitle);
    }

    //-----------------------------------------------
    public void setConfig(int normalTitle, int startTitleProgress, int finishTitleProgress) {
        this.normalTitle = context.getString(normalTitle);
        this.startTitleProgress = context.getString(startTitleProgress);
        this.finishTitleProgress = context.getString(finishTitleProgress);
        buttonProgress.setText(normalTitle);
    }

    //-----------------------------------------------
    public void startProgress() {
        smoothProgressBar.setVisibility(VISIBLE);
        buttonProgress.setText(startTitleProgress);
    }

    //-----------------------------------------------
    public void setBackgroundButton(int resource) {
        buttonProgress.setBackgroundResource(resource);
    }

    //-----------------------------------------------
    public void setBackgroundButtonColor(int resourceColor1, int resourceColor2) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_pressed}, // pressed
                new int[]{android.R.attr.state_enabled} // enabled
        };

        int[] colors = new int[]{
                ContextCompat.getColor(context, resourceColor2),
                ContextCompat.getColor(context, resourceColor1)
        };

        //buttonProgress.setSupportBackgroundTintList(new ColorStateList(states, colors));

    }

    //-----------------------------------------------
    public void stopProgress() {
        smoothProgressBar.setVisibility(GONE);
        buttonProgress.setText(finishTitleProgress);
    }
    //-----------------------------------------------

}
