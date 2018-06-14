package hami.aniticket.Activity.ServiceSearch.ServiceTrain;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainRequest;
import hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Fragment.FragmentFinalBookingTrain;
import hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Fragment.FragmentListWentTrain;
import hami.aniticket.Const.KeyConst;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;
import hami.aniticket.Util.UtilFragment;
import hami.aniticket.View.DialogExpire;


public class ActivityMainTrain extends AppCompatActivity {


    private TrainRequest trainRequest;

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        try {
            trainRequest = (TrainRequest) getIntent().getExtras().getSerializable(TrainRequest.class.getName());
        } catch (Exception e) {

        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            trainRequest = (TrainRequest) savedInstanceState.getSerializable(TrainRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TrainRequest.class.getName(), trainRequest);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMainContent), UtilFonts.IRAN_SANS_NORMAL);
        setupToolbar();
        timer();
        UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentTrain.newInstance(trainRequest));
    }

    //-----------------------------------------------

    @Override
    public void onBackPressed() {
        int index = getSupportFragmentManager().getFragments().size() - 1;
        if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentFinalBookingTrain &&
                ((FragmentFinalBookingTrain) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //-----------------------------------------------
    private void setupToolbar() {
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitleMenu);
        txtTitle.setText(R.string.train);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    //-----------------------------------------------
    private void timer() {
        new CountDownTimer(KeyConst.TIME_EXPIRE, KeyConst.TIME_STEP) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                showExpireTime();
            }
        }.start();
    }

    private void showExpireTime() {
        final DialogExpire dialogExpire = new DialogExpire();
        dialogExpire.show(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = getSupportFragmentManager().getBackStackEntryCount();
                while (counter >= 1) {
                    getSupportFragmentManager().popBackStackImmediate();
                    counter = getSupportFragmentManager().getBackStackEntryCount();
                }
                dialogExpire.dismiss();
                timer();
                UtilFragment.changeFragment(getSupportFragmentManager(), FragmentListWentTrain.newInstance(trainRequest));
            }
        });
    }
}
