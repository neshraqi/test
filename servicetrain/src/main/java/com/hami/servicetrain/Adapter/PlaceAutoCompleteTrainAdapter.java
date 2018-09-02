package com.hami.servicetrain.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.hami.servicetrain.R;
import com.hami.servicetrain.Services.Controller.Model.CityTrain;
import com.hami.servicetrain.TrainOffline;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by renjer on 1/28/2017.
 */

public class PlaceAutoCompleteTrainAdapter extends BaseAdapter implements Filterable {

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<CityTrain> resultList = new ArrayList<>();

    public PlaceAutoCompleteTrainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public CityTrain getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_search_items_place, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.CountryEng)).setText(getItem(position).getCityPersian());
        ((TextView) convertView.findViewById(R.id.countryFr)).setText(getItem(position).getCityEng());
        ((TextView) convertView.findViewById(R.id.txtCode)).setText(getItem(position).getCityEng().substring(0,1));
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
                    final List<CityTrain> cities =  new TrainOffline(mContext).getCityByName(constraint.toString());
                    //final List<SearchInternational> places = new InternationalApi(mContext).searchPlace2(constraint.toString()); //getListPlace(mContext,constraint.toString()); //findBooks(mContext, constraint.toString());
                    // Assign the data to the FilterResults
                    filterResults.values = cities;
                    filterResults.count = cities.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<CityTrain>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};
        return filter;
    }
    //-----------------------------------------------

}
