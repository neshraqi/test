package hami.mainapp.Util.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import hami.mainapp.Activity.ServiceSearch.ServiceBus.Services.Controller.Model.City;
import hami.mainapp.R;


public class BusOffline {


    protected static final String TAG = "BusOffline";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    public final static String TABLE_CITY = "tbl_city_fa";
    //-----------------------------------------------
    public BusOffline(Context context){
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }
    //-----------------------------------------------
    public BusOffline createDatabase() throws SQLException {
        try
        {
            mDbHelper.createDataBase();
        }
        catch (IOException mIOException)
        {


            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }
    //-----------------------------------------------
    public BusOffline open() throws SQLException {
        try
        {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        }
        catch (SQLException mSQLException)
        {


            throw mSQLException;
        }
        return this;
    }
    //-----------------------------------------------
    public void close() {
        mDbHelper.close();
    }
    //-----------------------------------------------
    public List<City> getCityByName(String name) {
        List<City> items = new ArrayList<>();
        try
        {
            this.createDatabase();
            this.open();
            String sql ="SELECT cid,country,cityName FROM "+TABLE_CITY + " WHERE cityName LIKE '%"+name+"%' AND cid % 1000000 <> 0";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur!=null && mCur.moveToNext()){
                items.add(new City(mCur.getString(0),mCur.getString(1),mCur.getString(2)));
            }
            this.close();
            return items;
        }
        catch (SQLException mSQLException)
        {


            return items;
        }
    }
    //-----------------------------------------------
    public List<City> getAllCity() {
        List<City> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();

            String sql = "SELECT cid,country,cityName FROM " + TABLE_CITY + " WHERE  cid % 1000000 <> 0";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new City(mCur.getString(0), mCur.getString(1), mCur.getString(2)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }
    //-----------------------------------------------
    public ArrayList<City> getCityListBusyAndAll() {
        ArrayList<City> items = new ArrayList<>();
        try {

            items.add(City.newInstance(mContext.getString(R.string.busyPlace)));
            items.add(City.newInstance("11320000", "1", "تهران"));
            items.add(City.newInstance("31310000", "1", "مشهد (خراسان )"));
            items.add(City.newInstance("21310000", "1", "اصفهان"));
            items.add(City.newInstance("26310000", "1", "تبریز"));
            items.add(City.newInstance("41310000", "1", "شیراز"));
            items.add(City.newInstance(mContext.getString(R.string.allPlace)));
            items.addAll(getAllCity());

            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }
}
