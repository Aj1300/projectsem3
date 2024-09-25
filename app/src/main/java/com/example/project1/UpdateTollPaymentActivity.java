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

public class UpdateTollPaymentActivity extends AppCompatActivity {

    private TollCollectionDBHelper dbHelper;
    private long tollPaymentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_toll_payment);

        dbHelper = new TollCollectionDBHelper(this);
        tollPaymentId = getIntent().getLongExtra("TOLL_PAYMENT_ID", -1);

        final EditText etTollName = findViewById(R.id.et_toll_name);
        final EditText etVehicleType = findViewById(R.id.et_vehicle_type);
        final EditText etAmount = findViewById(R.id.et_amount);
        Button btnUpdate = findViewById(R.id.btn_update);

        loadTollPaymentDetails(tollPaymentId, etTollName, etVehicleType, etAmount);

        // Update Button
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tollName = etTollName.getText().toString();
                String vehicleType = etVehicleType.getText().toString();
                double amount = Double.parseDouble(etAmount.getText().toString());

                updateTollPayment(tollPaymentId, tollName, vehicleType, amount);
                Toast.makeText(UpdateTollPaymentActivity.this, "Toll payment updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    // Load the toll payment details into the EditTexts
    private void loadTollPaymentDetails(long id, EditText etTollName, EditText etVehicleType, EditText etAmount) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM toll_payments WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            etTollName.setText(cursor.getString(cursor.getColumnIndex("toll_name")));
            etVehicleType.setText(cursor.getString(cursor.getColumnIndex("vehicle_type")));
            etAmount.setText(cursor.getString(cursor.getColumnIndex("amount")));
        }
        cursor.close();
    }

    // Update the toll payment in the database
    private void updateTollPayment(long id, String tollName, String vehicleType, double amount) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("toll_name", tollName);
        values.put("vehicle_type", vehicleType);
        values.put("amount", amount);
        db.update("toll_payments", values, "id = ?", new String[]{String.valueOf(id)});
    }
}
