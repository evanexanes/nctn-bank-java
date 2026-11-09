# NCTN Bank

A small banking app in Java with Swing dialog boxes. Built in a two-person team in my first year of BSIT, as a Java
project for an intermediate programming course.

It covers the basics of a bank: open an account, log in, deposit, withdraw, transfer and read your
transaction history. An admin menu can unlock accounts.

## What it does
- Create an account with a starting deposit. Account numbers start at 1001.
- Log in with a password. After 3 wrong attempts the account locks, and only the admin menu can
  unlock it.
- Check balance, deposit, withdraw, transfer to another account and view the transaction list.
- Admin menu: unlock or reset an account, and view all accounts.
- Classes are split in two: `Bank` manages the accounts and `BankAccount` holds one account's
  data and rules. `BankingAppGUI` is the menus.

## Run it
Java 9 or newer (the project uses `module-info.java`).

Open the folder in Eclipse or IntelliJ and run `BankingAppGUI`, or from the command line:

```bash
javac -d out $(find src -name "*.java" ! -name module-info.java)
java -cp out socit.inprola.project.BankingAppGUI
```

The admin password in this copy is the demo value `demo-admin`.

## What I would change today
- Data lives in memory, so closing the app erases every account. It needs a file or a database.
- Passwords are compared as plain text. They should be hashed.
- The admin password was written in the code. It belongs in configuration, not source.
- The menus are numbered text boxes. A real layout with buttons and tables would be easier to use.

## Project layout

| File | Role |
|---|---|
| `src/socit/inprola/project/BankingAppGUI.java` | Entry point and menus, built from JOptionPane dialogs |
| `src/socit/inprola/project/Bank.java` | The list of accounts, and transfers between them |
| `src/socit/inprola/project/BankAccount.java` | One account: balance, history and login lockout |
| `src/module-info.java` | Module descriptor, which needs `java.desktop` for Swing |

## Notes
First-year coursework, shared as it was submitted apart from the demo admin password. A later pass added a header block and comments to every source file. The code was not changed.

## Source file headers
Every source file opens with a header block: program name, description, author, date compiled and a change history. A commit that changes a file adds a row to its history.
