package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class adminlogin extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        dbHelper = new TollCollectionDBHelper(this);

        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnuser = findViewById(R.id.btn_user_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (isValidAdmin(username, password)) {
                    Intent intent = new Intent(adminlogin.this, adminActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(adminlogin.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnuser.setOnClickListener(view -> {
            Intent i = new Intent(adminlogin.this, LoginActivity.class);
            startActivity(i);
        });

    }

    private boolean isValidAdmin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admin WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.moveToFirst(); // If a match is found, the admin is valid
        cursor.close();
        return valid;
    }
}
