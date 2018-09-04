package hami.mainapp.tour.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hami.common.BaseNetwork.BaseConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hami.mainapp.R;


public class TourSlidingImageAdapter extends PagerAdapter {


    private ArrayList<String> image_List_String;
    private LayoutInflater inflater;
    private Context context;

    //-----------------------------------------------
    public TourSlidingImageAdapter(Context context, ArrayList<String> image_List_String) {
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

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);
        Picasso.with(context)
                .load(BaseConfig.FOLDER_IMAGE_TOUR_URL + image_List_String.get(position) + ".jpg")
                .error(R.drawable.no_image_hotel)
                .into(imageView);
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