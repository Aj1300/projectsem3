package com.example.project1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private EditText etUsername, etPassword, etPhone, etEmail, etVehicleNo;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        dbHelper = new TollCollectionDBHelper(this);
        userId = getIntent().getIntExtra("USER_ID", -1);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etVehicleNo = findViewById(R.id.et_vehicle_no);


        loadUserDetails(userId);

        Button btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void loadUserDetails(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            etUsername.setText(cursor.getString(cursor.getColumnIndex("username")));
            etPassword.setText(cursor.getString(cursor.getColumnIndex("password")));
            etPhone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            etEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
            etVehicleNo.setText(cursor.getString(cursor.getColumnIndex("vehicleno")));
        }
        cursor.close();
    }

    private void updateUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String vehicleNo = etVehicleNo.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("phone", phone);
        values.put("email", email);
        values.put("vehicleno", vehicleNo);

        db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});
        Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show();
        db.close();

        finish(); // Close activity after updating user
    }
}
