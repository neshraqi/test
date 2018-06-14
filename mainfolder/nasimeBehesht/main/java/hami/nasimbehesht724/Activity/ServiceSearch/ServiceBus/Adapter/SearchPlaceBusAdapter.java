package hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.nasimbehesht724.BaseController.SelectItemList;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class SearchPlaceBusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<City> listItem;
    private Context context;
    private SelectItemList<City> citySelectItemList;

    //-----------------------------------------------
    public SearchPlaceBusAdapter(Context context, List<City> list, SelectItemList<City> citySelectItemList) {
        this.citySelectItemList = citySelectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    public int getItemViewType(int position) {
        return listItem.get(position).getType();
    }

    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == City.HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_items_place_header, null);
            MyViewHolderHeader myViewHolderHeader = new MyViewHolderHeader(view);
            return myViewHolderHeader;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_items_place_new, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        City city = listItem.get(position);
        if (city.getType() == City.CITY) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.bind(city);
        } else {
            MyViewHolderHeader myViewHolder = (MyViewHolderHeader) viewHolder;
            myViewHolder.txtHeader.setText(city.getCityName());

        }

    }

    //-----------------------------------------------
    public void setFilter(List<City> listItem) {
        this.listItem = new ArrayList<>();
        this.listItem.addAll(listItem);
        notifyDataSetChanged();
        //notifyDataSetChanged();
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
                    citySelectItemList.onSelectItem(listItem.get(getAdapterPosition()),getAdapterPosition());
                }
            });


        }

        public void bind(City city) {
            txtCountry.setText(getTitle(city));
        }
    }

    //-----------------------------------------------
    public class MyViewHolderHeader extends RecyclerView.ViewHolder {

        public TextView txtHeader;

        public MyViewHolderHeader(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_BOLD);
            txtHeader = (TextView) itemLayoutView.findViewById(R.id.txtHeader);
        }
    }

    //-----------------------------------------------
    private String getTitle(City city) {
        String title = "";
        title = city.getCityName();
        return title;
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
}