package com.example.project1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddUserActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private EditText etUsername, etPassword, etPhone, etEmail, etVehicleNo, etVehicleType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        dbHelper = new TollCollectionDBHelper(this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etVehicleNo = findViewById(R.id.et_vehicle_no);
        etVehicleType = findViewById(R.id.et_vehicle_type);
        Button btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();
        String vehicleNo = etVehicleNo.getText().toString();
        String vehicleType = etVehicleType.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("phone", phone);
        values.put("email", email);
        values.put("vehicleno", vehicleNo);
        values.put("vehicletype", vehicleType);

        db.insert("users", null, values);
        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show();
        db.close();

        finish(); // Close activity after adding user
    }
}
