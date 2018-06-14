package hami.hamibelit.Activity.ContactUs;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import hami.hamibelit.R;
import hami.hamibelit.Util.CustomeChrome.CustomTabsPackages;
import hami.hamibelit.Util.Keyboard;
import hami.hamibelit.Util.UtilActionCall;
import hami.hamibelit.Util.UtilFonts;
import hami.hamibelit.View.Timeline.TimelineRow;
import hami.hamibelit.View.Timeline.TimelineViewAdapter;

public class ContactUsMaterialFragment extends Fragment {


    private View view;
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    private ArrayAdapter<TimelineRow> myAdapter;

    //-----------------------------------------------
    public static ContactUsMaterialFragment newInstance() {
        Bundle args = new Bundle();
        ContactUsMaterialFragment fragment = new ContactUsMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //-----------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        initialComponentFragment(view);
        return view;
    }

    //-----------------------------------------------
    private void initialComponentFragment(View view) {
        //coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator);
        UtilFonts.overrideFonts(getActivity(), view, UtilFonts.IRAN_SANS_NORMAL);
        Keyboard.closeKeyboard(getActivity());
        iniTimeline();
    }

    //-----------------------------------------------
    private void iniTimeline() {
        String[] title = {"051 - 31402", "info@respina24.com", "تور داخلی", "تور خارجی", "پرواز داخلی", "پرواز خارجی"};
        String[] telegrams = {"", "", "respinatour", "toorkhareji", "respinaflight", "Respina24com"};
        TimelineRow myRow = new TimelineRow(0);
        myRow.setTitle(title[0]);
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_call));
        myRow.setBellowLineColor(R.color.main_color_grey_300);
        myRow.setBellowLineSize(5);
        myRow.setImageSize(25);
        timelineRowsList.add(myRow);

        myRow = new TimelineRow(1);
        myRow.setTitle(title[1]);
        myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_email));
        myRow.setBellowLineColor(R.color.main_color_grey_300);
        myRow.setBellowLineSize(5);
        myRow.setImageSize(25);
        timelineRowsList.add(myRow);
        for (int i = 2; i < 6; i++) {
            myRow = new TimelineRow(i);
            myRow.setTitle(title[i]);
            myRow.setDescription(telegrams[i]);
            myRow.setImage(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_telegram));
            myRow.setBellowLineColor(R.color.main_color_grey_300);
            myRow.setBellowLineSize(5);
            myRow.setImageSize(25);
            timelineRowsList.add(myRow);
        }

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(getActivity(), 0, timelineRowsList, true);


        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) view.findViewById(R.id.rvResult);
        myListView.setAdapter(myAdapter);


        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = timelineRowsList.get(position);
                if (position == 0) {
                    UtilActionCall.call(getActivity(), row.getTitle());
                } else if (position == 1) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", row.getTitle(), null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "ارسال ایمیل"));
                } else {
                    showUrl("https://telegram.me/" + row.getDescription());
                }

            }
        };
        myListView.setOnItemClickListener(adapterListener);

    }

    //-----------------------------------------------
    private void showUrl(final String url) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new CustomTabsPackages(getActivity()).showUrl(url);
//                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                    CustomTabsIntent customTabsIntent = builder.build();
//                    builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//                    customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
                }
            });
        }
    }


}
