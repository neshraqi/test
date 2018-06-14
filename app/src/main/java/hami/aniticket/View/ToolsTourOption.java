package hami.aniticket.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;

/**
 * Created by renjer on 1/11/2017.
 */

public class ToolsTourOption extends LinearLayout {

    private TextView txtAdultPrice, txtInfantPrice, txtChildPrice, txtSingleAdultPrice;

    //-----------------------------------------------
    public ToolsTourOption(Context context) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public ToolsTourOption(Context context, String adults, String child, String infant) {
        super(context);
        ini(context);
    }

    //-----------------------------------------------
    public ToolsTourOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tools_tour_passenger, this);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        txtAdultPrice = (TextView) findViewById(R.id.txtAdultPrice);
        txtChildPrice = (TextView) findViewById(R.id.txtChildPrice);
        txtInfantPrice = (TextView) findViewById(R.id.txtInfantPrice);
        txtSingleAdultPrice = (TextView) findViewById(R.id.txtSingleAdultPrice);
    }

    //-----------------------------------------------
    public void setPassengerPrice(String adults, String child, String infant, String single) {
        txtAdultPrice.setText(adults);
        txtChildPrice.setText(child);
        txtInfantPrice.setText(infant);
        txtSingleAdultPrice.setText(single);
    }


}
