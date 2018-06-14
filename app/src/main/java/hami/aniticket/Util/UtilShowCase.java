package hami.aniticket.Util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

/**
 * Created by renjer on 2017-07-12.
 */

public class UtilShowCase {
    private Activity context;

    public UtilShowCase(Activity context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void show(View view , String title,String content , SpotlightListener spotlightListener ) {
        new SpotlightView.Builder(context)
                .introAnimationDuration(400)
                .enableRevealAnimation(true)
                .performClick(true)
                .fadeinTextDuration(400)
                .headingTvColor(Color.parseColor("#eb273f"))
                .headingTvSize(32)
                .headingTvText(title)
                .subHeadingTvColor(Color.parseColor("#ffffff"))
                .subHeadingTvSize(16)
                .subHeadingTvText(content)
                .maskColor(Color.argb(200,101,101,101))
                .target(view)
                .setListener(spotlightListener)
                .lineAnimDuration(400)
                .lineAndArcColor(Color.parseColor("#eb273f"))
                .dismissOnTouch(true)
                .dismissOnBackPress(true)
                .enableDismissAfterShown(true)
                .usageId(String.valueOf(view.getId())) //UNIQUE ID
                .show();
    }
}
