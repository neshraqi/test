package hami.nasimbehesht724.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;

/**
 * Created by renjer on 2018-03-08.
 */

public class TextToggleView extends RelativeLayout {

    private TextView txtToggleName;
    private ImageView imgToggle;
    private View view;

    //-----------------------------------------------
    public TextToggleView(Context context) {
        super(context);
        ini(context);
    }

    public TextToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_text_toggle_view, this);
        UtilFonts.overrideFonts(getContext(), view, UtilFonts.IRAN_SANS_NORMAL);
        imgToggle = (ImageView) findViewById(R.id.imgToggle);
        txtToggleName = (TextView) findViewById(R.id.txtToggleName);
    }

    //-----------------------------------------------
    public void setConfig(String toggleName, Boolean exist) {
        txtToggleName.setText(toggleName);
        if (exist)
            imgToggle.setImageResource(R.mipmap.ic_check);
        else
            imgToggle.setImageResource(R.mipmap.ic_close);
    }
}
