package com.revature.expensemanager.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.expensemanager.dao.Dao;
import com.revature.expensemanager.model.Expense;
import com.revature.expensemanager.model.User;
import com.revature.expensemanager.util.DbConnection;

public class ExpenseJDBC implements Dao<Expense> {

    Connection connection = DbConnection.dbConnection();

    @Override
    public Optional<Expense> get(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Expense> getAll() {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses;";
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                expenses.add(new Expense(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("description"),
                        resultSet.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return expenses;
    }

    @Override
    public void save(Expense t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Expense t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Expense t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public List<Expense> getPendingExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expenses LEFT JOIN approvals ON (expenses.id = approvals.expense_id) WHERE approvals.status=\"PENDING\";";
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                expenses.add(new Expense(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("description"),
                        resultSet.getString("date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return expenses;
    }
}
