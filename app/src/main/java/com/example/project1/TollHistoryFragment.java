package com.example.project1;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TollHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnClearHistory;
    private TollHistoryAdapter adapter;
    private SQLiteDatabase database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toll_history, container, false);

        // Initialize views
        recyclerView = view.findViewById(R.id.recycler_view_toll_history);
        btnClearHistory = view.findViewById(R.id.btn_clear_history);

        // Initialize database
        TollDatabaseHelper dbHelper = new TollDatabaseHelper(getContext());
        database = dbHelper.getReadableDatabase();

        // Load toll history
        loadTollHistory();

        // Set up Clear History button click listener
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

        return view;
    }

    private void loadTollHistory() {
        List<TollHistoryItem> tollHistoryList = new ArrayList<>();

        // Query the database to retrieve toll history
        Cursor cursor = database.query("toll_payments", new String[]{"toll_name", "amount"},
                null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tollName = cursor.getString(cursor.getColumnIndexOrThrow("toll_name"));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
                tollHistoryList.add(new TollHistoryItem(tollName, amount));
            }
            cursor.close();
        }

        // Set up RecyclerView with the adapter
        adapter = new TollHistoryAdapter(tollHistoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void showConfirmationDialog() {
        // Show a confirmation dialog before clearing history
        new AlertDialog.Builder(getContext())
                .setTitle("Clear History")
                .setMessage("Are you sure you want to clear all toll history?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        clearHistory();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void clearHistory() {
        // Delete all rows in the toll_payments table
        database.delete("toll_payments", null, null);
        Toast.makeText(getContext(), "History cleared", Toast.LENGTH_SHORT).show();

        // Reload the history to reflect the cleared state
        loadTollHistory();
    }
}