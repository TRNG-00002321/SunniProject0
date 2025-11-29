package com.revature.expensemanager.model;

public class Approval {
    private int id;
    private int expenseID;
    private String status;
    private int reviewer;
    private String comment;
    private String reviewDate;

    public Approval(int id, int expenseID, String status, int reviewer, String comment, String reviewDate) {
        this.id = id;
        this.expenseID = expenseID;
        this.status = status;
        this.reviewer = reviewer;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public int getReviewer() {
        return reviewer;
    }

    public void setReviewer(int reviewer) {
        this.reviewer = reviewer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

}
