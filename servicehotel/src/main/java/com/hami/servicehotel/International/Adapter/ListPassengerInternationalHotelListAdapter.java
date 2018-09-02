package com.hami.servicehotel.International.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hami.common.BaseController.SelectItemList;
import com.hami.common.Util.UtilFonts;
import com.hami.servicehotel.International.Controller.Model.PassengerInfoInternationalHotel;
import com.hami.servicehotel.International.Controller.Model.RoomPassengerInfoInternationalHotel;
import com.hami.servicehotel.International.Controller.Model.RowTypeHotelPassenger;
import com.hami.servicehotel.R;

import java.util.ArrayList;


public class ListPassengerInternationalHotelListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<RowTypeHotelPassenger> listItem;
    private Context context;
    private SelectItemList<PassengerInfoInternationalHotel> hotelSelectItemList;
    private ArrayList<String> listPrice;

    //-----------------------------------------------
    public ListPassengerInternationalHotelListAdapter(Context context,
                                                      ArrayList<RowTypeHotelPassenger> list,
                                                      SelectItemList<PassengerInfoInternationalHotel> hotelSelectItemList) {
        this.hotelSelectItemList = hotelSelectItemList;
        listItem = list;
        this.context = context;
    }

    //-----------------------------------------------
    public ListPassengerInternationalHotelListAdapter(Context context, ArrayList<RowTypeHotelPassenger> list) {
        listItem = list;
        this.context = context;
    }
    //-----------------------------------------------

    @Override
    public int getItemViewType(int position) {
        if (listItem.get(position) instanceof PassengerInfoInternationalHotel) {
            return RowTypeHotelPassenger.PASSENGER_ROW_TYPE;
        } else if (listItem.get(position) instanceof RoomPassengerInfoInternationalHotel) {
            return RowTypeHotelPassenger.ROOM_ROW_TYPE;
        } else
            return -1;

    }

    //-----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RowTypeHotelPassenger.PASSENGER_ROW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_passenger_international_hotel, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        } else if (viewType == RowTypeHotelPassenger.ROOM_ROW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_room_passenger_international_hotel, null);
            MyViewHolderRoom myViewHolder = new MyViewHolderRoom(view);
            return myViewHolder;
        } else {
            return null;
        }

    }

    //-----------------------------------------------
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof MyViewHolder) {
            MyViewHolder viewHolderItem = (MyViewHolder) viewHolder;
            PassengerInfoInternationalHotel passengerInfo = (PassengerInfoInternationalHotel) listItem.get(position);
            if (passengerInfo.getFirstName().length() == 0 && passengerInfo.getLastName().length() == 0) {
                viewHolderItem.txtFullName.setText(R.string.validateTypeFullNameEng);
            } else {
                viewHolderItem.txtFullName.setText(passengerInfo.getFirstName() + " " + passengerInfo.getLastName());
            }
            viewHolderItem.txtTypePassenger.setText(passengerInfo.getTypePassengerResource());
            if (passengerInfo.getHasError() == 1) {
                viewHolderItem.imgError.setVisibility(View.VISIBLE);
                Animation vibrateAnimation = AnimationUtils.loadAnimation(context, R.anim.shake);
                viewHolderItem.itemView.startAnimation(vibrateAnimation);
            } else if (passengerInfo.getHasError() == -1) {
                viewHolderItem.imgError.setVisibility(View.VISIBLE);
            } else {
                viewHolderItem.imgError.setVisibility(View.INVISIBLE);
            }
        } else if (viewHolder instanceof MyViewHolderRoom) {
            MyViewHolderRoom viewHolderItem = (MyViewHolderRoom) viewHolder;
            RoomPassengerInfoInternationalHotel roomPassengerInfoInternationalHotel = (RoomPassengerInfoInternationalHotel) listItem.get(position);
            viewHolderItem.txtFullNameRoom.setText(roomPassengerInfoInternationalHotel.getFullNameRoom());
        }

    }

    //-----------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFullName;
        public TextView txtTypePassenger;
        public ImageView imgError;

        public MyViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullName = (TextView) itemLayoutView.findViewById(R.id.txtFullName);
            txtTypePassenger = (TextView) itemLayoutView.findViewById(R.id.txtTypePassenger);
            imgError = (ImageView) itemLayoutView.findViewById(R.id.imgError);
            if (hotelSelectItemList != null)
                itemLayoutView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PassengerInfoInternationalHotel passengerInfoInternationalHotel = (PassengerInfoInternationalHotel) listItem.get(getAdapterPosition());
                        hotelSelectItemList.onSelectItem(passengerInfoInternationalHotel, getAdapterPosition());
                    }
                });
            // txtTypePrice = itemLayoutView.findViewById(R.id.txtTypePrice);
        }
    }

    //-----------------------------------------------
    public class MyViewHolderRoom extends RecyclerView.ViewHolder {

        public TextView txtFullNameRoom;

        public MyViewHolderRoom(final View itemLayoutView) {
            super(itemLayoutView);
            UtilFonts.overrideFonts(context, itemLayoutView, UtilFonts.IRAN_SANS_NORMAL);
            txtFullNameRoom = (TextView) itemLayoutView.findViewById(R.id.txtFullnameRoom);
        }
    }

    //-----------------------------------------------
    public void update(int index, PassengerInfoInternationalHotel passengerInfo) {
        listItem.set(index, passengerInfo);
        notifyItemChanged(index);
        notifyDataSetChanged();
    }

    //-----------------------------------------------
    public ArrayList<PassengerInfoInternationalHotel> getListPassenger() {
        try {
            ArrayList<PassengerInfoInternationalHotel> result = new ArrayList<>();
            for (RowTypeHotelPassenger item : listItem) {
                if (item instanceof PassengerInfoInternationalHotel) {
                    PassengerInfoInternationalHotel passengerInfoInternationalHotel = (PassengerInfoInternationalHotel) item;
                    result.add(passengerInfoInternationalHotel);
                }
            }
            return result;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    //-----------------------------------------------
    public Boolean validateInfoPassenger() {
        for (int index = 0; index < listItem.size(); index++) {
            if (listItem.get(index) instanceof PassengerInfoInternationalHotel) {
                PassengerInfoInternationalHotel passengerInfo = (PassengerInfoInternationalHotel) listItem.get(index);
                if (passengerInfo.getHasError() == -1 || passengerInfo.getHasError() == 1) {
                    passengerInfo.setHasError(true);
                    update(index, passengerInfo);
                    return false;
                }
            }
        }
        return true;
    }

    //-----------------------------------------------
    @Override
    public int getItemCount() {
        return listItem.size();
    }

}