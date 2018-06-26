package hami.nasimbehesht724.BaseNetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.util.Log;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import hami.nasimbehesht724.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Presenter.NetworkListener;
import hami.nasimbehesht724.Const.KeyConst;
import hami.nasimbehesht724.Util.Keyboard;
import hami.nasimbehesht724.Util.SecureAndroid;

/**
 * Created by renjer on 1/10/2017.
 */

public class WebServiceNetwork {
    private Context context;
    private ConnectivityManager connectivityManager;
    private NetworkInfo wifiInfo, mobileInfo;
    private boolean connected = false;
    private int TIMEOUT = 50000;
    private static final String TAG = "WebServiceNetwork";
    //-----------------------------------------------

    public WebServiceNetwork(Context context) {
        this.context = context;
    }

    //-----------------------------------------------
    public boolean isOnline() {
        try {
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;


        } catch (Exception e) {

            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

    //-----------------------------------------------
    public void requestWebServiceByPost(String serviceUrl, int timeOut, HashMap<String, String> postDataParams, NetworkListener networkListener) {
        URL url;
        String response = "";
        try {
            networkListener.onStart();
            postDataParams.put(KeyConst.APP_DEVICE_ID, SecureAndroid.getSecureId(context));
            postDataParams.put(KeyConst.APP_DEVICE_OS, "android");
            if (!this.isOnline()) {
                networkListener.onErrorInternetConnection();
                return;
            } else {

                url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(timeOut);
                conn.setConnectTimeout(timeOut);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    networkListener.onFinish(response);
                } else {
                    networkListener.onErrorServer();
                }

            }
        } catch (Exception e) {

            networkListener.onErrorServer();
        }
    }

    //-----------------------------------------------
    public void requestWebServiceByPost(String serviceUrl, HashMap<String, String> postDataParams, NetworkListener networkListener) {
        URL url;
        String response = "";
        try {
            networkListener.onStart();
            postDataParams.put(KeyConst.APP_DEVICE_ID, SecureAndroid.getSecureId(context));
            postDataParams.put(KeyConst.APP_DEVICE_OS, "android");
            if (!this.isOnline()) {
                networkListener.onErrorInternetConnection();
                return;
            } else {

                url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIMEOUT);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    conn.disconnect();
                    networkListener.onFinish(response);
                } else {
                    networkListener.onErrorServer();
                }

            }
        } catch (Exception e) {
//
//
            networkListener.onErrorServer();
        }
    }

    //-----------------------------------------------
    public void requestWebServiceByPost(String serviceUrl, String jsonRequest, NetworkListener networkListener) {

        URL url;
        String response = "";
        try {
            jsonRequest = Keyboard.convertPersianNumberToEngNumber(jsonRequest);
            networkListener.onStart();
            if (!this.isOnline()) {
                networkListener.onErrorInternetConnection();
                return;
            } else {
                url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIMEOUT);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonRequest);

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    networkListener.onFinish(response);
                } else {
                    networkListener.onErrorServer();
                }

            }
        } catch (Exception e) {

            networkListener.onErrorServer();
        }
    }

    //-----------------------------------------------
    public void requestWebServiceByGet(String serviceUrl, @Nullable HashMap<String, String> postDataParams, NetworkListener networkListener) {
        URL url;
        String response = "";
        try {
            networkListener.onStart();
            if (!this.isOnline()) {
                networkListener.onErrorInternetConnection();
                return;
            } else {
                if (postDataParams == null)
                    url = new URL(serviceUrl);
                else
                    url = new URL(serviceUrl + "?" + getPostDataString(postDataParams));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(TIMEOUT);
                CookieManager cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Map<String, List<String>> headerFields = conn.getHeaderFields();

//                writer.write(getPostDataString(postDataParams));
//
//                writer.flush();
//                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    networkListener.onFinish(response);
                } else {
                    networkListener.onErrorServer();
                }

            }
        } catch (Exception e) {

            networkListener.onErrorServer();
        }
    }

    //-----------------------------------------------
    public String requestWebServiceByGet(String serviceUrl, @Nullable HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {

            if (!this.isOnline()) {
                //networkListener.onErrorInternetConnection();
                return null;
            } else {
                postDataParams.put(KeyConst.APP_DEVICE_ID, SecureAndroid.getSecureId(context));
                postDataParams.put(KeyConst.APP_DEVICE_OS, "android");
                if (postDataParams == null)
                    url = new URL(serviceUrl);
                else
                    url = new URL(serviceUrl + "?" + getPostDataString(postDataParams));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(TIMEOUT);
                CookieManager cookieManager = new CookieManager();
                CookieHandler.setDefault(cookieManager);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                Map<String, List<String>> headerFields = conn.getHeaderFields();

//                writer.write(getPostDataString(postDataParams));
//
//                writer.flush();
//                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    return response;
                } else {
                    return null;
                }

            }
        } catch (Exception e) {

            return null;
        }
    }

    //-----------------------------------------------
    public String requestWebServiceByPost(String serviceUrl, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            if (!this.isOnline()) {
                return null;
            } else {
                postDataParams.put(KeyConst.APP_DEVICE_ID, SecureAndroid.getSecureId(context));
                postDataParams.put(KeyConst.APP_DEVICE_OS, "android");
                url = new URL(serviceUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(TIMEOUT);
                conn.setConnectTimeout(TIMEOUT);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    return response;
                } else {
                    return "";
                }

            }
        } catch (Exception e) {

            return null;
        }
    }

    //-----------------------------------------------
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(Keyboard.convertPersianNumberToEngNumber(entry.getValue()), "UTF-8"));
        }
        //String finalResult = Keyboard.convertPersianNumberToEngNumber(result.toString());
        return result.toString();
        //return finalResult;
    }

}
