import datetime
import mysql
import pandas as pd
import os
import sys

# import sql_db as db
import mysql_db as mdb

def clear_console():
    if os.name == 'nt':  # For Windows
        os.system('cls')
    else:  # For Linux and macOS
        os.system('clear')
    print("Employee Expense Portal")

def validate_id(id):
    try:
        id = int(id)
    except ValueError:
        raise ValueError("Invalid ID")
    if id < 0:
        raise ValueError("Invalid ID")
    return id

def validate_date(date):
    date = date.split('-')
    if len(date) < 3:
        raise ValueError("Invalid Date Format")
    try:
        date = [int(d) for d in date]
        date = datetime.date(date[0], date[1], date[2])
    except ValueError:
        raise ValueError("Invalid Date Format")
    except IndexError:
        raise ValueError("Invalid Date Format")
    
    now = datetime.date.today()
    if date > now:
        raise ValueError("Date cannot be in the future.")
    return date

def validate_amount(amount, limit):
    amount = float(amount)
    if amount < 0:
        raise ValueError("Amount cannot be negative.")
    if amount > limit:
        raise ValueError(f"Amount cannot exceed limit: ${limit}")
    
def validate_description(description):
    if not description:
        raise ValueError("Description cannot be empty.")

class EmployeeMenu:

    def __init__(self):
        # self.db = db.Database()
        try:
            self.db = mdb.Database()
        except mysql.connector.errors.InterfaceError:
            print("Could not connect to database.")
            sys.exit()
        self.menu_options = [
                ("Exit", self.close),
                ("View Pending Expense Reports", self.view_pending),
                ("View All Expense Reports", self.view_all),
                ("Submit New Expense Report" , self.add_expense),
                ("Edit Pending Expense Reports", self.edit_expense),
                ("Delete Pending Expense Reports", self.delete_expense),
        ]
        
    def get_category(self):
        categories = self.db.get_expense_categories()
        print("Select Expense Category:")
        for i, category in enumerate(categories, start=1):
            print(f"{i} - {category[1]}")
        while True:
            try:
                choice = int(input("Enter choice: ")) - 1
                if choice < 0 or choice >= len(categories):
                    raise IndexError
            except ValueError:
                print("Invalid choice. Try again.")
            except IndexError:
                print("Invalid choice. Try again.")
            else:
                return categories[choice][0]

    def add_expense(self):
        print("Submitting New Expense Report: ")
        while True:
            try:
                date = input("Enter date (YYYY-MM-DD): ")
                date = validate_date(date)
                amount = input("Enter amount ($25.04): $")
                validate_amount(amount, 500000000)
                description = input("Enter description: ")
                validate_description(description)
                category_id = self.get_category()
            except ValueError as ve:
                clear_console()
                print("Submitting New Expense Report: ")
                print(ve, " Try again.")
            except IndexError:
                clear_console()
                print("Submitting New Expense Report: ")
                print("Invalid date format. Use YYYY-MM-DD.")
            else:
                result = self.db.add_expense(self.user[0], float(amount), description, date, category_id)
                break
        if result:
            return "Expense Successfully Added"
        return "Could Not Add Expense Try Again"

    def edit_expense(self):
        print(self.view_pending())
        print()
        while True:
            try:
                expense_id = input("Enter ID of expense report: ")
                expense_id = validate_id(expense_id)
                expense = self.db.get_pending_expense_by_id(self.user[0], expense_id)
                if(expense is None):
                    raise ValueError("Invalid ID")

                amount = input("Enter amount ($25.04) or (Enter) to skip): $")
                if amount == '':
                    amount = expense[1]
                else:
                    validate_amount(amount, 1000000000)
                
                description = input("Enter description or (Enter) to skip): ")
                if description == '':
                    description = expense[2]
                else:
                    validate_description(description)

                date = input("Enter date (YYYY-MM-DD) or (Enter) to skip): ")
                if date == '':
                    date = expense[3]
                date = validate_date(date)
            except ValueError as e:
                clear_console()
                print(self.view_pending())
                print(e)

            else:
                result = self.db.edit_expense(expense_id, self.user[0], amount, description, date)
                break
        if result:
            return "Expense Sucessfully Updated"
        return "Expense NOT Updated"

    def delete_expense(self):
        print(self.view_pending())
        print()
        while True:
            try:
                expense_id = input("Enter ID of expense report to delete: ")
                expense_id = validate_id(expense_id)
            except ValueError:
                print("Invalid input. Try again.")
            else:
                result = self.db.delete_expense(self.user[0], expense_id)
                break
        print(result)
        if result:
            return "Expense Successfully Deleted"
        return "Could Not Delete Expense. Please Try Again"
            
    def view_pending(self):
        expenses = self.db.get_pending_expenses(self.user[0])
        expenses = pd.DataFrame.from_records(expenses, columns=['ID', 'Amount', 'Description', 'Date', 'Status'])
        expenses.set_index('ID', inplace=True)
        return expenses

    def view_all(self):
        expenses = self.db.get_all_expenses(self.user[0])
        expenses = pd.DataFrame.from_records(expenses, columns=['ID', 'Amount', 'Description', 'Date', 'Status', "Comment"])
        expenses.set_index('ID', inplace=True)
        return expenses
            
    def login(self):
        clear_console()
        while True:
            username = input("Enter username: ")
            password = input("Enter password: ")
            self.user = self.db.validate_user(username, password)
            if self.user:
                clear_console()
                print(f"Welcome, {username}!")
                return
            clear_console()
            print("Invalid username or password. Try again.")

    def run(self):
        self.login()
        while True:
            self.display_menu()
    
    def display_menu(self):
        choice = -1
        chosen = False
        while not chosen:
            chosen = True
            print()
            for i, option in enumerate(self.menu_options[1:], start=1):
                print(f"{i} - {option[0]}")
            print(f"0 - {self.menu_options[0][0]}")
            try:
                choice = int(input("Select an option: "))
            except ValueError:
                clear_console()
                print("Invalid choice. Try again.")
                chosen = False
            except IndexError:
                clear_console()
                print("Invalid choice. Try again.")
                chosen = False
            if choice < 0 or choice >= len(self.menu_options):
                clear_console()
                print("Invalid choice. Try again.")
                chosen = False
        clear_console()
        result = self.menu_options[choice][1]()
        clear_console()
        print(result)
        
        
    def close(self):
        self.db.close()
        sys.exit()

if __name__ == "__main__":
    menu = EmployeeMenu()
    menu.run()
