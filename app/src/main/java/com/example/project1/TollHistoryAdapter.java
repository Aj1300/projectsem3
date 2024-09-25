package com.example.project1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TollHistoryAdapter extends RecyclerView.Adapter<TollHistoryAdapter.ViewHolder> {

    private List<TollHistoryItem> tollHistoryList;

    public TollHistoryAdapter(List<TollHistoryItem> tollHistoryList) {
        this.tollHistoryList = tollHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_toll_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TollHistoryItem item = tollHistoryList.get(position);
        holder.tollNameTextView.setText(item.getTollName());
        holder.amountTextView.setText("₹" + item.getAmount());
    }

    @Override
    public int getItemCount() {
        return tollHistoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tollNameTextView;
        TextView amountTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tollNameTextView = itemView.findViewById(R.id.tv_toll_name);
            amountTextView = itemView.findViewById(R.id.tv_amount);
        }
    }
}