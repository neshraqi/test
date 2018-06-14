package hami.aniticket.Util.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hami.aniticket.Activity.ServiceSearch.ServiceTrain.Services.Controller.Model.CityTrain;
import hami.aniticket.R;


public class TrainOffline {


    protected static final String TAG = "TrainOffline";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    public final static String TABLE_CITY = "city";

    //-----------------------------------------------
    public TrainOffline(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    //-----------------------------------------------
    public TrainOffline createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {


            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    //-----------------------------------------------
    public TrainOffline open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            //mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
        return this;
    }

    //-----------------------------------------------
    public void close() {
        mDbHelper.close();
    }

    //-----------------------------------------------
    public List<CityTrain> getCityByName(String name) {
        List<CityTrain> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT cityName,cityNamePersian FROM " + TABLE_CITY + " WHERE cityName LIKE '%" + name + "%' OR  cityNamePersian LIKE '%" + name + "%'";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new CityTrain(mCur.getString(0), mCur.getString(1)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public List<CityTrain> getAllCity() {
        List<CityTrain> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT cityName,cityNamePersian FROM " + TABLE_CITY;
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new CityTrain(mCur.getString(0), mCur.getString(1)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public List<CityTrain> getAllCityWithHeader() {
        ArrayList<CityTrain> items = new ArrayList<>();
        try {
            items.add(new CityTrain(mContext.getString(R.string.busyPlace)));
            items.add(new CityTrain("Tehran", "تهران"));
            items.add(new CityTrain("Mashhad", "مشهد"));
            items.add(new CityTrain("Esfahan", "اصفهان"));
            items.add(new CityTrain("Tabriz", "تبریز"));
            items.add(new CityTrain("Shiraz", "شیراز"));
            items.add(new CityTrain(mContext.getString(R.string.allPlace)));
            items.addAll(getAllCity());
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }
}
