package hami.mainapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TrainFragment extends Fragment implements View.OnClickListener{


    private TextView tv_train, tv_flight, tv_bus,tv_tour, tv_hotel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        tv_train = (TextView) view.findViewById(R.id.tv_train);
        tv_flight = (TextView) view.findViewById(R.id.tv_flight);
        tv_bus = (TextView) view.findViewById(R.id.tv_bus);
        tv_tour = (TextView) view.findViewById(R.id.tv_tour);
        tv_hotel = (TextView) view.findViewById(R.id.tv_hotel);

        tv_flight.setOnClickListener(this);
        tv_train.setOnClickListener(this);
        tv_bus.setOnClickListener(this);
        tv_tour.setOnClickListener(this);
        tv_hotel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.tv_train) {
            try {
                Intent myIntent = new Intent(TrainFragment.this.getActivity(), Class.forName("com.hami.servicetrain.ActivityMainTrain"));
                startActivity(myIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (i == R.id.tv_flight) {
            try {
                Intent myIntent = new Intent(TrainFragment.this.getActivity(), Class.forName("com.hami.serviceflight.ActivityMainFlight"));
                startActivity(myIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (i == R.id.tv_bus) {
            try {
                Intent myIntent = new Intent(TrainFragment.this.getActivity(), Class.forName("com.hami.servicebus.ActivityMainBus"));
                startActivity(myIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (i == R.id.tv_tour) {
            try {
                Intent myIntent = new Intent(TrainFragment.this.getActivity(), Class.forName("com.hami.servicetour.ActivityMainTour"));
                startActivity(myIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (i == R.id.tv_hotel) {
            try {
                Intent myIntent = new Intent(TrainFragment.this.getActivity(), Class.forName("com.hami.servicehotel.ActivityMainHotel"));
                startActivity(myIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
