package hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Presenter.OnSelectItemPassengerDomesticListener;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.TrainPassengerInfo;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceTrain.Services.Controller.Presenter.OnSelectItemPassengerTrainListener;
import hami.nasimbehesht724.Const.TrainRules;
import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;


public class PassengerTrainListAdapter extends RecyclerView.Adapter<PassengerTrainListAdapter.MyViewHolder> {

    private List<TrainPassengerInfo> listItem;
    private Context context;
    private OnSelectItemPassengerDomesticListener onSelectItemPassengerDomesticListener;
    private OnSelectItemPassengerTrainListener onSelectItemPassengerTrainListener;
    private Typeface typeface;
    private int infantCount = 0, adultsCount = 0, childCount = 0;

    //-----------------------------------------------
    public PassengerTrainListAdapter(Context context, OnSelectItemPassengerTrainListener onSelectItemPassengerTrainListener) {
        this.onSelectItemPassengerTrainListener = onSelectItemPassengerTrainListener;
        listItem = new ArrayList<>();
        this.context = context;
        //typeface = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_web.ttf");
    }

    //-----------------------------------------------
    @Override
    public PassengerTrainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_service_train_passenger_info, null);
        PassengerTrainListAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final PassengerTrainListAdapter.MyViewHolder viewHolder, final int position) {
        final TrainPassengerInfo trainPassengerInfo = listItem.get(position);
        if (trainPassengerInfo.getTypePassengerApp() == TrainRules.TP_ADULTS) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.adults));
        } else if (trainPassengerInfo.getTypePassengerApp() == TrainRules.TP_CHILD) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.children));
        } else if (trainPassengerInfo.getTypePassengerApp() == TrainRules.TP_SHAHED) {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.shahed));
        } else {
            viewHolder.txtTypePassenger.setText("ویرایش اطلاعات مسافر " + context.getString(R.string.veteran));
        }
        viewHolder.txtFullName.setText(trainPassengerInfo.getFirstNamePersian() + " " + trainPassengerInfo.getLastNamePersian());
        if (trainPassengerInfo.getNationalityType() == TrainRules.PN_IRANIAN)
            viewHolder.txtNationalCode.setText(trainPassengerInfo.getNationalCode());
        else
            viewHolder.txtNationalCode.setText(trainPassengerInfo.getPassportNo());
    }
    //-----------------------------------------------

    public List<TrainPassengerInfo> getListItem() {
        return listItem;
    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFullName;
        public TextView txtTypePassenger;
        public TextView txtNationalCode;
        public ImageView btnRemoveItem;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullName);
            txtNationalCode = (TextView) itemLayoutView.findViewById(R.id.txtNationalCode);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            btnRemoveItem = (ImageView) itemLayoutView.findViewById(R.id.btnImgRemove);
            btnRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerTrainListener.onRemoveItemTrain(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectItemPassengerTrainListener.onSelectItemTrain(listItem.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }

    //-----------------------------------------------
    public void addNewPassenger(TrainPassengerInfo trainPassengerInfo) {
        listItem.add(trainPassengerInfo);
        notifyItemInserted(listItem.size() - 1);
    }

    //-----------------------------------------------
    public void editPassenger(TrainPassengerInfo trainPassengerInfo, int position) {
        listItem.set(position, trainPassengerInfo);
        notifyItemChanged(position);
        notifyItemRangeChanged(0, listItem.size() - 1);
    }

    //-----------------------------------------------
    public void removePassenger(int position) {
        listItem.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listItem.size());
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
