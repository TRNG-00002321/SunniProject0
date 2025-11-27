package com.revature.expensemanager.service;

import java.util.List;

import com.revature.expensemanager.JDBC.UserJDBC;
import com.revature.expensemanager.exception.UserNotFoundException;
import com.revature.expensemanager.model.User;

public class LoginService {
    // private static final Connection connection = DbConnection.dbConnection();
    private static final UserJDBC userJDBC = new UserJDBC();

    public int validateLogin(String username, String password) throws UserNotFoundException {
        List<User> users = userJDBC.getAll();
        for (User user : users) {
            if (user.getUsername() == username && user.getPassword() == password && user.getId().isPresent())
                return user.getId().get();
        }
        throw new UserNotFoundException("Incorrect username or password");
    }
}
