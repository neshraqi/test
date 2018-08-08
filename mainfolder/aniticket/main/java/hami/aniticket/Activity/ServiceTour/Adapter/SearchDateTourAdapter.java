package hami.mainapp.Activity.ServiceTour.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hami.mainapp.Activity.ServiceTour.Controller.Model.DateTour;
import hami.mainapp.BaseController.SelectItemList;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class SearchDateTourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DateTour> listItem;
    private Context context;
    private SelectItemList<DateTour> citySelectItemList;

    //-----------------------------------------------
    public SearchDateTourAdapter(Context context, ArrayList<DateTour> list, SelectItemList<DateTour> citySelectItemList) {
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
        DateTour item = listItem.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.txtTitle.setText(item.getJday() + " " + item.getJmonth());

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
            imgPlace.setImageResource(R.mipmap.ic_date_range);
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