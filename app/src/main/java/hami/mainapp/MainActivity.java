package hami.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hami.common.Const.ServiceID;
import hami.mainapp.flight.ActivityMainFlight;
import hami.mainapp.flight.Services.Domestic.Controller.Model.DomesticRequest;

import static com.hami.common.Const.ServiceID.INTENT_SERVICE_ID;

//import com.hami.common.Util.UtilFonts;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        initialComponentActivity();
    }

    //----------------------------------------
    private void initialComponentActivity() {
        configService();
    }

    //----------------------------------------
    private void configService() {
        Object  object  = getIntent().getExtras().get(INTENT_SERVICE_ID);
        if (object instanceof DomesticRequest) {
            DomesticRequest domesticRequest = (DomesticRequest) object;
            Intent intent = new Intent(MainActivity.this, ActivityMainFlight.class);
            intent.putExtra(DomesticRequest.class.getName(), domesticRequest);
            startActivity(intent);
        }
    }
    //----------------------------------------

}
