package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
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

public class adminlogin extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnUserLogin;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnUserLogin = findViewById(R.id.btn_user_login);

        // Create a database connection
        db = openOrCreateDatabase("toll.db", MODE_PRIVATE, null);
        createTable();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(adminlogin.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor cursor = db.rawQuery("SELECT * FROM admin WHERE username = ? AND password = ?", new String[]{username, password});
                    if (cursor.moveToFirst()) {
                        Toast.makeText(adminlogin.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Start a new activity or fragment after successful login
                        Intent intent = new Intent(adminlogin.this, adminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(adminlogin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            }
        });

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminlogin.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT)");
    }
}