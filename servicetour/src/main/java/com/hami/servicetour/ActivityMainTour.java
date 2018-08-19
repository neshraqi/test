package com.hami.servicetour;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.Util.UtilFonts;
import com.hami.common.Util.UtilFragment;
import com.hami.servicetour.Controller.Model.SearchTourRequest;
import com.hami.servicetour.Fragment.FragmentTourFinalBooking;
import com.hami.servicetour.Fragment.FragmentTourList;


public class ActivityMainTour extends AppCompatActivity {
    private SearchTourRequest searchTourRequest;

    //-----------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicetour_main);
        try {
            searchTourRequest = getIntent().getExtras().getParcelable(SearchTourRequest.class.getName());
        } catch (Exception e) {
        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            searchTourRequest = savedInstanceState.getParcelable(SearchTourRequest.class.getName());
        }
    }

    //-----------------------------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null)
            outState.putParcelable(SearchTourRequest.class.getName(), searchTourRequest);
        super.onSaveInstanceState(outState);
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMainContent), UtilFonts.IRAN_SANS_NORMAL);
        setupToolbar();
        UtilFragment.changeFragment(getSupportFragmentManager(), FragmentTourList.newInstance(searchTourRequest), R.id.frame_Layout);
    }
    //-----------------------------------------------

    @Override
    public void onBackPressed() {
        try {
            int index = getSupportFragmentManager().getFragments().size() - 1;
            if (getSupportFragmentManager().getFragments().get(index) instanceof FragmentTourFinalBooking &&
                    ((FragmentTourFinalBooking) getSupportFragmentManager().getFragments().get(index)).hasBuyTicket()) {
                finish();
            } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        } catch (Exception e) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                finish();
            }
        }
    }


    //-----------------------------------------------
    private void setupToolbar() {
        ImageView btnBack = (ImageView) findViewById(R.id.btnBack);
        TextView txtTitle = (TextView) findViewById(R.id.txtTitleMenu);
        txtTitle.setText(R.string.tour);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    //-----------------------------------------------

}
