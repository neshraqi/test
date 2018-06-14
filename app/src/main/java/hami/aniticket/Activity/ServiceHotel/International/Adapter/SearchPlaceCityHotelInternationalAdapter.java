package hami.aniticket.Activity.ServiceHotel.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.SearchCity;
import hami.aniticket.Activity.ServiceHotel.International.Controller.Model.SearchDestination;
import hami.aniticket.BaseController.SelectItemList;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class SearchPlaceCityHotelInternationalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchCity> listItem;
    private Context context;
    private SelectItemList<SearchCity> selectItemSearchInternational;

    //-----------------------------------------------
    public SearchPlaceCityHotelInternationalAdapter(Context context, List<SearchCity> list, SelectItemList<SearchCity> selectItemSearchInternational) {
        this.selectItemSearchInternational = selectItemSearchInternational;
        listItem = list;
        this.context = context;
    }
//
//    //-----------------------------------------------
//    public int getItemViewType(int position) {
//        return listItem.get(position).getApiType();
//    }

    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_items_place_new_ltr, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        SearchCity searchDestination = listItem.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.bind(searchDestination);
    }

    //-----------------------------------------------
    public void setFilter(List<SearchCity> listItem) {
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

                    selectItemSearchInternational.onSelectItem(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }

        public void bind(SearchCity searchDestination) {
            txtCountry.setText(searchDestination.getName() + "(" + searchDestination.getCountryCode() + ")");
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
    private String getTitle(SearchDestination searchDestination) {
        return searchDestination.getName();
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
}