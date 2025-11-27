package com.revature.expensemanager.model;

public class Expense {
    private int id;
    private int userID;
    private double amount;
    private String description;
    private String status;
    private String date;
    private static int maxLength = 0;

    public Expense(int id, int userID, double amount, String description, String status, String date) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.date = date;
        maxLength = (description.length() > maxLength) ? description.length() : maxLength;
        maxLength = (description.length() > maxLength) ? description.length() : maxLength;
        maxLength = (description.length() > maxLength) ? description.length() : maxLength;
    }

    public int getId() {
        return id;
    }

    public int getUserID() {
        return userID;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private void updateMaxLength(String str) {
        maxLength = (str.length() > maxLength) ? str.length() : maxLength;
    }

    public static String getHeader() {
        // StringBuilder sb = new StringBuilder();
        return String.format("%" + maxLength + "s"
                + "%" + maxLength + "s"
                + "%" + maxLength + "s"
                + "%" + maxLength + "s"
                + "%" + maxLength + "s"
                + "%" + maxLength + "s",
                "ID", "User ID", "Amount", "Description", "Data");
    }

    @Override
    public String toString() {
        return String.format("%" + maxLength + "d %" + maxLength + "s", this.id, this.description);
    }

}
