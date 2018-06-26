package hami.nasimbehesht724.Activity.Updates;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadFile extends AsyncTask<String, String, Boolean> {

    private ProgressDialog pDialog;
    private Context context;
    private DownloadFileListener downloadFileListener;
    private String fileName,path;
    //-----------------------------------------------
    public DownloadFile(Context context,DownloadFileListener downloadFileListener) {
        fileName = "respina24.apk";
        path = Environment.getExternalStorageDirectory()+"/download/"+fileName;
        this.context = context;
        this.downloadFileListener = downloadFileListener;
    }
    //-----------------------------------------------
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("در حال دریافت رسپینا24،شکیبا باشید...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
    }
    //-----------------------------------------------
    @Override
    protected Boolean doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file

            //String addressFile = "/sdcard/download/respina24.apk";

            File file = new File(path+fileName);
            if(file.exists()){
                file.delete();
            }
            OutputStream output = new FileOutputStream(path+fileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress(""+(int)((total*100)/lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            if(file.exists()){
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                installIntent.setDataAndType(
                        Uri.parse("file://" + file.toString()),
                        "application/vnd.android.package-archive");
                context.startActivity(installIntent);
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    //-----------------------------------------------
        protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }
    //-----------------------------------------------
    @Override
    protected void onPostExecute(Boolean status) {
        pDialog.dismiss();
        if(status!=null && status){
            File apkfile = new File(path+fileName);
            if (apkfile.exists()) {
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                installIntent.setDataAndType(
                        Uri.parse("file://" + apkfile.toString()),
                        "application/vnd.android.package-archive");
                context.startActivity(installIntent);
            }
            downloadFileListener.onSuccessDownload();
        }
        else{
            downloadFileListener.onErrorDownload();
        }

        //String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
        //my_image.setImageDrawable(Drawable.createFromPath(imagePath));
    }
    //-----------------------------------------------

}