package com.example.justin.mg;

/**
 * Created by Justin on 1/29/2016.
 */
public class Transaction {
    private String name;
    private int amount, id;
    private boolean decision;

    public Transaction(String name, int amount, int id, boolean decision) {
        this.name = name;
        this.amount = amount;
        this.id = id;
        this.decision = decision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }
}
