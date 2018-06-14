package hami.hamibelit.Activity.Authentication;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.hamibelit.Activity.Authentication.Controller.UserApi;
import hami.hamibelit.Activity.Authentication.Controller.UserResponse;
import hami.hamibelit.BaseController.ResultSearchPresenter;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.View.Progressbar.ButtonWithProgress;
import hami.hamibelit.View.ToastMessageBar;

public class SignUpActivity extends AppCompatActivity {

    private RelativeLayout root;
    private TextView txtForgetPassword, txtMobile, txtContent;
    private AppCompatEditText edtMobile, edtPassword;
    private TextInputLayout inputLayoutPassword, inputLayoutMobile;
    private ButtonWithProgress btnLogin;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        root = (RelativeLayout) findViewById(R.id.root);
        UtilFonts.overrideFonts(this, root, UtilFonts.IRAN_SANS_NORMAL);
        txtMobile = (TextView) findViewById(R.id.txtMobile);
        edtMobile = (AppCompatEditText) findViewById(R.id.edtMobile);
        edtPassword = (AppCompatEditText) findViewById(R.id.edtPassword);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.inputLayoutMobile);
        txtContent = (TextView) findViewById(R.id.txtContent);
        txtForgetPassword = (TextView) findViewById(R.id.txtForgetPassword);
        btnLogin = (ButtonWithProgress) findViewById(R.id.btnSignIn);
        styleSignIn();
    }

    //-----------------------------------------------
    private void styleSignIn() {
        txtForgetPassword.setVisibility(View.GONE);
        inputLayoutPassword.setVisibility(View.GONE);
        inputLayoutMobile.setVisibility(View.VISIBLE);
        txtContent.setText(R.string.aboutInputMobileNumber);
        btnLogin.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnLogin.setConfig("ارسال", "در حال ارسال", "ارسال");
    }

    //-----------------------------------------------
    private void styleAcceptCode() {

        txtForgetPassword.setVisibility(View.VISIBLE);
        txtForgetPassword.setPaintFlags(txtForgetPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtForgetPassword.setOnClickListener(onClickListener);
        inputLayoutPassword.setVisibility(View.VISIBLE);
        inputLayoutMobile.setVisibility(View.GONE);
        txtContent.setText(R.string.aboutInputPassword);
        txtMobile.setText(edtMobile.getText().toString());
        btnLogin.setCallBack(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptCode();
            }
        });
//        new CountDownTimer(60000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                txtContent.setText("مدت باقی مانده ارسال پیامک: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
//                txtContent.setText("done!");
//            }
//        }.start();
        btnLogin.setConfig("بررسی رمز", "در حال بررسی", "بررسی رمز");
    }

    //-----------------------------------------------
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnSignIn) {
                signIn();
            } else if (v.getId() == R.id.txtForgetPassword) {
                styleSignIn();
            }
        }
    };

    //-----------------------------------------------
    private void signIn() {

        if (edtMobile.length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtMobile.startAnimation(vibrateAnimation);
            return;
        }
        new UserApi(this).requestSignInByMobile(edtMobile.getText().toString(), userResponsePresenter);

    }

    //-----------------------------------------------
    private ResultSearchPresenter<Boolean> userResponsePresenter = new ResultSearchPresenter<Boolean>() {
        @Override
        public void onStart() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    root.setEnabled(false);
                    btnLogin.setEnableButton(false);
                    btnLogin.startProgress();
                }
            });
        }

        @Override
        public void onErrorServer(int type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorServer);
                }
            });
        }

        @Override
        public void onErrorInternetConnection() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorInternetConnection);
                }
            });
        }

        @Override
        public void onSuccessResultSearch(Boolean result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    styleAcceptCode();
                    //ToastMessageBar.show(SignUpActivity.this, R.string.successSendPassword);
                    //finish();
                }
            });
        }

        @Override
        public void noResult(int type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorIncorrectUsernameAndEmail);
                }
            });
        }

        @Override
        public void onError(final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastMessageBar.show(SignUpActivity.this, msg);
                }
            });
        }

        @Override
        public void onFinish() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnLogin.setEnableButton(true);
                    btnLogin.stopProgress();
                    root.setEnabled(true);
                }
            });
        }
    };

    //-----------------------------------------------
    private void acceptCode() {
        if (edtPassword.length() == 0) {
            Animation vibrateAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
            edtPassword.startAnimation(vibrateAnimation);
            return;
        }
        new UserApi(this).confirmCode(txtMobile.getText().toString(), edtPassword.getText().toString(), new ResultSearchPresenter<UserResponse>() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        root.setEnabled(false);
                        btnLogin.setEnableButton(false);
                        btnLogin.startProgress();
                    }
                });
            }

            @Override
            public void onErrorServer(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorServer);
                    }
                });
            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorInternetConnection);
                    }
                });
            }

            @Override
            public void onSuccessResultSearch(UserResponse result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SignUpActivity.this, R.string.successSendPassword);
                        finish();
                    }
                });
            }

            @Override
            public void noResult(int type) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SignUpActivity.this, R.string.msgErrorIncorrectUsernameAndEmail);
                    }
                });
            }

            @Override
            public void onError(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastMessageBar.show(SignUpActivity.this, msg);
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnLogin.setEnableButton(true);
                        btnLogin.stopProgress();
                        root.setEnabled(true);
                    }
                });
            }
        });
    }
}
