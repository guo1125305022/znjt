package com.znjtgs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2017/7/7.
 */

public class CarRechargeDb {
    private static CarRechargeDb regarchDb = null;
    private static BaseDbHelper baseDbHelper = null;
    public static final String TABLE_RECHARGE = "recharge";
    public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static Context context;

    private CarRechargeDb(Context context) {
        this.context = context.getApplicationContext();
        baseDbHelper = new BaseDbHelper(this.context);
        initDb();
    }

    /**
     * 初始化数据表
     */
    private void initDb() {
        //检查数据表是否存在如果存在不创建数据表反之则创建数据表
        baseDbHelper.getWritableDatabase().execSQL(CarRegarchDbColumns.getCreateTableSql());
    }

    /**
     * 插入数据
     * @param carId 小车编号
     * @param money 充值金额
     * @param userName 用户操作
     * @return
     */
    public long insertData(int carId, int money, String userName) {
        ContentValues cv = new ContentValues();
        cv.put(CarRegarchDbColumns.CAR_ID, carId);
        cv.put(CarRegarchDbColumns.RECHARGE_MONEY, money);
        cv.put(CarRegarchDbColumns.RECHARGE_USER, userName);
        cv.put(CarRegarchDbColumns.RECHARGE_TIME, dateFormat.format(new Date()));
        return baseDbHelper.insert(TABLE_RECHARGE, cv);
    }

    /**
     * 查询 所有数据
     *
     * @param layoutId     布局ID
     * @param bindToViewId 需要显示到的视图 id
     * @param up           是否升序
     * @return
     */
    public SimpleCursorAdapter queryAllData(int layoutId, int[] bindToViewId, boolean up) {
        String[] columns = CarRegarchDbColumns.getColumns();
        Cursor cursor = baseDbHelper.query(TABLE_RECHARGE, columns, null, null, null, null, CarRegarchDbColumns.RECHARGE_TIME + (up ? " asc" : " desc"));
        return new SimpleCursorAdapter(context, layoutId, cursor, columns, bindToViewId, 0);
    }


    public static CarRechargeDb get(Context context) {
        if (regarchDb == null) {
            regarchDb = new CarRechargeDb(context);
        }
        return regarchDb;
    }

    public static class CarRegarchDbColumns extends BaseDbInfo {
        public static final String CAR_ID = "carId";//小车编号
        public static final String RECHARGE_MONEY = "rechargeMoney";//小车充值金额
        public static final String RECHARGE_TIME = "rechargeTime";//充值时间
        public static final String RECHARGE_USER = "rechargeUser";//操作的用户

        public static String[] getColumns() {
            return new String[]{ID, CAR_ID, RECHARGE_MONEY, RECHARGE_USER, RECHARGE_TIME};
        }

        /**
         * 获取创建数据库表SQL语句
         *
         * @return sql 语句
         */
        public static String getCreateTableSql() {
            String format = String.format("create table if not exists %s(%s,%s,%s,%s,%s)",
                    TABLE_RECHARGE,
                    ID + " integer primary key autoincrement",
                    CAR_ID + " integer ",
                    RECHARGE_MONEY + " integer",
                    RECHARGE_USER + " varchar(20)",
                    RECHARGE_TIME + " time"
            );

            return format;
        }
    }
}
