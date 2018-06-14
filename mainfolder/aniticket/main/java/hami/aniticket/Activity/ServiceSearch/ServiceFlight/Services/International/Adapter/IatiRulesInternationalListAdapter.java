package hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hami.hamibelit.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesIati;
import hami.hamibelit.R;
import hami.hamibelit.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class IatiRulesInternationalListAdapter extends RecyclerView.Adapter<IatiRulesInternationalListAdapter.MyViewHolder> {

    private ArrayList<RulesIati> list_item;
    private Context context;

    //-----------------------------------------------
    public IatiRulesInternationalListAdapter(Context context, ArrayList<RulesIati> item) {
        this.list_item = item;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public IatiRulesInternationalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_international_rules_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        RulesIati rulesIati = list_item.get(position);
        viewHolder.txtName.setText(rulesIati.getName());
        viewHolder.txtDesc.setText(getRulesString(rulesIati.getRules()));
    }

    //-----------------------------------------------
    private String getRulesString(ArrayList<String> rulesIatiList) {
        String result = "";
        for (String item : rulesIatiList) {
            result += item + "\n";
        }
        return result;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtDesc;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtName = (TextView) itemLayoutView.findViewById(R.id.txtNameRules);
            txtDesc = (TextView) itemLayoutView.findViewById(R.id.txtDescRules);
        }
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        if (list_item != null) {
            return list_item.size() > 0 ? list_item.size() : 0;
        }
        return 0;
    }

}