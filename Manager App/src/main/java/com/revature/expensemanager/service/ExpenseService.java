package com.revature.expensemanager.service;

import java.util.Arrays;
import java.util.List;

import com.revature.expensemanager.JDBC.ExpenseJDBC;
import com.revature.expensemanager.model.Expense;

public class ExpenseService {
    private static final ExpenseJDBC expenseJDBC = new ExpenseJDBC();

    public String getExpenseTable() {
        StringBuilder sb = new StringBuilder();
        List<Expense> expenses = expenseJDBC.getPendingExpenses();
        for (Expense expense : expenses) {
            sb.append("\n");
            sb.append(expense);
        }
        sb.append("\n");
        return Expense.getHeader() + "\n" + sb.toString();
    }
}
