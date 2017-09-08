package com.znjtgs.db;

import android.content.ContentValues;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/7/15.
 */

public class BusXCapacityDb {
    public static final String TABLE_NAME = "BusCapacityTable";
    private BaseDbHelper dbHelper;
    private Context context;
    private static BusXCapacityDb busXCapacityDb = null;

    private BusXCapacityDb(Context context) {
        this.context = context;
        this.dbHelper = new BaseDbHelper(context);
    }


    public static BusXCapacityDb get(Context context) {
        if (busXCapacityDb == null) {
            busXCapacityDb = new BusXCapacityDb(context);
        }
        return busXCapacityDb;
    }

    public long insert(int busId, int capacity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.BUS_ID, busId);
        contentValues.put(Columns.OVER_LOAD_NUMBER, capacity);
        contentValues.put(Columns.TIME, new SimpleDateFormat("yyyy/MM/dd :HH:mm:ss").format(new Date()));
        return dbHelper.insert(TABLE_NAME, contentValues);
    }

    private static class Columns extends BaseDbInfo {
        public static final String BUS_ID = "bus_id";
        public static final String OVER_LOAD_NUMBER = "over_load_number";
        public static final String TIME = "rec_time";

        public static String getCreatTAbleSql() {
            return String.format("create table if not exisit %s(%s,%s,%s,%s)",
                    TABLE_NAME,
                    ID + " integer primary key autoincrement",
                    BUS_ID + " integer",
                    OVER_LOAD_NUMBER + " integer",
                    TIME + " time");
        }
    }
}
