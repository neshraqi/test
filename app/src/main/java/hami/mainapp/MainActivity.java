package hami.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private ImageView ivToor, ivHotel, ivTrain, ivPlane, ivBus;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    // private AccessStatusResponse accessStatusResponse;
    private TextView tv_train, tv_flight, tv_bus,tv_tour;
    private FrameLayout frame_Layout;


    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        tv_train = (TextView) findViewById(R.id.tv_train);
        tv_flight = (TextView) findViewById(R.id.tv_flight);
        tv_bus = (TextView) findViewById(R.id.tv_bus);
        tv_tour = (TextView) findViewById(R.id.tv_tour);
        txtTitleMenu = (TextView) findViewById(R.id.txtTitleMenu);
        frame_Layout = (FrameLayout) findViewById(R.id.frame_Layout);

        tv_flight.setOnClickListener(this);
        tv_train.setOnClickListener(this);
        tv_bus.setOnClickListener(this);
        tv_tour.setOnClickListener(this);


//        tv_train.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                try {
//
//                    Intent myIntent = new Intent(MainActivity.this, Class.forName("com.hami.servicetrain.ActivityMainTrain"));
//                    startActivity(myIntent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_train:

                try {
                    Intent myIntent = new Intent(MainActivity.this, Class.forName("com.hami.servicetrain.ActivityMainTrain"));
                    startActivity(myIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_flight:

                try {
                    Intent myIntent = new Intent(MainActivity.this, Class.forName("com.hami.serviceflight.ActivityMainFlight"));
                    startActivity(myIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_bus:

                try {
                    Intent myIntent = new Intent(MainActivity.this, Class.forName("com.hami.servicebus.ActivityMainBus"));
                    startActivity(myIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.tv_tour:

                try {
                    Intent myIntent = new Intent(MainActivity.this, Class.forName("com.hami.servicetour.ActivityMainTour"));
                    startActivity(myIntent);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
    //---------