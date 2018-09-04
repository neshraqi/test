package hami.mainapp.flight.Services.International.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import hami.mainapp.R;
import hami.mainapp.flight.Services.International.Controller.Model.Country;
import hami.mainapp.flight.Services.International.Controller.Presenter.InternationalApi;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renjer on 1/28/2017.
 */

public class CountryAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<Country> resultList = new ArrayList<>();

    public CountryAutoCompleteAdapter(Context context) {
        mContext = context;
    }
    //-----------------------------------------------
    @Override
    public int getCount() {
        return resultList.size();
    }
    //-----------------------------------------------
    @Override
    public Country getItem(int index) {
        return resultList.get(index);
    }
    //-----------------------------------------------
    @Override
    public long getItemId(int position) {
        return position;
    }
    //-----------------------------------------------
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //InternationalWarehouse.clearWareHouse();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_search_items_place, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.CountryEng)).setText(getItem(position).getFullName());
        ((TextView) convertView.findViewById(R.id.countryFr)).setText(getItem(position).getPersian());
        ((TextView) convertView.findViewById(R.id.txtCode)).setText(getItem(position).getCode());
        return convertView;
    }
    //-----------------------------------------------
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    final List<Country> places = new InternationalApi(mContext).searchCountry(constraint.toString()); //getListPlace(mContext,constraint.toString()); //findBooks(mContext, constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = places;
                    filterResults.count = places.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Country>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
    //-----------------------------------------------

}
