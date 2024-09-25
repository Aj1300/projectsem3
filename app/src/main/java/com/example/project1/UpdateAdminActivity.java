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

public class UpdateAdminActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private EditText etUsername, etPassword;
    private int adminId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_admin);

        dbHelper = new TollCollectionDBHelper(this);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnUpdate = findViewById(R.id.btn_update);

        adminId = getIntent().getIntExtra("ADMIN_ID", -1);
        loadAdminDetails(adminId);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                updateAdmin(adminId, username, password);
                Toast.makeText(UpdateAdminActivity.this, "Admin updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close activity after updating
            }
        });
    }

    private void loadAdminDetails(int adminId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admin WHERE id = ?", new String[]{String.valueOf(adminId)});
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            etUsername.setText(username);
            etPassword.setText(password);
        }
        cursor.close();
    }

    private void updateAdmin(int adminId, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.update("admin", values, "id = ?", new String[]{String.valueOf(adminId)});
        db.close();
    }
}
