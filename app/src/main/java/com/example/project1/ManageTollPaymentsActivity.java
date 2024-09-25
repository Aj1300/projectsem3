package com.example.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ManageTollPaymentsActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private ListView tollListView;
    private long selectedTollPaymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_toll_payments);

        dbHelper = new TollCollectionDBHelper(this);
        tollListView = findViewById(R.id.toll_list_view);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnUpdate = findViewById(R.id.btn_update);
        Button btnDelete = findViewById(R.id.btn_delete);

        loadTollPayments();

        tollListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedTollPaymentId = id; // Store the selected payment ID for update or delete
            }
        });

        // Add Button
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageTollPaymentsActivity.this, AddTollPaymentActivity.class);
                startActivity(intent);
            }
        });

        // Update Button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTollPaymentId > 0) {
                    Intent intent = new Intent(ManageTollPaymentsActivity.this, UpdateTollPaymentActivity.class);
                    intent.putExtra("TOLL_PAYMENT_ID", selectedTollPaymentId);
                    startActivity(intent);
                } else {
                    Toast.makeText(ManageTollPaymentsActivity.this, "Select a toll payment to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete Button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTollPaymentId > 0) {
                    deleteTollPayment(selectedTollPaymentId);
                    loadTollPayments();
                } else {
                    Toast.makeText(ManageTollPaymentsActivity.this, "Select a toll payment to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Load all toll payments from database and display in the list view
    private void loadTollPayments() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM toll_payments", null);

        String[] fromColumns = {"toll_name", "vehicle_type", "amount"};
        int[] toViews = {R.id.toll_name, R.id.vehicle_type, R.id.amount};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.toll_payment_item, // Custom layout for each toll payment
                cursor,
                fromColumns,
                toViews,
                0
        );

        tollListView.setAdapter(adapter);
    }

    // Delete a toll payment by ID
    private void deleteTollPayment(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("toll_payments", "id = ?", new String[]{String.valueOf(id)});
        Toast.makeText(this, "Toll payment deleted", Toast.LENGTH_SHORT).show();
    }
}
