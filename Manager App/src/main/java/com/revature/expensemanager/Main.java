package com.revature.expensemanager;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.expensemanager.menu.Menu;

public class Main {

    private static int getUserChoice() {
        Scanner input = new Scanner(System.in);
        int choice = -1;
        boolean valid = false;
        System.out.println("Select and option: ");
        while (!valid) {
            System.out.print("> ");
            try {
                choice = input.nextInt();
                if (choice < 0 || choice > 5)
                    throw new IllegalArgumentException();
                valid = true;
            } catch (InputMismatchException ime) {
                System.out.println("Please enter an integer from the options above.");
            } catch (IllegalArgumentException iae) {
                System.out.println("Invalid Selection. ");
            } finally {
                input.nextLine();
            }
        }
        return choice;
    }

    public static void main(String[] args) {
        // System.out.println(DbConnection.class.getClassLoader().getResource("expensedb.properties"));

        Menu menu = new Menu();
        menu.run();
    }

}
