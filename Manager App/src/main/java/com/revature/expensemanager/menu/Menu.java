package com.revature.expensemanager.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.expensemanager.service.UserService;
import com.revature.expensemanager.service.ExpenseService;
import com.revature.expensemanager.exception.UserNotFoundException;
import com.revature.expensemanager.service.ApprovalService;
import com.revature.expensemanager.service.LoginService;

public class Menu {

    private static final UserService userService = new UserService();
    private static final ExpenseService expenseService = new ExpenseService();
    private static final ApprovalService approvalService = new ApprovalService();
    private static final LoginService loginService = new LoginService();
    Scanner input = new Scanner(System.in);;

    String[] menuOptions = {
            "1. View Pending Expenses",
            "2. Approve Pending Expense",
            "3. Deny Pending Expense",
            "4. View Reports" };

    private int userID = -1;

    private void login() {
        while (userID < 0)
            System.out.print("Enter username: ");
        String username = input.nextLine();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        try {
            userID = loginService.validateLogin(username, password);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage() + " Try Again");
        }
    }

    private void displayOptions() {
        for (String option : menuOptions) {
            System.out.println(option);
        }
    }

    private int getIntegerInput() {
        boolean valid = false;
        int choice = -1;
        while (!valid) {
            System.out.print("> ");
            try {
                choice = input.nextInt();
                valid = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter an integer value from the options above.");
            } finally {
                input.nextLine();
            }
        }
        return choice;
    }

    private String getDescription() {
        String description = "";
        boolean valid = false;

        while (!valid) {
            description = getStringInput();
            valid = true;
            if (!description.isEmpty()) {
                valid = false;
                System.out.println("Description Cannot Be Empty");
            }
        }
        return description;
    }

    private String getStringInput() {
        String choice = "";
        System.out.print("> ");
        choice = input.nextLine();
        return choice;
    }

    private int chooseOption() {
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            try {
                System.out.println("Select and option: ");
                choice = getIntegerInput();
                if (choice < 0 || choice > menuOptions.length - 1)
                    throw new IllegalArgumentException();
                valid = true;
            } catch (IllegalArgumentException iae) {
                System.out.println("Invalid Selection. Enter a value from 0 to " + (menuOptions.length - 1));
            }
        }
        return choice;
    }

    public void run() {
        login();
        boolean done = false;
        int choice;

        while (!done) {
            displayOptions();
            choice = chooseOption();

            switch (choice) {
                case 1:
                    System.out.println(expenseService.getExpenseTable(new String[] { "PENDING" }));
                    break;
                case 2:
                    approveExpense();
                    break;
                case 3:
                    denyExpense();
                    break;
                case 4:
                    viewReports();
                default:
                    break;
            }
        }
    }

    private void viewReports() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewReports'");
    }

    private void denyExpense() {
        System.out.println(expenseService.getPendingExpenseTable());
        int expenseID = getIntegerInput();
        String description = getDescription();
        expenseService.approveExpense(expenseID, description);
    }

    private void approveExpense() {
        System.out.println(expenseService.getPendingExpenseTable());
        int expenseID = getIntegerInput();
        String description = getDescription();
        expenseService.approveExpense(expenseID, description);
    }
}
