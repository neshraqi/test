package hami.hamibelit.View;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;

/**
 * Created by renjer on 2018-02-18.
 */

public class DialogExpire {
    private AlertDialog alertDialog;

    //-----------------------------------------------
    public void show(final Activity activity, View.OnClickListener onClickListener) {
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.include_layout_expire_time_message, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            UtilFonts.overrideFonts(activity, dialogView, UtilFonts.IRAN_SANS_BOLD);
            final TextView tvTitleCenter = (TextView) dialogView.findViewById(R.id.tvTitleCenter);
            AppCompatButton tvButtonRetry = (AppCompatButton) dialogView.findViewById(R.id.tvButtonRetry);
            AppCompatButton btnBack = (AppCompatButton) dialogView.findViewById(R.id.btnBack);
            //tvTitleCenter.setText(R.string.msgErrorExpireTime);
            tvButtonRetry.setText("جستجو مجدد");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    activity.finish();
                }
            });
            tvButtonRetry.setOnClickListener(onClickListener);
            alertDialog.show();
        } catch (Exception e) {

        }

    }

    //-----------------------------------------------
    public void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }
    //-----------------------------------------------
}
