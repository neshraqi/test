package hami.mainapp.Util.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import hami.mainapp.Activity.ServiceSearch.ConstService.ServiceID;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.Domestic.Controller.Model.RegisterFlightResponse;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.Country;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.SearchInternational;
import hami.mainapp.Activity.ServiceSearch.ServiceFlight.Services.International.Controller.Model.TicketInternational;
import hami.mainapp.R;

/**
 * Created by renjer on 2017-07-26.
 */

public class FlightDomesticOffline {
    private static final String TAG = "FragmentPreFactorInternational";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    public final static String TABLE_CITY = "tbl_city_domestic";
    public final static String TABLE_TICKET = "tbl_ticket";

    //-----------------------------------------------
    public FlightDomesticOffline(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    //-----------------------------------------------
    public FlightDomesticOffline createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {


            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    //-----------------------------------------------
    public FlightDomesticOffline open() throws SQLException {
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
    public FlightDomesticOffline openWrite() throws SQLException {
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
    public ArrayList<Country> getCountry() {
        try {
            this.createDatabase();
            this.open();
            ArrayList<Country> items = new ArrayList<>();
            String sql = "SELECT * FROM tbl_country";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new Country(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(4)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public ArrayList<Country> getCountry(@Nullable String country) {
        try {
            this.createDatabase();
            this.open();
            ArrayList<Country> items = new ArrayList<>();
            String sql = "SELECT * FROM tbl_country WHERE fullname LIKE  '%" + country + "%' OR persian LIKE '%" + country + "%' ";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new Country(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(4)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public Country getCountryByName(String country) {
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT * FROM tbl_country WHERE fullname LIKE  '%" + country + "'";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null && mCur.moveToFirst()) {
                return new Country(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(3));
            }
            this.close();
            return new Country();
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public ArrayList<SearchInternational> getCityByName(String name) {
        try {
            this.createDatabase();
            this.open();
            ArrayList<SearchInternational> items = new ArrayList<>();
            String sql = "SELECT cityNameEng,cityNameFr,cityYata FROM " + TABLE_CITY + " WHERE cityNameEng LIKE '%" + name + "%'"
                    + " OR  cityNameFr LIKE '%" + name + "%'" +
                    " OR  cityYata LIKE '%" + name + "%'";

            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(SearchInternational.newInstance(mCur.getString(0), mCur.getString(1), mCur.getString(2)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public ArrayList<SearchInternational> getCityList() {
        try {
            this.createDatabase();
            this.open();
            ArrayList<SearchInternational> items = new ArrayList<>();
            String sql = "SELECT cityNameEng,cityNameFr,cityYata FROM " + TABLE_CITY;
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(SearchInternational.newInstance(mCur.getString(0), mCur.getString(1), mCur.getString(2)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public ArrayList<SearchInternational> getCityListBusy() {
        try {
            ArrayList<SearchInternational> items = new ArrayList<>();
            items.add(SearchInternational.newInstance("Tehran", "تهران", "THR"));
            items.add(SearchInternational.newInstance("Mashhad", "مشهد", "MHD"));
            items.add(SearchInternational.newInstance("Isfahan", "اصفهان", "IFN"));
            items.add(SearchInternational.newInstance("Tabriz", "تبریز", "TBZ"));
            items.add(SearchInternational.newInstance("Shiraz", "شیراز", "SYZ"));

            return items;
        } catch (SQLException mSQLException) {


            throw mSQLException;
        }
    }

    //-----------------------------------------------
    public ArrayList<SearchInternational> getCityListBusyAndAll() {
        ArrayList<SearchInternational> items = new ArrayList<>();
        try {

            items.add(SearchInternational.newInstance(mContext.getString(R.string.busyPlace)));
            items.add(SearchInternational.newInstance("Tehran", "تهران", "THR"));
            items.add(SearchInternational.newInstance("Mashhad", "مشهد", "MHD"));
            items.add(SearchInternational.newInstance("Isfahan", "اصفهان", "IFN"));
            items.add(SearchInternational.newInstance("Tabriz", "تبریز", "TBZ"));
            items.add(SearchInternational.newInstance("Shiraz", "شیراز", "SYZ"));
            items.add(SearchInternational.newInstance(mContext.getString(R.string.allPlace)));
            items.addAll(getCityList());

            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public boolean saveTicketFlight(String id, String json, int type) {
        try {
            this.createDatabase();
            this.openWrite();
            ContentValues insertValues = new ContentValues();
            insertValues.put("id", id);
            insertValues.put("type", type);
            insertValues.put("json", json);
            long insertResult = mDb.insert(TABLE_TICKET, null, insertValues);
            this.close();
            if (insertResult < 0)
                return false;
            return true;
        } catch (SQLException mSQLException) {


            return false;
        }
    }

    //-----------------------------------------------
    public ArrayList<RegisterFlightResponse> getFlightDomesticPastPurchases() {
        ArrayList<RegisterFlightResponse> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT * FROM " + TABLE_TICKET + " WHERE type=" + ServiceID.SERVICE_ID_FLIGHT_DOMESTIC;
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                Gson gson = new Gson();
                String result = mCur.getString(2);
                RegisterFlightResponse registerFlightResponse = gson.fromJson(result, RegisterFlightResponse.class);
                items.add(registerFlightResponse);
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public ArrayList<TicketInternational> getFlightInternationalPastPurchases() {
        ArrayList<TicketInternational> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT * FROM " + TABLE_TICKET + " WHERE type=" +
                    ServiceID.SERVICE_ID_FLIGHT_INTERNATIONAL + " ORDER BY id desc";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                Gson gson = new Gson();
                String result = mCur.getString(2);
                TicketInternational registerFlightResponse = gson.fromJson(result, TicketInternational.class);
                items.add(registerFlightResponse);
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }
}
