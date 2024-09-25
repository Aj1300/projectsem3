package com.example.project1;

public class TollHistoryItem {

    private String tollName;
    private int amount;

    public TollHistoryItem(String tollName, int amount) {
        this.tollName = tollName;
        this.amount = amount;
    }

    public String getTollName() {
        return tollName;
    }

    public int getAmount() {
        return amount;
    }
}