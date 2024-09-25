package com.example.project1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTollPaymentActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toll_payment);

        dbHelper = new TollCollectionDBHelper(this);

        Button btnSave = findViewById(R.id.btn_save);
        final EditText etTollName = findViewById(R.id.et_toll_name);
        final EditText etVehicleType = findViewById(R.id.et_vehicle_type);
        final EditText etAmount = findViewById(R.id.et_amount);

        // Save new toll payment
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tollName = etTollName.getText().toString();
                String vehicleType = etVehicleType.getText().toString();
                double amount = Double.parseDouble(etAmount.getText().toString());

                addTollPayment(tollName, vehicleType, amount);
                Toast.makeText(AddTollPaymentActivity.this, "Toll payment added", Toast.LENGTH_SHORT).show();
                finish(); // Go back to the main activity
            }
        });
    }

    // Add toll payment to the database
    private void addTollPayment(String tollName, String vehicleType, double amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("toll_name", tollName);
        values.put("vehicle_type", vehicleType);
        values.put("amount", amount);
        db.insert("toll_payments", null, values);
    }
}
