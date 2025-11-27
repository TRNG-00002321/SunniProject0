package com.revature.expensemanager.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.revature.expensemanager.JDBC.ExpenseJDBC;
import com.revature.expensemanager.model.Expense;

public class ExpenseService {
    private static final ExpenseJDBC expenseJDBC = new ExpenseJDBC();

    public String getExpenseTable(String[] status) {
        List<String> statusList = Arrays.asList(status);
        StringBuilder sb = new StringBuilder(Expense.getHeader());
        List<Expense> expenses = expenseJDBC.getAll();
        for (Expense expense : expenses) {
            if (statusList.contains(expense.getStatus())) {
                sb.append("\n");
                sb.append(expense);
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public String getPendingExpenseTable() {
        return getExpenseTable(new String[] { "PENDING" });
    }

    private void updateStatus(int expenseID, String description, String status) {
        Optional<Expense> optionalExpense = expenseJDBC.get(expenseID);
        Expense expense;
        if (optionalExpense.isEmpty()) {
            System.out.println("Expense Not Found with Id: " + expenseID);
            return;
        }
        expense = optionalExpense.get();
        if (expense.getStatus().equals("PENDING")) {
            expense.setStatus(status);
            Calendar now = Calendar.getInstance();
            expense.setDate(
                    now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_WEEK));
            expense.setDescription(description);
        }
    }

    public void approveExpense(int expenseID, String description) {
        updateStatus(expenseID, description, "APPROVED");
    }

    public void denyExpense(int expenseID, String description) {
        updateStatus(expenseID, description, "DENIED");
    }
}
