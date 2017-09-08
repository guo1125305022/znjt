package com.znjtgs.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JKX_GXL on 2017/5/14.
 */

public class BaseDbHelper extends SQLiteOpenHelper {
    private static final String DB_BASE_NAME = "tra";

//    public static final String TABLE_

    public BaseDbHelper(Context context) {
        super(context, DB_BASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public long insert(String tableName, ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        return writableDatabase.insert(tableName, null, contentValues);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        return writableDatabase.delete(table, whereClause, whereArgs);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        return readableDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        return writableDatabase.update(table, values, whereClause, whereArgs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
