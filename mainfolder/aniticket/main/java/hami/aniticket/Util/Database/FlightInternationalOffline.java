package hami.mainapp.Util.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;

/**
 * Created by renjer on 2017-07-26.
 */

public class FlightInternationalOffline {
    protected static final String TAG = "DataAdapter";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    public final static String TABLE_CITY = "tbl_city_domestic";
    public final static String TABLE_INTERNATIONAL = "tbl_search_international";

    //-----------------------------------------------
    public FlightInternationalOffline(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    //-----------------------------------------------
    public FlightInternationalOffline createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    //-----------------------------------------------
    public FlightInternationalOffline open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        return this;
    }

    //-----------------------------------------------
    public FlightInternationalOffline openWrite() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
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
    public boolean savePlace(String key, String json) {
        try {
            this.createDatabase();
            this.openWrite();
            ContentValues insertValues = new ContentValues();
            insertValues.put("Key", key);
            insertValues.put("jsonString", json);
            String sql = "SELECT * FROM " + TABLE_INTERNATIONAL + " WHERE key LIKE  '%" + key+ "'";
            Cursor mCur = mDb.rawQuery(sql, null);
            long insertResult = 0;
            if (mCur != null && mCur.getCount() == 0)
                insertResult = mDb.insert(TABLE_INTERNATIONAL, null, insertValues);
            this.close();
            if (insertResult < 0)
                return false;
            return true;
        } catch (SQLException mSQLException) {
            return false;
        }
    }

    //-----------------------------------------------
    public ArrayList<SearchInternational> getListPlace() {
        ArrayList<SearchInternational> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();


            String sql = "SELECT * FROM " + TABLE_INTERNATIONAL;
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                Gson gson = new Gson();
                String result = mCur.getString(2);
                SearchInternational searchInternational = gson.fromJson(result, SearchInternational.class);
                items.add(searchInternational);
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {
            mSQLException.getMessage();
            //throw mSQLException;
            return items;
        }
    }
    //-----------------------------------------------
}
