package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Adapter.PassengerCounterFlight;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.DomesticPassengerInfo;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.OnSelectItemPassengerDomesticListener;
import hami.nasimbehesht724.Const.FlightRules;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;


public class PassengerDomesticListAdapter extends RecyclerView.Adapter<PassengerDomesticListAdapter.MyViewHolder> {

    private static final String TAG = "PassengerDomesticListAdapter";
    private ArrayList<DomesticPassengerInfo> listItem;
    private Context context;
    private OnSelectItemPassengerDomesticListener onSelectItemPassengerDomesticListener;
    private Typeface typeface;
    private String adultPrice, childPrice, infantPrice;
    private int international;

    //-----------------------------------------------
    public PassengerDomesticListAdapter(Context context, OnSelectItemPassengerDomesticListener onSelectItemPassengerDomesticListener) {
        this.onSelectItemPassengerDomesticListener = onSelectItemPassengerDomesticListener;
        listItem = new ArrayList<>();
        this.context = context;
    }

    //-----------------------------------------------
    public void setConfigPrice(String adultPrice, String childPrice, String infantPrice) {
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
    }

    //-----------------------------------------------
    public void setConfigInternational(int international) {
        this.international = international;
    }

    //-----------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_info_passenger, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        final DomesticPassengerInfo domesticPassengerInfo = listItem.get(position);
        if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_ADULTS) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.adults));
            //viewHolder.txtPrice.setText(getFinalPrice(adultPrice));
        } else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_CHILDREN) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.children));
            //viewHolder.txtPrice.setText(getFinalPrice(childPrice));
        } else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_INFANT) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.infant));
            //viewHolder.txtPrice.setText(getFinalPrice(infantPrice));
        }
        viewHolder.txtFullNameEng.setText("نام کامل: " + domesticPassengerInfo.getFirstNameEng() + " " + domesticPassengerInfo.getLastNameEng());
        if ((international == 1 && domesticPassengerInfo.getNationalityType() == DomesticPassengerInfo.EXPORTING_COUNTRY_IRAN) ||
                domesticPassengerInfo.getNationalityType() == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
            viewHolder.txtNationalCode.setText("پاسپورت:" + domesticPassengerInfo.getPassportCo());
        } else {
            viewHolder.txtNationalCode.setText("کد ملی:" + domesticPassengerInfo.getNationalCode());
        }

    }

    //-----------------------------------------------

    public ArrayList<DomesticPassengerInfo> getListItem() {
        return listItem;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtPrice;
        public TextView txtFullName;
        public TextView txtFullNameEng;
        public ImageView imgTypePassenger;
        public TextView txtTypePassenger;
        public TextView txtNationalCode;
        public ImageView btnRemoveItem;
        public ImageView imgError;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);

            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtPrice = itemLayoutView.findViewById(R.id.txtPrice);
            txtFullNameEng = itemLayoutView.findViewById(R.id.txtFullNameEng);
            txtNationalCode = itemLayoutView.findViewById(R.id.txtNationalCode);
            imgTypePassenger = itemLayoutView.findViewById(R.id.imgTypePassenger);
            txtTypePassenger = itemLayoutView.findViewById(R.id.txtTypePassenger);
            btnRemoveItem = itemLayoutView.findViewById(R.id.btnImgRemove);
            imgError = itemLayoutView.findViewById(R.id.imgError);
            txtPrice.setVisibility(View.GONE);
            UtilFonts.overrideFonts(context, txtTypePassenger, UtilFonts.IRAN_SANS_BOLD);

            imgError.setVisibility(View.GONE);
            btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerDomesticListener.onRemoveItemFlightDomestic(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerDomesticListener.onSelectItemFlightDomestic(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }

    //-----------------------------------------------
    public void addNewPassenger(DomesticPassengerInfo domesticPassengerInfo) {
        listItem.add(domesticPassengerInfo);
        notifyItemInserted(listItem.size() - 1);
    }

    //-----------------------------------------------
    public void editPassenger(DomesticPassengerInfo domesticPassengerInfo, int position) {
        listItem.set(position, domesticPassengerInfo);
        notifyItemChanged(position);
        notifyItemRangeChanged(0, listItem.size() - 1);
    }

    //-----------------------------------------------
    public void removePassenger(int position) {
        listItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listItem.size());
    }

    //-----------------------------------------------
    private PassengerCounterFlight getCountPassengerType() {

        int counterAdult = 0;
        int counterChild = 0;
        int counterInfant = 0;
        for (int index = 0; index < listItem.size(); index++) {
            if (listItem.get(index).getTypePassenger() == FlightRules.TP_ADULTS)
                counterAdult++;
            else if (listItem.get(index).getTypePassenger() == FlightRules.TP_CHILDREN)
                counterChild++;
            else if (listItem.get(index).getTypePassenger() == FlightRules.TP_INFANT)
                counterInfant++;
        }
        PassengerCounterFlight passengerCounterFlight = new PassengerCounterFlight(counterAdult, counterChild, counterInfant);
        return passengerCounterFlight;
    }


    //-----------------------------------------------
    public Boolean hasValidateChild() {
        PassengerCounterFlight passengerCounterFlight = getCountPassengerType();
        return passengerCounterFlight.getAdultCount() >= 1 && passengerCounterFlight.getChildCount() >= 0;
    }

    //-----------------------------------------------
    public Boolean hasValidateInfant() {
        PassengerCounterFlight passengerCounterFlight = getCountPassengerType();
        return (passengerCounterFlight.getAdultCount() >= passengerCounterFlight.getInfantCount());
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
