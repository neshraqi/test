package hami.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hami.common.Util.UtilFonts;


public class MainActivity extends AppCompatActivity {

    //TextView tv_flight;
    //-----------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        TextView textView = findViewById(R.id.tv_flight);
        textView.setText("Test j");
        com.hami.common.Util.UtilFonts.overrideFonts(this, textView, UtilFonts.IRAN_SANS_BOLD);
    }
}
