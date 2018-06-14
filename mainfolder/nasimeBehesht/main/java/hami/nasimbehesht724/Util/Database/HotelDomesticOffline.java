package hami.nasimbehesht724.Util.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hami.nasimbehesht724.Activity.ServiceHotel.Domestic.Controller.Model.HotelDomesticCity;
import hami.nasimbehesht724.R;


public class HotelDomesticOffline {


    protected static final String TAG = "HotelDomesticOffline";
    private final Context mContext;
    private SQLiteDatabase mDb;
    private DataBaseHelper mDbHelper;
    public final static String TABLE_CITY = "tbl_city_hotel";

    //-----------------------------------------------
    public HotelDomesticOffline(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
    }

    //-----------------------------------------------
    public HotelDomesticOffline createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {


            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    //-----------------------------------------------
    public HotelDomesticOffline open() throws SQLException {
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
    public List<HotelDomesticCity> getCityByName(String name) {
        List<HotelDomesticCity> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT * FROM " + TABLE_CITY + " WHERE cityName LIKE '%" + name + "%' OR  nameFa LIKE '%" + name + "%' OR  stateFa LIKE '%" + name + "%'";
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new HotelDomesticCity(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(4)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public List<HotelDomesticCity> getAllCity() {
        List<HotelDomesticCity> items = new ArrayList<>();
        try {
            this.createDatabase();
            this.open();
            String sql = "SELECT * FROM " + TABLE_CITY;
            Cursor mCur = mDb.rawQuery(sql, null);
            while (mCur != null && mCur.moveToNext()) {
                items.add(new HotelDomesticCity(mCur.getString(0), mCur.getString(1), mCur.getString(2), mCur.getString(3), mCur.getString(4)));
            }
            this.close();
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }

    //-----------------------------------------------
    public List<HotelDomesticCity> getAllCityWithHeader() {
        ArrayList<HotelDomesticCity> items = new ArrayList<>();
        try {
            items.add(HotelDomesticCity.newInstance(mContext.getString(R.string.busyPlaceHotel)));
            items.add(HotelDomesticCity.newInstance("Mashhad", "مشهد", "خراسان رضوي", "59.615564", "36.28819"));
            items.add(HotelDomesticCity.newInstance("Qeshm", "قشم", "هرمزگان", "56.266251", "26.954269"));
            items.add(HotelDomesticCity.newInstance("Kish", "کیش", "هرمزگان", "54.028301", "26.526278"));
            items.add(HotelDomesticCity.newInstance("Shiraz", "شیراز", "فارس", "52.5090833", "29.6294833"));
            items.add(HotelDomesticCity.newInstance("Esfahan", "اصفهان", "اصفهان", "51.666641", "32.667703"));
            items.add(HotelDomesticCity.newInstance("Tehran", "تهران", "تهران", "51.368358", "35.722347"));
            items.add(HotelDomesticCity.newInstance(mContext.getString(R.string.allPlace)));
            items.addAll(getAllCity());
            return items;
        } catch (SQLException mSQLException) {


            return items;
        }
    }
}
