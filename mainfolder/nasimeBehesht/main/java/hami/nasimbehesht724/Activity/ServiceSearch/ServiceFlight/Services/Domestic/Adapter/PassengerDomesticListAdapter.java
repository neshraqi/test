package hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

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
    private int infantCount = 0, adultsCount = 0, childCount = 0;
    private String adultPrice, childPrice, infantPrice;

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
    @Override
    public PassengerDomesticListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_domestic_info_passenger, null);
        PassengerDomesticListAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final PassengerDomesticListAdapter.MyViewHolder viewHolder, final int position) {
        final DomesticPassengerInfo domesticPassengerInfo = listItem.get(position);
        if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_ADULTS) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.adults));
            viewHolder.txtPrice.setText(getFinalPrice(adultPrice));
        } else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_CHILDREN) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.children));
            viewHolder.txtPrice.setText(getFinalPrice(childPrice));
        } else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_INFANT) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.infant));
            viewHolder.txtPrice.setText(getFinalPrice(infantPrice));
        }
        viewHolder.txtFullNameEng.setText("نام کامل: " + domesticPassengerInfo.getFirstNameEng() + " " + domesticPassengerInfo.getLastNameEng());
        if (domesticPassengerInfo.getNationalityType() == DomesticPassengerInfo.EXPORTING_COUNTRY_FOREIGN) {
            viewHolder.txtNationalCode.setText("پاسپورت:" + domesticPassengerInfo.getPassportCo());
        } else {
            viewHolder.txtNationalCode.setText("کد ملی:" + domesticPassengerInfo.getNationalCode());
        }

    }

    //-----------------------------------------------
    private String getFinalPrice(String price) {
        String finalPrice = "";
        try {

            finalPrice = price.replace(",", "");
            finalPrice = NumberFormat.getNumberInstance(Locale.US).format(Long.valueOf(finalPrice) / 10);
            finalPrice += " تومان";

            return finalPrice;
        } catch (Exception e) {

            return price + " ریال";
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

            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_WEB);
            txtPrice = (TextView) itemLayoutView.findViewById(R.id.txtPrice);
            txtFullNameEng = (TextView) itemLayoutView.findViewById(R.id.txtFullNameEng);
            txtNationalCode = (TextView) itemLayoutView.findViewById(R.id.txtNationalCode);
            imgTypePassenger = (ImageView) itemLayoutView.findViewById(R.id.imgTypePassenger);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            btnRemoveItem = (ImageView) itemLayoutView.findViewById(R.id.btnImgRemove);
            imgError = (ImageView) itemLayoutView.findViewById(R.id.imgError);
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
        if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_ADULTS)
            adultsCount++;
        else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_CHILDREN)
            childCount++;
        else if (domesticPassengerInfo.getTypePassenger() == FlightRules.TP_INFANT)
            infantCount++;
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
        if (listItem.get(position).getTypePassenger() == FlightRules.TP_ADULTS)
            adultsCount--;
        else if (listItem.get(position).getTypePassenger() == FlightRules.TP_CHILDREN)
            childCount--;
        else if (listItem.get(position).getTypePassenger() == FlightRules.TP_INFANT)
            infantCount--;
        listItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listItem.size());
    }

    //-----------------------------------------------
    public Boolean hasValidateChild() {
        if ((childCount > 0 && adultsCount > 0) || (childCount == 0 && adultsCount == 0) || (childCount == 0 && adultsCount > 0))
            return true;
        return false;
    }

    //-----------------------------------------------
    public Boolean hasValidateInfant() {
        return (adultsCount >= infantCount);
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
