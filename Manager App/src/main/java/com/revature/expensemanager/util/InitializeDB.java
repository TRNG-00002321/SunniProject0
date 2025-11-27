package com.revature.expensemanager.util;

import com.revature.expensemanager.model.User;
import com.revature.expensemanager.service.UserService;

public class InitializeDB {
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        userService.addUser(new User("admin", "password", "MANAGER"));
        userService.addUser(new User("alice", "wonderland", "EMPLOYEE"));
        userService.addUser(new User("bob", "builder", "EMPLOYEE"));
    }

}
