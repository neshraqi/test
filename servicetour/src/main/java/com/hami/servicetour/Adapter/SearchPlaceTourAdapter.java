package com.hami.servicetour.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import com.hami.servicetour.Controller.Model.NameValue;
import com.hami.servicetour.R;

import java.util.ArrayList;


/**
 * Created by renjer on 1/8/2017.
 */

public class SearchPlaceTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<NameValue> listItem;
    private Context context;
    private SelectItemList<NameValue> citySelectItemList;

    //-----------------------------------------------
    public SearchPlaceTourAdapter(Context context, ArrayList<NameValue> list, SelectItemList<NameValue> citySelectItemList) {
        this.citySelectItemList = citySelectItemList;
        listItem = list;
        this.context = context;
    }


    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_items_place_new, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        NameValue item = listItem.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.txtTitle.setText(item.getValue());
    }


    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTitle;
        public ImageView imgPlace;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtTitle = (TextView) itemLayoutView.findViewById(R.id.txtCountry);
            imgPlace = (ImageView) itemLayoutView.findViewById(R.id.imgPlace);
            imgPlace.setImageResource(R.mipmap.ic_place_object);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    citySelectItemList.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
}