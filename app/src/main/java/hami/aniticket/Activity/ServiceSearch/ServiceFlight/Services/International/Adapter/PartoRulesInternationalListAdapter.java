package hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesDetailItemParto;
import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model2.RulesItemParto;
import hami.aniticket.R;
import hami.aniticket.Util.UtilFonts;

/**
 * Created by renjer on 1/8/2017.
 */

public class PartoRulesInternationalListAdapter extends RecyclerView.Adapter<PartoRulesInternationalListAdapter.MyViewHolder> {

    private ArrayList<RulesItemParto> list_item;
    private Context context;

    //-----------------------------------------------
    public PartoRulesInternationalListAdapter(Context context, ArrayList<RulesItemParto> item) {
        this.list_item = item;
        this.context = context;
    }

    //-----------------------------------------------
    @Override
    public PartoRulesInternationalListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_flight_international_rules_layout, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {
        RulesItemParto rulesItemParto = list_item.get(position);
        //viewHolder.txtName.setText("");
        viewHolder.txtDesc.setText(getRulesString(rulesItemParto.getRuleDetailsList()));
    }

    //-----------------------------------------------
    private String getRulesString(ArrayList<RulesDetailItemParto> rulesIatiList) {
        String result = "";
        for (int j = 0; j < rulesIatiList.size(); j++) {
            result += rulesIatiList.get(j).getCategory() + "\n";
            result += rulesIatiList.get(j).getRules() + "\n";
        }

        return result;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView txtDesc;
        public View line;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtName = (TextView) itemLayoutView.findViewById(R.id.txtNameRules);
            txtDesc = (TextView) itemLayoutView.findViewById(R.id.txtDescRules);
            line = itemLayoutView.findViewById(R.id.line);
            line.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
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