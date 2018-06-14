package hami.aniticket.View.Learning;

import android.app.Activity;
import android.view.View;

/**
 * Created by renjer on 1/26/2017.
 */

public class LearningClass {
    private Activity activity;
    //-----------------------------------------------

    public LearningClass(Activity activity) {
        this.activity = activity;
    }

    //-----------------------------------------------
    public void showLearning(View view, String text, String caption) {
//        TapTargetView.showFor(activity,
//                TapTarget.forView(view, text, caption)
//                        .outerCircleColor(R.color.redMaster)      // Specify a color for the outer circle
//                        .targetCircleColor(R.color.mdtp_white)   // Specify a color for the target circle
//                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
//                        .titleTextColor(R.color.mdtp_white)      // Specify the color of the title text
//                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
//                        .descriptionTextColor(R.color.redMaster)  // Specify the color of the description text
//                        .textColor(R.color.blueButton)            // Specify a color for both the title and description text
//                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
//                        .dimColor(R.color.mdtp_transparent_black)            // If set, will dim behind the view with 30% opacity of the given color
//                        .drawShadow(true)                   // Whether to draw a drop shadow or not
//                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
//                        .tintTarget(true)                   // Whether to tint the target view's color
//                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
//                        .icon(activity.getResources().getDrawable( R.drawable.bg_button_silver) )                    // Specify a custom drawable to draw as the target
//                        .targetRadius(60),                  // Specify the target radius (in dp)
//                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
//                    @Override
//                    public void onTargetClick(TapTargetView view) {
//                        super.onTargetClick(view);      // This call is optional
//                        //doSomething();
//                    }
//                });
    }
    //-----------------------------------------------
}
