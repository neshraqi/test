package hami.aniticket.Activity.Authentication;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.aniticket.Activity.Authentication.Controller.UserApi;
import hami.aniticket.BaseController.ResultSearchPresenter;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.View.ToastMessageBar;
import io.supercharge.shimmerlayout.ShimmerLayout;



public class ForgetPasswordActivity extends AppCompatActivity {

    private ShimmerLayout shimmerLayout;
    private RelativeLayout layoutMain;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        UtilFonts.overrideFonts(this, layoutMain, UtilFonts.IRAN_SANS_NORMAL);
        shimmerLayout = (ShimmerLayout) findViewById(R.id.shimmer_layout);
        shimmerLayout.setVisibility(View.GONE);
        TextView txtBackToLogin = (TextView) findViewById(R.id.txtBackToLogin);
        txtBackToLogin.setPaintFlags(txtBackToLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Button btnSendPassword = (Button) findViewById(R.id.btnSendPassword);
        txtBackToLogin.setOnClickListener(onClickListener);
        btnSendPassword.setOnClickListener(onClickListener);
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSendPassword) {
                forgetPassword();
            } else if (v.getId() == R.id.txtBackToLogin) {
                finish();
            }
        }
    };

    //-----------------------------------------------
    private void forgetPassword() {
        AppCompatEditText edtMobileOrEmail = (AppCompatEditText) findViewById(R.id.edtMobileOrEmail);
        if (edtMobileOrEmail.length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtMobileOrEmail.startAnimation(vibrateAnimation);
            return;
        }
        if (isValidMobile(edtMobileOrEmail.getText().toString())) {
            new UserApi(this).forgetPasswordByMobile(edtMobileOrEmail.getText().toString(), userResponseResultSearchPresenter);
        } else if (isValidMail(edtMobileOrEmail.getText().toString())) {
            new UserApi(this).forgetPasswordByEmail(edtMobileOrEmail.getText().toString(), userResponseResultSearchPresenter);
        } else {
            ToastMessageBar.show(this, R.string.msgErrorEmailOrMobile);
        }

    }

    //-----------------------------------------------
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    //-----------------------------------------------
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //-----------------------------------------------
    ResultSearchPresenter<Boolean> userResponseResultSearchPresenter = new ResultSearchPresenter<Boolean>() {
        @Override
        public void onStart() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layoutMain.setVisibility(View.GONE);
                    shimmerLayout.setVisibility(View.VISIBLE);
                    shimmerLayout.startShimmerAnimation();
                }
            });
        }

        @Override
        public void onErrorServer(int type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(ForgetPasswordActivity.this, R.string.msgErrorServer);
                }
            });
        }

        @Override
        public void onErrorInternetConnection() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(ForgetPasswordActivity.this, R.string.msgErrorInternetConnection);
                }
            });
        }

        @Override
        public void onSuccessResultSearch(Boolean result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(ForgetPasswordActivity.this, R.string.successSendPassword);
                    finish();
                }
            });
        }

        @Override
        public void noResult(int type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(ForgetPasswordActivity.this, R.string.msgErrorIncorrectUsernameAndEmail);
                }
            });
        }

        @Override
        public void onError(final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(ForgetPasswordActivity.this, msg);
                }
            });
        }

        @Override
        public void onFinish() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    layoutMain.setVisibility(View.VISIBLE);
                    shimmerLayout.setVisibility(View.GONE);
                }
            });
        }
    };
    //-----------------------------------------------

}
