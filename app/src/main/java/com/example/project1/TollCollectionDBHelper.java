package com.example.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TollCollectionDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "toll.db";
    private static final int DATABASE_VERSION = 1;

    public TollCollectionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, phone TEXT, email TEXT, vehicleno TEXT, vehicletype TEXT)");

        // Create toll payments table
        db.execSQL("CREATE TABLE toll_payments (id INTEGER PRIMARY KEY AUTOINCREMENT, toll_name TEXT, vehicle_type TEXT, amount REAL)");

        // Create admin table
        db.execSQL("CREATE TABLE admin (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

        // Add default admin
        addDefaultAdmin(db);
    }

    private void addDefaultAdmin(SQLiteDatabase db) {
        db.execSQL("INSERT INTO admin (username, password) VALUES ('admin', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS toll_payments");
        db.execSQL("DROP TABLE IF EXISTS admin");
        onCreate(db);
    }
}
