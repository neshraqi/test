package hami.hamibelit.Activity.PastPurchases.DomesticFlight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hami.hamibelit.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.Util.UtilFragment;
import hami.hamibelit.View.ToastMessageBar;

public class RefundTicketFlightDomesticActivity extends AppCompatActivity {

    private TextView txtTitleMenu;
    private TextView txtSubTitleMenu;
    private PurchasesFlightDomestic registerFlightResponse;

    //-----------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_pre_refound);
        if (getIntent().hasExtra(PurchasesFlightDomestic.class.getName())) {
            registerFlightResponse = (PurchasesFlightDomestic) getIntent().getSerializableExtra(PurchasesFlightDomestic.class.getName());
        } else {
            finish();
            ToastMessageBar.show(this, R.string.msgErrorRefund);
        }
        initialComponentActivity();
    }

    //-----------------------------------------------
    private void initialComponentActivity() {
        UtilFonts.overrideFonts(this, findViewById(R.id.layoutMain), UtilFonts.IRAN_SANS_NORMAL);
        if (registerFlightResponse.getRefund() == 0)
            UtilFragment.addNewFragment(getSupportFragmentManager(), RefundTicketFlightDomesticFragment.newInstance(registerFlightResponse));
        else
            UtilFragment.addNewFragment(getSupportFragmentManager(), RefundMoneyFlightDomesticFragment.newInstance(registerFlightResponse));
    }

    //-----------------------------------------------
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
    //-----------------------------------------------

}
