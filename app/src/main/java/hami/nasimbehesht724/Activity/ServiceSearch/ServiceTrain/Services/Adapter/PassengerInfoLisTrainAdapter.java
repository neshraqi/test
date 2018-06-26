package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.ParamTrain;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.RegisterTrainResponse;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class PassengerInfoLisTrainAdapter extends RecyclerView.Adapter<PassengerInfoLisTrainAdapter.MyViewHolder> {

    private Context context;
    private RegisterTrainResponse registerTrainResponse;

    //-----------------------------------------------
    public PassengerInfoLisTrainAdapter(Context context, RegisterTrainResponse registerTrainResponse) {
        this.registerTrainResponse = registerTrainResponse;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PassengerInfoLisTrainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_passnger_pre_reserve, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        ParamTrain paramTrain = registerTrainResponse.getViewParamsTrain().getParams();
        viewHolder.txtFullName.setText(paramTrain.getNamep()[position] + " " + paramTrain.getFamilyp()[position]);
        viewHolder.txtTypePassenger.setText(context.getString(paramTrain.getPassengerTypeReSource(position)));
        if (paramTrain.getMelicode() != null && paramTrain.getMelicode()[position].length() > 1)
            viewHolder.txtCoNational.setText("کد ملی:" + paramTrain.getMelicode()[position]);
        else
            viewHolder.txtCoNational.setText("شماره پاسپورت:" + paramTrain.getPassport_number()[position]);
        String passport = "";
        /*
        if (paramTrain.getPassport_number()[position] == null || paramTrain.getPassport_number()[position].length() > 1)
            viewHolder.txtCoPassport.setText("شماره پاسپورت:" + paramTrain.getPassport_number()[position]);
        else
            viewHolder.txtCoPassport.setText("شماره پاسپورت:--------");*/
        //viewHolder.txtBirthDay.setText("تاریخ تولد:" + paramTrain.getBirthday()[position]);
        //price = NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(price));
        viewHolder.txtPrice.setText(getFinalPrice(paramTrain.getPriceByPassenger(position)));
    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {
            finalPrice = price;
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {
            return price + " ریال";
        }

    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFullName;
        public TextView txtTypePassenger;
        public TextView txtCoNational;
        public TextView txtBirthDay;
        public TextView txtPrice;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullname);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            txtCoNational = (TextView) itemLayoutView.findViewById(R.id.txtCoNational);
            txtBirthDay = (TextView) itemLayoutView.findViewById(R.id.txtBirthDay);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        if (registerTrainResponse.getViewParamsTrain().getParams().getNumberp() != null) {
            return Integer.valueOf(registerTrainResponse.getViewParamsTrain().getParams().getNumberp()) > 0 ? Integer.valueOf(registerTrainResponse.getViewParamsTrain().getParams().getNumberp()) : 0;
        }
        return 0;
    }

}