package com.hami.servicehotel.Domestic.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hami.common.Util.UtilImageLoader;
import com.hami.servicehotel.R;

import java.util.ArrayList;

public class DomesticHotelSlidingImageAdapter extends PagerAdapter {


    private ArrayList<String> image_List_String;
    private LayoutInflater inflater;
    private Context context;

    //-----------------------------------------------
    public DomesticHotelSlidingImageAdapter(Context context, ArrayList<String> image_List_String) {
        this.context = context;
        this.image_List_String = image_List_String;
        inflater = LayoutInflater.from(context);
    }

    //-----------------------------------------------
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //-----------------------------------------------
    @Override
    public int getCount() {
        return image_List_String.size();
    }

    //-----------------------------------------------
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.row_image_gallery_layout, view, false);
        final ImageView imageView = imageLayout.findViewById(R.id.image);
        UtilImageLoader.loadImage(context, imageView, image_List_String.get(position), R.drawable.no_image_hotel);
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    //-----------------------------------------------
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    //-----------------------------------------------
    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    //-----------------------------------------------
    @Override
    public Parcelable saveState() {
        return null;
    }
//-----------------------------------------------

}