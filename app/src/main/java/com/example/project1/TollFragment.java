package com.example.project1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TollFragment extends Fragment {

    private EditText etTollName;
    private RadioGroup rgVehicleType;
    private Button btnPay;
    private SQLiteDatabase database;
    private String userno;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toll, container, false);

        // Retrieve vehicle number passed from MainActivity
        Bundle args = getArguments();
        userno = args != null ? args.getString("USER_NO") : "Unknown user";

        // Initialize views
        etTollName = view.findViewById(R.id.et_toll_name);
        rgVehicleType = view.findViewById(R.id.rg_vehicle_type);
        btnPay = view.findViewById(R.id.btn_pay);

        // Initialize database
        TollCollectionDBHelper dbHelper = new TollCollectionDBHelper(getContext());
        database = dbHelper.getWritableDatabase();

        // Set up Pay button click listener
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePayment();
            }
        });

        return view;
    }

    private void handlePayment() {
        String tollName = etTollName.getText().toString().trim();
        int selectedVehicleId = rgVehicleType.getCheckedRadioButtonId();
        int amount = 0;

        // Determine the amount based on the selected vehicle type
        if (selectedVehicleId == R.id.rb_two_three_wheeler) {
            amount = 20;
        } else if (selectedVehicleId == R.id.rb_four_wheeler) {
            amount = 40;
        } else if (selectedVehicleId == R.id.rb_six_wheeler) {
            amount = 50;
        }

        if (!tollName.isEmpty() && amount > 0) {
            // Save data to the database
            ContentValues values = new ContentValues();
            values.put("toll_name", tollName);
            values.put("vehicle_type", getVehicleTypeString(selectedVehicleId));
            values.put("amount", amount);
            values.put("uid", userno);  // Save the vehicle number
            database.insert("toll_payments", null, values);

            Toast.makeText(getContext(), "Payment successful! ₹" + amount, Toast.LENGTH_SHORT).show();

            // Clear inputs
            etTollName.setText("");
            rgVehicleType.clearCheck();
        } else {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private String getVehicleTypeString(int id) {
        if (id == R.id.rb_two_three_wheeler) return "2 & 3 Wheeler";
        if (id == R.id.rb_four_wheeler) return "4 Wheeler";
        if (id == R.id.rb_six_wheeler) return "6 Wheeler";
        return "Unknown";
    }
}