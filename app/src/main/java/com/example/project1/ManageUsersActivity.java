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

public class ManageUsersActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private ListView listViewUsers;
    private ArrayList<String> userList;
    private ArrayAdapter<String> userAdapter;
    private int selectedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        dbHelper = new TollCollectionDBHelper(this);
        listViewUsers = findViewById(R.id.list_view_users);

        // Load users when the activity starts
        loadUsers();

        Button btnAddUser = findViewById(R.id.btn_add_user);
        Button btnUpdateUser = findViewById(R.id.btn_update_user);
        Button btnDeleteUser = findViewById(R.id.btn_delete_user);

        // Add User Button
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageUsersActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        // Update User Button
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserId != -1) {
                    Intent intent = new Intent(ManageUsersActivity.this, UpdateUserActivity.class);
                    intent.putExtra("USER_ID", selectedUserId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManageUsersActivity.this, "Please select a user to update.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete User Button
        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUserId != -1) {
                    deleteUser(selectedUserId);
                    loadUsers(); // Refresh the list after deletion
                } else {
                    Toast.makeText(ManageUsersActivity.this, "Please select a user to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle user selection from ListView
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = userList.get(position);
                String[] parts = selectedUser.split(":");
                selectedUserId = Integer.parseInt(parts[0]); // Get the user ID from the selected item
                Toast.makeText(ManageUsersActivity.this, "Selected User ID: " + selectedUserId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load users from the database
    private void loadUsers() {
        userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                userList.add(id + ": " + username); // Add "id: username" to the list
            } while (cursor.moveToNext());
        }

        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listViewUsers.setAdapter(userAdapter);

        cursor.close();
    }

    // Delete the selected user from the database
    private void deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("users", "id = ?", new String[]{String.valueOf(userId)});
        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
