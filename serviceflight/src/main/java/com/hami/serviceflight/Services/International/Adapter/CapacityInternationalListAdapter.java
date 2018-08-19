package com.hami.serviceflight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hami.common.Util.UtilFonts;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.International.Controller.Model.BaggageInfo;

import java.util.List;


/**
 * Created by renjer on 1/8/2017.
 */

public class CapacityInternationalListAdapter extends RecyclerView.Adapter<CapacityInternationalListAdapter.MyViewHolder> {

    private List<BaggageInfo> list_item ;
    private Context context;
    //-----------------------------------------------
    public CapacityInternationalListAdapter(Context context, List<BaggageInfo> item) {
        this.list_item = item;
        this.context = context;
    }
    //-----------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_capacity_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {
        BaggageInfo baggageInfoes = list_item.get(position);
        viewHolder.txtFromPlace.setText(baggageInfoes.getDeparture());
        viewHolder.txtToPlace.setText(baggageInfoes.getArrival());
        viewHolder.txtFlightNumber.setText(baggageInfoes.getFlightNo());
        viewHolder.txtCapacity.setText(baggageInfoes.getBaggage());
    }
    //-----------------------------------------------
    public  class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFromPlace;
        public TextView txtToPlace;
        public TextView txtFlightNumber;
        public TextView txtCapacity;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context,itemLayoutView,"iran_sans_web.ttf");
            txtFromPlace = (TextView) itemLayoutView.findViewById(R.id.txtFromPlace);
            txtToPlace = (TextView) itemLayoutView.findViewById(R.id.txtToPlace);
            txtFlightNumber = (TextView) itemLayoutView.findViewById(R.id.txtFlightNumber);
            txtCapacity = (TextView) itemLayoutView.findViewById(R.id.txtCapacity);
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