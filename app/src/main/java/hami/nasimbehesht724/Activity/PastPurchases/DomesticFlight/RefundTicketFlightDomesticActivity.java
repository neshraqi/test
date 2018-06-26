package hami.nasimbehesht724.Activity.PastPurchases.DomesticFlight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hami.nasimbehesht724.Activity.PastPurchases.Model.PurchasesFlightDomestic;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;
import hami.nasimbehesht724.Util.UtilFragment;
import hami.nasimbehesht724.View.ToastMessageBar;

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
