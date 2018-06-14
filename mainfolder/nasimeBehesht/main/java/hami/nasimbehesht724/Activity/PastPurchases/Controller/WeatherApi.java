package hami.nasimbehesht724.Activity.PastPurchases.Controller;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import hami.nasimbehesht724.Activity.PastPurchases.Model.Forecast;
import hami.nasimbehesht724.Activity.PastPurchases.Model.LocationWeather;
import hami.nasimbehesht724.Activity.PastPurchases.Model.ResponseWeather;
import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.nasimbehesht724.BaseController.CallBackRequestSearch;
import hami.nasimbehesht724.BaseNetwork.WebServiceNetwork;

/**
 * Created by renjer on 1/10/2017.
 */

public class WeatherApi {

    private Context context;
    private Thread thread;
    //-----------------------------------------------

    public WeatherApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void searchWeather(final String location, final String date, final CallBackRequestSearch<LocationWeather> CallBackRequestSearch) {
        final String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.Forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + location + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        final HashMap<String, String> getHashMap = new HashMap<>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onErrorInternetConnection() {

                    }

                    @Override
                    public void onErrorServer() {

                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String json = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("item").toString();
                            Gson gson = new Gson();
                            ResponseWeather responseWeather = gson.fromJson(result, ResponseWeather.class);
                            if (responseWeather != null) {
                                Forecast forecast = responseWeather.getForecast().get(0);
                                if (responseWeather.getCondition().getDate().contains(date) && forecast.getDate().contains(date)) {
                                    //developing
                                    //responseWeather.getCondition().getDate()
                                    LocationWeather locationWeather = new LocationWeather();
                                    CallBackRequestSearch.getResponse(locationWeather);
                                } else {
                                    //LocationWeather locationWeather = null;
                                    for (Forecast object : responseWeather.getForecast()) {
                                        if (object.getDate().contains(date)) {
                                            LocationWeather locationWeather = new LocationWeather();
                                            CallBackRequestSearch.getResponse(locationWeather);
                                            break;
                                        }
                                    }
                                }
                            }

                        } catch (Exception e) {

                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    public synchronized void cancelRequest() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }


}
