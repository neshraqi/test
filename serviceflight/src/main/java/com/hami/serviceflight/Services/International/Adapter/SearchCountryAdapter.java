package com.hami.serviceflight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import com.hami.serviceflight.R;
import com.hami.serviceflight.Services.International.Controller.Model.Country;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renjer on 1/8/2017.
 */

public class SearchCountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Country> listItem;
    private Context context;
    private SelectItemList<Country> selectItemListCountry;

    //-----------------------------------------------
    public SearchCountryAdapter(Context context, ArrayList<Country> listItem, SelectItemList<Country> selectItemListCountry) {
        this.selectItemListCountry = selectItemListCountry;
        this.listItem = listItem;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_items_place_new, null);
        MyViewHolder myViewHolderHeader = new MyViewHolder(view);
        return myViewHolderHeader;

    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Country country = listItem.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.bind(country);
    }

    //-----------------------------------------------
    public void setFilter(List<Country> listItem) {
        this.listItem = new ArrayList<>();
        this.listItem.addAll(listItem);
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtCountry;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_WEB);
            txtCountry = (TextView) itemLayoutView.findViewById(R.id.txtCountry);
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItemListCountry.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }

        public void bind(Country country) {
            txtCountry.setText(country.getFullName() + "," + country.getPersian());
        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
}