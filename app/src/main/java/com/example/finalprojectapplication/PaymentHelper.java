package com.example.finalprojectapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentHelper extends SQLiteOpenHelper {
    private static final int DATEBASE_VERSION = 1;
    private static final String DATEBASE_NAME = "electricity.db";

    // 缴费记录表
    public static final String TABLE_RECORDS = "records";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";

    // 创建表SQL
    private static final String CREATE_TABLE_RECORDS =
            "CREATE TABLE " + TABLE_RECORDS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ACCOUNT + " TEXT NOT NULL, " +
                    COLUMN_AMOUNT + " REAL NOT NULL, " +
                    COLUMN_DATE + " TEXT NOT NULL);";


    public PaymentHelper(@Nullable Context context) {
        super(context,DATEBASE_NAME,null,DATEBASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RECORDS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // 添加缴费记录
    public long addRecord(String account, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ACCOUNT, account);
        values.put(COLUMN_AMOUNT, amount);
        values.put(COLUMN_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date()));
        return db.insert(TABLE_RECORDS, null, values);
    }

    // 获取所有记录
    @SuppressLint("Range")
    public List<PaymentRecord> getAllRecords() {
        List<PaymentRecord> recordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECORDS + " ORDER BY " + COLUMN_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                PaymentRecord record = new PaymentRecord();
                record.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                record.setAccount(cursor.getString(cursor.getColumnIndex(COLUMN_ACCOUNT)));
                record.setAmount(cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT)));
                record.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recordList;
    }

}

