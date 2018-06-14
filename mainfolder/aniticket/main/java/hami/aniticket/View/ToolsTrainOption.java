package hami.hamibelit.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import hami.hamibelit.Const.TrainRules;
import hami.hamibelit.R;

public class ToolsTrainOption extends LinearLayout {

    private int countPassenger = 1, typePassenger = TrainRules.TP_NORMAL;
    private Boolean hasFullCope = false;
    private CheckBox chkFullCope;
    private EditText edtCountPassenger, edtTypePassenger;
    private Button btnAccept;
    //-----------------------------------------------
    public ToolsTrainOption(Context context, int countPassenger, int typePassenger, Boolean hasFullCope) {
        super(context);
        this.countPassenger = countPassenger;
        this.typePassenger = typePassenger;
        this.hasFullCope = hasFullCope;
        ini(context);
    }

    //-----------------------------------------------
    public ToolsTrainOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        ini(context);
    }

    //-----------------------------------------------
    private void ini(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_tools_service_train_search, this);
        //-----------------------------------------------
        btnAccept = (Button) findViewById(R.id.btnAccept);
        chkFullCope = (CheckBox) findViewById(R.id.chkFullCope);
        chkFullCope.setChecked(hasFullCope);
        edtCountPassenger = (EditText) findViewById(R.id.edtCountPassenger);
        edtCountPassenger.setFocusable(false);
        edtCountPassenger.setCursorVisible(false);
        edtCountPassenger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuCountPassenger();
            }
        });
        edtTypePassenger = (EditText) findViewById(R.id.edtTypePassenger);
        edtTypePassenger.setFocusable(false);
        edtTypePassenger.setCursorVisible(false);
        edtTypePassenger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenuTypePassenger();
            }
        });
        switch (typePassenger) {
            case 0:
                edtTypePassenger.setText(R.string.normalPassenger);
                break;
            case 1:
                edtTypePassenger.setText(R.string.manPassenger);
                break;
            case 2:
                edtTypePassenger.setText(R.string.womanPassenger);
                break;
            default:
                edtTypePassenger.setText(R.string.normalPassenger);
                break;
        }
        edtCountPassenger.setText(String.valueOf(countPassenger));
    }
    //-----------------------------------------------
    public void setCallbackAcceptButton(OnClickListener callbackAcceptButton) {
        btnAccept.setOnClickListener(callbackAcceptButton);
    }
    //-----------------------------------------------
    private void showPopupMenuTypePassenger() {
        PopupMenu popupMenu = new PopupMenu(getContext(), edtTypePassenger);
        popupMenu.inflate(R.menu.menu_type_search_passenger);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                edtTypePassenger.setText(item.getTitle());
                if (item.getItemId() == R.id.menuNormalPassenger)
                    typePassenger = TrainRules.TP_NORMAL;
                else if (item.getItemId() == R.id.menuManPassenger)
                    typePassenger = TrainRules.TP_MEN;
                else
                    typePassenger = TrainRules.TP_WOMEN;
                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    private void showPopupMenuCountPassenger() {
        PopupMenu popupMenu = new PopupMenu(getContext(), edtCountPassenger);
        popupMenu.inflate(R.menu.menu_count_passenger_train);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                edtCountPassenger.setText(item.getTitle());
                if (item.getItemId() == R.id.menu1)
                    countPassenger = 1;
                else if (item.getItemId() == R.id.menu2)
                    countPassenger = 2;
                else if (item.getItemId() == R.id.menu3)
                    countPassenger = 3;
                else if (item.getItemId() == R.id.menu4)
                    countPassenger = 4;
                else if (item.getItemId() == R.id.menu5)
                    countPassenger = 5;
                else if (item.getItemId() == R.id.menu6)
                    countPassenger = 6;
                return false;
            }
        });
        popupMenu.show();
    }

    //-----------------------------------------------
    public String getCountPassenger() {
        return String.valueOf(countPassenger);
    }

    //-----------------------------------------------
    public Boolean getHasFullCope() {
        hasFullCope = chkFullCope.isChecked();
        return hasFullCope;
    }

    //-----------------------------------------------
    public String getTypePassenger() {
        switch (typePassenger) {
            case 0:
                return String.valueOf(TrainRules.TP_NORMAL);
            case 1:
                return String.valueOf(TrainRules.TP_MEN);
            case 2:
                return String.valueOf(TrainRules.TP_WOMEN);
            default:
                return String.valueOf(TrainRules.TP_NORMAL);
        }
    }
    //-----------------------------------------------
    public int getStringTypePassenger() {
        switch (typePassenger) {
            case 0:
                return R.string.normalPassenger;
            case 1:
                return R.string.manPassenger;
            case 2:
                return R.string.womanPassenger;
            default:
                return R.string.normalPassenger;
        }
    }
}
