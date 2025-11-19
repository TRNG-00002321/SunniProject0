import datetime
import sys
import database_modules as db

mydb = db.Database()

def login():
    while True:
        username = input("Enter username: ")
        password = input("Enter password: ")
        if mydb.validate_user(username, password):
            print(f"Welcome, {username}!")
            break
        print("Invalid username or password. Try again.")

def add_expense_report():
    while True:
        try:
            date = input("Enter date: ")
            amount = float(input("Enter amount: "))
            category = input("Enter category: ")
            desc = input("Enter description: ")
        except ValueError:
            print("Invalid input. Try again.")
        else:
            mydb.add(date, amount, category, desc)
            break

def view_expense_reports():
    mydb.view()

def edit_expense_reports():
    mydb.view()
    while True:
        try:
            index = input("Enter index of expense report: ")
            date = input("Enter date: ")
            amount = float(input("Enter amount: "))
            category = input("Enter category: ")
            desc = input("Enter description: ")
        except ValueError:
            print("Invalid input. Try again.")
        else:
            mydb.update(index, date, amount, category, desc)
            break

def close():
    mydb.close()
    sys.exit()

menu_options = [("Submit New Expense Report" , add_expense_report),
                ("View Pending Expense Reports", view_expense_reports),
                ("Edit Pending Expense Reports", edit_expense_reports),
                # ("View All Expense Reports", view_all_expense_reports),
                ("Exit", close)]

def display_menu():
    choice = 0
    chosen = False
    while not chosen:
        chosen = True
        for i, option in enumerate(menu_options):
            print(f"{i} - {option[0]}")
        try:
            choice = int(input("Select an option: "))
        except ValueError:
            print("Invalid choice. Try again.")
            chosen = False
        if choice < 0 or choice > len(menu_options):
            print("Invalid choice. Try again.")
            chosen = False
    menu_options[choice][1]()


def main():
    login()
    while True:
        display_menu()
main()