package hami.nasimbehesht724.View.Timeline;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hami.nasimbehesht724.R;
import hami.nasimbehesht724.Util.UtilFonts;


public class TimelineViewAdapter extends ArrayAdapter<TimelineRow> {

    private Context context;
    private Resources res;
    private List<TimelineRow> RowDataList;


    public TimelineViewAdapter(Context context, int resource, ArrayList<TimelineRow> objects, boolean orderTheList) {
        super(context, resource, objects);
        this.context = context;
        res = context.getResources();
        this.RowDataList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimelineRow row = RowDataList.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ctimeline_row, null);
        UtilFonts.overrideFonts(context, view, UtilFonts.IRAN_SANS_NORMAL);
        TextView rowTitle = (TextView) view.findViewById(R.id.crowTitle);
        TextView rowDescription = (TextView) view.findViewById(R.id.crowDesc);
        ImageView rowImage = (ImageView) view.findViewById(R.id.crowImg);
        View rowUpperLine = (View) view.findViewById(R.id.crowUpperLine);
        View rowLowerLine = (View) view.findViewById(R.id.crowLowerLine);


//        final float scale = getContext().getResources().getDisplayMetrics().density;


        if (position == 0 && position == RowDataList.size() - 1) {
            rowUpperLine.setVisibility(View.INVISIBLE);
            rowLowerLine.setVisibility(View.INVISIBLE);
        } else if (position == 0) {
//            int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);

            rowUpperLine.setVisibility(View.INVISIBLE);
            //rowLowerLine.setBackgroundColor(row.getBellowLineColor());
//            rowLowerLine.getLayoutParams().width = pixels;
        } else if (position == RowDataList.size() - 1) {
//            int pixels = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

            rowLowerLine.setVisibility(View.INVISIBLE);
//            rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
//            rowUpperLine.getLayoutParams().width = pixels;
        } else {
//            int pixels = (int) (row.getBellowLineSize() * scale + 0.5f);
//            int pixels2 = (int) (RowDataList.get(position - 1).getBellowLineSize() * scale + 0.5f);

            //rowLowerLine.setBackgroundColor(row.getBellowLineColor());
            //rowUpperLine.setBackgroundColor(RowDataList.get(position - 1).getBellowLineColor());
//            rowLowerLine.getLayoutParams().width = pixels;
//            rowUpperLine.getLayoutParams().width = pixels2;
        }

        if (row.getTitle() == null)
            rowTitle.setVisibility(View.GONE);
        else {
            rowTitle.setText(row.getTitle());
            if (row.getTitleColor() != 0)
                rowTitle.setTextColor(row.getTitleColor());
        }
        if (row.getDescription() == null)
            rowDescription.setVisibility(View.GONE);
        else {
            rowDescription.setText(row.getDescription());
            rowDescription.setVisibility(View.GONE);
            if (row.getDescriptionColor() != 0)
                rowDescription.setTextColor(row.getDescriptionColor());
        }


        if (row.getImage() != null) {
            rowImage.setImageBitmap(row.getImage());
        }

//        int pixels = (int) (row.getImageSize() * scale + 0.5f);
//        rowImage.getLayoutParams().width = pixels;
//        rowImage.getLayoutParams().height = pixels;

        View backgroundView = view.findViewById(R.id.crowBackground);
        if (row.getBackgroundColor() == 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                backgroundView.setBackground(null);
            } else {
                backgroundView.setBackgroundResource(0);
            }
        else {
            if (row.getBackgroundSize() == -1) {
//                backgroundView.getLayoutParams().width = pixels;
//                backgroundView.getLayoutParams().height = pixels;
            } else {
//                int BackgroundPixels = (int) (row.getBackgroundSize() * scale + 0.5f);
//                backgroundView.getLayoutParams().width = BackgroundPixels;
//                backgroundView.getLayoutParams().height = BackgroundPixels;
            }
            GradientDrawable background = (GradientDrawable) backgroundView.getBackground();
            if (background != null) {
                background.setColor(row.getBackgroundColor());
            }
        }


        return view;
    }


}