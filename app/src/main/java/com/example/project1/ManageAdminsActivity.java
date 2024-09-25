package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageAdminsActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private ListView adminListView;
    private ArrayList<String> adminList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        dbHelper = new TollCollectionDBHelper(this);
        adminListView = findViewById(R.id.admin_list_view);
        adminList = new ArrayList<>();

        Button btnAddAdmin = findViewById(R.id.btn_add_admin);
        Button btnUpdateAdmin = findViewById(R.id.btn_update_admin);
        Button btnDeleteAdmin = findViewById(R.id.btn_delete_admin);

        // Add Admin Button
        btnAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageAdminsActivity.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });

        // Update Admin Button
        btnUpdateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement a way to select an admin for updating
                int selectedAdminId = getSelectedAdminId(); // Placeholder for actual selection logic
                if (selectedAdminId != -1) {
                    Intent intent = new Intent(ManageAdminsActivity.this, UpdateAdminActivity.class);
                    intent.putExtra("ADMIN_ID", selectedAdminId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManageAdminsActivity.this, "Select an admin to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete Admin Button
        btnDeleteAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedAdminId = getSelectedAdminId(); // Placeholder for actual selection logic
                if (selectedAdminId != -1) {
                    deleteAdmin(selectedAdminId);
                    Toast.makeText(ManageAdminsActivity.this, "Admin deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManageAdminsActivity.this, "Select an admin to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load initial admin data
        loadAdmins();
    }

    private void loadAdmins() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM admin", null);
        adminList.clear();
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            adminList.add(username);
        }
        cursor.close();
        // You can set this list to a ListView or RecyclerView
    }

    private void deleteAdmin(int adminId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("admin", "id = ?", new String[]{String.valueOf(adminId)});
        db.close();
    }

    private int getSelectedAdminId() {
        // Implement logic to get selected admin ID from the ListView or any other selection method
        return -1; // Placeholder return
    }
}
