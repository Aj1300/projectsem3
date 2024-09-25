package com.example.project1;

import android.content.ContentValues;
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
        // Create the admin table
        String createAdminTable = "CREATE TABLE admin (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL)";
        db.execSQL(createAdminTable);

        // Insert the default admin (username: admin, password: admin)
        insertDefaultAdmin(db);

        // Other table creation queries (e.g., users, toll_payments, etc.)
        String createUsersTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "phone TEXT, " +
                "email TEXT, " +
                "vehicleno TEXT, " +
                "vehicletype TEXT)";
        db.execSQL(createUsersTable);

        String createTollPaymentsTable = "CREATE TABLE toll_payments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "toll_name TEXT, " +
                "vehicle_type TEXT, " +
                "amount REAL)";
        db.execSQL(createTollPaymentsTable);
    }

    // Method to insert default admin
    private void insertDefaultAdmin(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("username", "admin");
        values.put("password", "admin");
        long result = db.insert("admin", null, values);

        if (result != -1) {
            // Log or show a Toast message if the admin is successfully inserted
            System.out.println("Default admin inserted successfully!");
        } else {
            System.out.println("Failed to insert default admin.");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if they exist
        db.execSQL("DROP TABLE IF EXISTS admin");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS toll_payments");

        // Recreate tables
        onCreate(db);
    }
}
