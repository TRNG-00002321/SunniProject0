package com.revature.expensemanager.model;

public class Approval {
    private int id;
    private int expenseID;
    private String status;
    private int reviewerID;
    private String comment;
    private String reviewDate;

    public Approval(int id, int expenseID, String status, int reviewerID, String comment, String reviewDate) {
        this.id = id;
        this.expenseID = expenseID;
        this.status = status;
        this.reviewerID = reviewerID;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getId() {
        return id;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public String getStatus() {
        return status;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public String getComment() {
        return comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

}
