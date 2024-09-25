package com.example.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new TollCollectionDBHelper(this);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                String phone = ((EditText) findViewById(R.id.et_phone)).getText().toString();
                String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
                String vehicleno = ((EditText) findViewById(R.id.et_vno)).getText().toString();
                String vehicletype = ((EditText) findViewById(R.id.et_vtype)).getText().toString();

                // Store user details in SQLite database
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("username", username);
                values.put("password", password);
                values.put("phone", phone);
                values.put("email", email);
                values.put("vehicleno", vehicleno);
                values.put("vehicletype", vehicletype);
                long newRowId = db.insert("users", null, values);

                if (newRowId != -1) {
                    Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error registering user", Toast.LENGTH_SHORT).show();
                }

                // Navigate to login screen
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}