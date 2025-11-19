import sqlite3

class Database:
    def __init__(self):
        self.connection = sqlite3.connect('../expenses.db')
        self.cursor = self.connection.cursor()

    # def validate_user(self, username, password):
    # def add_expense(self, user_id, amount, description, date):
    # def edit_expense(self, user_id, amount, description, date):
    # def delete_expense(self, user_id, expense_id):
    # def view_pending_expenses(self, user_id):
    # def view_all_expenses(self):

if __name__ == '__main__':
    connection = sqlite3.connect('../expenses.db')
    cursor = connection.cursor()
    cursor.execute('''
                    CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username TEXT NOT NULL UNIQUE,
                        password TEXT NOT NULL,
                        role TEXT CHECK(role in ('MANAGER', 'EMPLOYEE')));
                    ''')
    cursor.execute('''
                    CREATE TABLE IF NOT EXISTS expenses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id INTEGER NOT NULL,
                        amount REAL NOT NULL,
                        description TEXT NOT NULL,
                        date TEXT NOT NULL);
                        FOREIGN KEY (user_id) REFERENCES users(id)
                    );
                    ''')
    cursor.execute('''
                    CREATE TABLE IF NOT EXISTS approvals (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        expense_id INTEGER NOT NULL,
                        status TEXT CHECK(status in ('PENDING', 'APPROVED', 'DENIED')),
                        reviewer INTEGER,
                        comment TEXT,
                        review_date TEXT,
                    ''')

    cursor.execute('''INSERT INTO users (username, password, role) VALUES (?, ?, ?);''',
                   ("admin", "admin123", "MANAGER"))
    cursor.execute('''INSERT INTO users (username, password, role) VALUES (?, ?, ?);''',
                   ("alice", "wonderland", "EMPLOYEE"))


    cursor.execute('''select * from users;''')
    print(cursor.fetchall())
    cursor.execute("SELECT name FROM sqlite_master WHERE type='table';")
    print(cursor.fetchall())