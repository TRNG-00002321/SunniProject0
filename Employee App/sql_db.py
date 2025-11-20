import os
import pandas as pd
import sqlite3

import init_db
class Database:
    def __init__(self):
        if not os.path.exists("../expenses.db"):
            init_db.initialize()
        self.connection = sqlite3.connect('../expenses.db')
        self.cursor = self.connection.cursor()

    def validate_user(self, username, password):
        self.cursor.execute("SELECT * FROM users WHERE username = ? AND password = ?", (username, password))
        return self.cursor.fetchone()
    
    def add_expense(self, user_id, amount, description, date):
        self.cursor.execute("INSERT INTO expenses (user_id, amount, description, date) VALUES(?, ?, ?, ?)", (user_id, amount, description, date))
        expense_id = self.cursor.lastrowid        
        self.cursor.execute("INSERT INTO approvals(expense_id, status) VALUES(?, ?)", (expense_id, "PENDING"))
        self.connection.commit()
        return self.cursor.lastrowid

    def edit_expense(self, expense_id, user_id, amount, description, date):
        self.cursor.execute('''UPDATE TABLE expenses
                            SET amount=?, description=?, date=?
                            WHERE expense_id = ? AND
                            user_id = ?
                            ''', (amount, description, date, expense_id, user_id))
        self.connection.commit()
        return self.cursor.rowcount

    def delete_expense(self, user_id, expense_id):
        self.cursor.execute('DELETE FROM expenses WHERE expense_id=? AND user_id=?', (expense_id, user_id))
        return self.cursor.rowcount
    
    def view_pending_expenses(self, user_id):
        self.cursor.execute('''SELECT e.id, e.amount, e.description, e.date, a.status 
                               FROM expenses AS e INNER JOIN approvals AS a on a.expense_id = e.id 
                               WHERE e.user_id = ? AND a.status = "PENDING"''', (user_id,))
        return self.cursor.fetchall()
    
    def view_all_expenses(self, user_id):
        self.cursor.execute('''SELECT e.id, e.amount, e.description, e.date, a.status 
                               FROM expenses AS e INNER JOIN approvals AS a on a.expense_id = e.id 
                               WHERE e.user_id = ?''', (user_id,))
        return self.cursor.fetchall()

if __name__ == "__main__":
    database = Database()
    user = database.validate_user('admin', 'admin123')
    print(user)
    print(type(user[0]))
    database.add_expense(user[0], 500.00, "Client Dinner", "2025-11-20")

    expenses = database.view_pending_expenses(user[0])
    expenses = pd.DataFrame.from_records(expenses, columns=['ID', 'Amount', 'Description', 'Date', 'Status'])
    expenses.set_index('ID', inplace=True)
    print(expenses)

    database.cursor.execute("SELECT * from approvals")
    approvals = database.cursor.fetchall()
    for a in approvals:
        print(a)






