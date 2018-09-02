package com.hami.serviceflight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.TimeDate;
import com.hami.common.Util.UtilFonts;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.International.Controller.Model.Legs;
import com.hami.serviceflight.Services.International.Controller.Model.OutBound;
import com.hami.serviceflight.Services.International.Controller.Presenter.SelectItemShowRoutingFlightInternational;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by renjer on 1/8/2017.
 */

public class RoutingInternationalListAdapter extends RecyclerView.Adapter<RoutingInternationalListAdapter.MyViewHolder> {

    private List<Legs> list_item ;
    private Context context;
    private SelectItemShowRoutingFlightInternational selectItemShowRoutingFlightInternational;
    private String fileName;
    SimpleDateFormat formatter = null;
    //-----------------------------------------------
    public RoutingInternationalListAdapter(Context context, OutBound outBound, SelectItemShowRoutingFlightInternational selectItemShowRoutingFlightInternational ) {
        //this.selectItemShowRoutingFlightInternational= selectItemShowRoutingFlightInternational;
        list_item = outBound.getLegs();
        this.context = context;
        fileName = outBound.getFileName();
        formatter  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    }
    //-----------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_international_routing_, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        final Legs legs = list_item.get(position);
        String fromDesc =""+legs.getDepartureCityPersian() +" فرودگاه  - "+ legs.getDepartureAirportPersian() + "راس ساعت "+TimeDate.getTime(null,legs.getDepartureTime()) + " در تاریخ " + TimeDate.getDate(null,legs.getDepartureTime())+
                " از فرودگاه " + legs.getDepartureAirportPersian() + " حرکت میکند";
        viewHolder.txtFromDesc.setText(fromDesc);
        String ToDesc = "راس ساعت "+ TimeDate.getTime(null,legs.getDepartureTime()) + " در تاریخ " + TimeDate.getDate(null,legs.getDepartureTime())+
                " از فرودگاه " + legs.getArrivalAirportPersian() + " حرکت میکند";
        viewHolder.txtToDesc.setText(fromDesc);
        /*
        viewHolder.txtFromYataAndTime.setText(TimeDate.getTime(null,legs.getDepartureTime())+"- "+TimeDate.getDate(null,legs.getDepartureTime()));
        viewHolder.txtFromCityAndAirportEngPlace.setText(legs.getDepartureCityName()+"-"+legs.getDepartureAirportName());
        viewHolder.txtFromCityAndAirportPerPlace.setText(legs.getDepartureCityPersian()+"-"+legs.getDepartureAirportPersian());
        //viewHolder.txtFromDate.setText(TimeDate.getDate(null,legs.getDepartureTime()));

        viewHolder.txtToYataAndTime.setText(TimeDate.getTime(null,legs.getArrivalTime())+"- "+TimeDate.getDate(null,legs.getArrivalTime()));
        viewHolder.txtToCityAndAirportEngPlace.setText(legs.getArrivalCityName()+"-"+legs.getArrivalAirportName());
        viewHolder.txtToCityAndAirportPerPlace.setText(legs.getArrivalCityPersian()+"-"+legs.getArrivalAirportPersian());
        //viewHolder.txtToDate.setText(TimeDate.getDate(null,legs.getArrivalTime()));
        */
        viewHolder.txtFlightNumber.setText("FlightNumber:"+legs.getFlightNo());

        Picasso.with(context)
                .load(BaseConfig.BASE_URL_MASTER + BaseConfig.FOLDER_IMAGE_INTERNATIONAL_URL +fileName )
                .into(viewHolder.imgLogoAirLine);
        //"http://hami.hamibelit.com/" + "assets/images/externalagent/PC.jpg";


        //viewHolder.country_name.setText(searchInternational.getAirport()+"("+searchInternational.getAirportF()+")");


    }
    //-----------------------------------------------

    //-----------------------------------------------
    public  class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFromYataAndTime;
        public TextView txtFromCityAndAirportEngPlace;
        public TextView txtFromCityAndAirportPerPlace;
        public TextView txtFromDate;

        public TextView txtToYataAndTime;
        public TextView txtToCityAndAirportEngPlace;
        public TextView txtToCityAndAirportPerPlace;
        public TextView txtToDate;


        public TextView txtTimeFlight;
        public TextView txtFlightNumber;
        private TextView txtFromDesc , txtToDesc;
        public ImageView imgLogoAirLine;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context,itemLayoutView, UtilFonts.IRAN_SANS_WEB);

            txtFromDesc = (TextView) itemLayoutView.findViewById(R.id.txtFromDesc);
            txtToDesc = (TextView) itemLayoutView.findViewById(R.id.txtToDesc);

            imgLogoAirLine = (ImageView) itemLayoutView.findViewById(R.id.imgLogoAirLine);
            txtFlightNumber = (TextView) itemLayoutView.findViewById(R.id.txtFlightNumber);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //selectItemShowRoutingFlightInternational.onSelectItemRouting(list_item.get(getLayoutPosition()));
                }
            });

        }
    }
    //-----------------------------------------------
    @Override
    public int getItemCount() {
        if(list_item!=null) {
            return list_item.size() > 0 ? list_item.size() : 0;
        }
        return 0;
    }

}