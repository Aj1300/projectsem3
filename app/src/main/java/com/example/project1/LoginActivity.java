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

public class LoginActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new TollCollectionDBHelper(this);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

                // Retrieve user details from SQLite database
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                Cursor cursor = db.rawQuery(query, new String[]{username, password});

                if (cursor.moveToFirst()) {
                    String uidd = cursor.getString(cursor.getColumnIndex("id"));

                    // User exists, generate a session ID and navigate to toll collection screen
                    String sessionId = generateSessionId();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("SESSION_ID", sessionId);
                    intent.putExtra("USER_NO", uidd);

                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the register activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        Button btnAdminLogin = findViewById(R.id.btn_admin_login);
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, adminlogin.class);
                startActivity(intent);
            }
        });
    }

    private String generateSessionId() {
        // For now, just generate a random session ID
        return "SESSION_" + System.currentTimeMillis();
    }
}