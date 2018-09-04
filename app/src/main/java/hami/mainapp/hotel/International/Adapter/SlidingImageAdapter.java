package hami.mainapp.hotel.International.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hami.common.BaseNetwork.BaseConfig;
import com.hami.common.Util.UtilImageLoader;
import hami.mainapp.R;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {


    private ArrayList<String> image_List_String;
    private LayoutInflater inflater;
    private Context context;
    private String hotelId;

    //-----------------------------------------------
    public SlidingImageAdapter(Context context, String hotelId, ArrayList<String> image_List_String) {
        this.context = context;
        this.image_List_String = image_List_String;
        this.hotelId = hotelId;
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
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        String url = BaseConfig.IMAGE_HOTEL_URL + hotelId + "/" + image_List_String.get(position);
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