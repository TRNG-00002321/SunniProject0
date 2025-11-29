package com.revature.expensemanager.service;

import java.util.Calendar;
import java.util.Optional;

import com.revature.expensemanager.JDBC.ApprovalJDBC;
import com.revature.expensemanager.model.Approval;

public class ApprovalService {
    private static final ApprovalJDBC approvalJDBC = new ApprovalJDBC();

    private void updateStatus(int expenseID, int reviewerID, String comment, String status) {
        Approval approval = approvalJDBC.getByExpenseID(expenseID);

        if (approval.getStatus().equals("PENDING")) {
            approval.setStatus(status);
            approval.setReviewer(reviewerID);
            Calendar now = Calendar.getInstance();
            approval.setReviewDate(
                    now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_WEEK));
            approval.setComment(comment);
        }
        approvalJDBC.update(approval);
    }

    public void approveExpense(int expenseID, int reviewerID, String comment) {
        updateStatus(expenseID, reviewerID, comment, "APPROVED");
    }

    public void denyExpense(int expenseID, int reviewerID, String comment) {
        updateStatus(expenseID, reviewerID, comment, "DENIED");
    }
}
