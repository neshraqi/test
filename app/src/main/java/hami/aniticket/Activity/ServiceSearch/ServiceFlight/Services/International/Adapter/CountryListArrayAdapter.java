package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.aniticket.R;

/**
 * Created by renjer on 2017-05-09.
 */

public class CountryListArrayAdapter extends ArrayAdapter<Country> {

    private final List<Country> list;
    private final Activity context;
    static class ViewHolder {
        protected TextView txtCountry;
        //protected ImageView flag;
    }

    public CountryListArrayAdapter(Activity context, List<Country> list) {
        super(context, R.layout.row_search_items_place, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.row_search_items_place, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtCountry = (TextView) view.findViewById(R.id.txtCountry);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.txtCountry.setText(list.get(position).getFullName()+"("+list.get(position).getPersian()+")");
        return view;
    }
}