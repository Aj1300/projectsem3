package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ManageAdminsActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private ListView adminListView;
    private ArrayList<String> adminList;
    private ArrayAdapter<String> adminAdapter;
    private int selectedAdminId = -1; // To store the selected admin's ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admins);

        dbHelper = new TollCollectionDBHelper(this);
        adminListView = findViewById(R.id.admin_list_view);
        adminList = new ArrayList<>();

        adminAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, adminList);
        adminListView.setAdapter(adminAdapter);
        adminListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // Enable single choice mode

        Button btnAddAdmin = findViewById(R.id.btn_add_admin);
        Button btnUpdateAdmin = findViewById(R.id.btn_update_admin);
        Button btnDeleteAdmin = findViewById(R.id.btn_delete_admin);

        // Add Admin Button
        btnAddAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(ManageAdminsActivity.this, AddAdminActivity.class);
            startActivity(intent);
        });

        // Update Admin Button
        btnUpdateAdmin.setOnClickListener(v -> {
            if (selectedAdminId != -1) {
                Intent intent = new Intent(ManageAdminsActivity.this, UpdateAdminActivity.class);
                intent.putExtra("ADMIN_ID", selectedAdminId);
                startActivity(intent);
            } else {
                Toast.makeText(ManageAdminsActivity.this, "Select an admin to update", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete Admin Button
        btnDeleteAdmin.setOnClickListener(v -> {
            if (selectedAdminId != -1) {
                deleteAdmin(selectedAdminId);
                Toast.makeText(ManageAdminsActivity.this, "Admin deleted successfully", Toast.LENGTH_SHORT).show();
                loadAdmins(); // Reload the list after deletion
                selectedAdminId = -1; // Reset selection
            } else {
                Toast.makeText(ManageAdminsActivity.this, "Select an admin to delete", Toast.LENGTH_SHORT).show();
            }
        });

        // Load initial admin data
        loadAdmins();

        // Set item click listener to handle admin selection
        adminListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedAdminId = getAdminIdByUsername(adminList.get(position));
            if (selectedAdminId != -1) {
                Toast.makeText(ManageAdminsActivity.this, "Selected Admin: " + adminList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
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
        adminAdapter.notifyDataSetChanged(); // Notify the adapter about the new data
    }

    private void deleteAdmin(int adminId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("admin", "id = ?", new String[]{String.valueOf(adminId)});
        db.close();
    }

    private int getAdminIdByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM admin WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
            return id;
        }
        cursor.close();
        return -1; // Admin not found
    }
}
