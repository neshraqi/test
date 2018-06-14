package hami.aniticket.Activity.Updates;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;

import java.util.HashMap;

import hami.aniticket.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.aniticket.BaseNetwork.BaseConfig;
import hami.aniticket.BaseNetwork.WebServiceNetwork;


public class UpdatesApi {

    private Context context;

    //-----------------------------------------------
    public UpdatesApi(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public void getUpdate(final GetUpdatePresenter getUpdatePresenter) {

        final String url = BaseConfig.BASE_URL_MASTER + "main/getApkVersion";
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> getHashMap = new HashMap<>();
                new WebServiceNetwork(context).requestWebServiceByPost(url, getHashMap, new NetworkListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onErrorInternetConnection() {
                        getUpdatePresenter.noUpdate();
                    }

                    @Override
                    public void onErrorServer() {
                        getUpdatePresenter.noUpdate();
                    }

                    @Override
                    public void onFinish(String result) {
                        try {
                            Gson gson = new Gson();
                            UpdateResponse updateResponse = gson.fromJson(result, UpdateResponse.class);
                            if (updateResponse.getVersion() > getVersionInfo())
                                getUpdatePresenter.hasUpdate();
                            else
                                getUpdatePresenter.noUpdate();
                        } catch (Exception e) {
                            getUpdatePresenter.noUpdate();
                        }

                    }
                });
            }
        });
        thread.start();
    }

    //-----------------------------------------------
    private int getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    //-----------------------------------------------

}
