package hami.mainapp.Activity.ServiceSearch.ServiceFlight.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.mainapp.BaseController.SelectItemList;
import hami.mainapp.R;
import hami.mainapp.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class SearchPlaceFlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchInternational> listItem;
    private Context context;
    private SelectItemList<SearchInternational> selectItemSearchInternational;

    //-----------------------------------------------
    public SearchPlaceFlightAdapter(Context context, List<SearchInternational> list, SelectItemList<SearchInternational> selectItemSearchInternational) {
        this.selectItemSearchInternational = selectItemSearchInternational;
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
        if (viewType == SearchInternational.HEADER) {
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
        SearchInternational searchInternational = listItem.get(position);
        if (searchInternational.getType() == SearchInternational.CITY) {
            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.bind(searchInternational);
        } else {
            MyViewHolderHeader myViewHolder = (MyViewHolderHeader) viewHolder;
            myViewHolder.txtHeader.setText(searchInternational.getData());
        }


    }

    //-----------------------------------------------
    public void setFilter(List<SearchInternational> listItem) {
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

        public void bind(SearchInternational international) {
            txtCountry.setText(getTitle(international));
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
    private String getTitle(SearchInternational searchInternational) {
        String title = "";
        if (searchInternational.getAirportF() == null || searchInternational.getAirportF().length() == 0)
            title = searchInternational.getDataF();
        else
            title = searchInternational.getDataF() + " , " + searchInternational.getAirportF();
        return title;
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

    //-----------------------------------------------
}