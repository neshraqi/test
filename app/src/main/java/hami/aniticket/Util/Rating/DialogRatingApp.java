package hami.aniticket.Util.Rating;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;



import hami.aniticket.Activity.Authentication.Controller.UserApi;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.BaseResult;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.ToastMessageBar;


public class DialogRatingApp {

    private FragmentActivity activity;
    private AlertDialog alertDialog;
    private EditText edtComment;
    private TextView txtRateValue;
    private static final String TAG = "DialogRatingApp";
    private RatingBar ratingBar;
    private String[] ratingString = {"جالب نیست", "بد نیست", "خوبه،باید بهتر بشه", "خیلی خوبه", "عالیه"};

    //-----------------------------------------------
    public DialogRatingApp(FragmentActivity activity) {
        this.activity = activity;
    }

    //-----------------------------------------------
    public void show() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_app_rating_layout, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        UtilFonts.overrideFonts(activity, dialogView, UtilFonts.IRAN_SANS_BOLD);
        edtComment = (EditText) dialogView.findViewById(R.id.edtComment);
        txtRateValue = (TextView) dialogView.findViewById(R.id.txtRateValue);
        ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                try {
                    if (rating > 0)
                        txtRateValue.setText(ratingString[Math.round(rating) - 1]);
                    else {
                        ratingBar.setRating(1.0f);
                        txtRateValue.setText(ratingString[0]);
                    }
                } catch (Exception e) {


                }

            }
        });
        Button btnSentRate = (Button) dialogView.findViewById(R.id.btnSentRate);
        Button btnDismiss = (Button) dialogView.findViewById(R.id.btnDismiss);
        btnSentRate.setOnClickListener(onClickListener);
        btnDismiss.setOnClickListener(onClickListener);
        ratingBar.setRating(ratingString.length);
        alertDialog.show();
    }

    //-----------------------------------------------
    private void sentRating() {
        new UserApi(activity).sentRating(String.valueOf(ratingBar.getRating()), edtComment.getText().toString(), new ResultSearchPresenter<BaseResult>() {
            @Override
            public void onStart() {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onErrorServer(int type) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onErrorInternetConnection() {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onSuccessResultSearch(final BaseResult result) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ToastMessageBar.show(activity, result.getMsg());
                            } catch (Exception e) {


                            }

                        }
                    });
                }
            }

            @Override
            public void noResult(int type) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            alertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onError(final String msg) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastMessageBar.show(activity, msg);
                            alertDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onFinish() {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            alertDialog.dismiss();
                        }
                    });
                }
            }
        });
    }


    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDismiss:
                    alertDialog.cancel();
                    break;
                case R.id.btnSentRate:
                    sentRating();

                    break;
            }
        }
    };
    //-----------------------------------------------
}
