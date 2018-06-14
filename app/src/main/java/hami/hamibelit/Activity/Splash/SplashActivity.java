package hami.hamibelit.Activity.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import hami.hamibelit.BaseController.AccessApi;
import hami.hamibelit.BaseController.AccessStatusPresenter;
import hami.hamibelit.BaseController.AccessStatusResponse;
import hami.hamibelit.MainActivityMaterial;
import hami.hamibelit.R;

public class SplashActivity extends AppCompatActivity {

    private AccessApi accessApi;
    private ProgressBar progressBar;
    private TextView txtTitle;
    private Button btnTryAgain;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_layout);
        initialComponentActivity();

    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        accessApi = new AccessApi(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAccess();
            }
        });
        btnTryAgain.setVisibility(View.GONE);
        getAccess();
    }

    //-----------------------------------------------
    public void getAccess() {
        accessApi.getAccessStatus(new AccessStatusPresenter() {
            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        txtTitle.setVisibility(View.VISIBLE);
                        txtTitle.setText(R.string.authenticating);
                        btnTryAgain.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onErrorServer() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setVisibility(View.VISIBLE);
                        txtTitle.setText(R.string.msgErrorServer);
                        btnTryAgain.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onErrorInternetConnection() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setVisibility(View.VISIBLE);
                        txtTitle.setText(R.string.msgErrorInternetConnection);
                        btnTryAgain.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onSuccessGetAccessStatus(final AccessStatusResponse accessStatusResponse) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        endIntroduction(accessStatusResponse);
                    }
                });
            }

            @Override
            public void onError(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtTitle.setVisibility(View.VISIBLE);
                        txtTitle.setText(R.string.msgErrorServer);
                        btnTryAgain.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    //-----------------------------------------------
    private void endIntroduction(AccessStatusResponse accessStatusResponse) {
        Intent intent = new Intent(this, MainActivityMaterial.class);
        intent.putExtra(AccessStatusResponse.class.getName(), accessStatusResponse);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

}
