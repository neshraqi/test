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


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private ImageView ivToor, ivHotel, ivTrain, ivPlane, ivBus;
    boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = MainActivity.class.getSimpleName();
    // private AccessStatusResponse accessStatusResponse;
    private TextView tv_ok;
    private FrameLayout frame_Layout;


    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        tv_ok = (TextView)findViewById(R.id.tv_ok) ;
        txtTitleMenu = (TextView)findViewById(R.id.txtTitleMenu) ;
        frame_Layout = (FrameLayout)findViewById(R.id.frame_Layout) ;

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"ggdgd",Toast.LENGTH_SHORT);
                try {

                    Intent myIntent = new Intent(MainActivity.this,Class.forName("com.hami.servicetrain.ActivityMainTrain"));
                    startActivity(myIntent );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
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


    //-----------------------------------------------
    @Override
    protected void onResume() {
        super.onResume();

    }
//ActivityMainTrain

}
