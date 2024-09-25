package com.example.project1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class adminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnManageUsers = findViewById(R.id.btn_manage_users);
        Button btnManageTollPayments = findViewById(R.id.btn_manage_toll_payments);
        Button btnManageAdmins = findViewById(R.id.btn_manage_admins);

        // Manage Users Button
        btnManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, ManageUsersActivity.class);
                startActivity(intent);
            }
        });

        // Manage Toll Payments Button
        btnManageTollPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, ManageTollPaymentsActivity.class);
                startActivity(intent);
            }
        });

        // Manage Admins Button
        btnManageAdmins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, ManageAdminsActivity.class);
                startActivity(intent);
            }
        });
    }
}