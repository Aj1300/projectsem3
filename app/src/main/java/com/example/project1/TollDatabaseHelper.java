package com.example.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TollDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "toll.db";
    private static final int DATABASE_VERSION = 1;

    public TollDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE toll_payments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "toll_name TEXT, " +
                "vehicle_type TEXT, " +
                "amount INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS toll_payments");
        onCreate(db);
    }
}
