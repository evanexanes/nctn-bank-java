<div align="center">

# NCTN Bank

**A small banking app in Java with Swing dialog boxes: accounts, transfers, a transaction history and an admin lockout.**

![Java 9+](https://img.shields.io/badge/Java-9%2B-ED8B00?logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Swing%20JOptionPane-2d4a7c)
![Course](https://img.shields.io/badge/Course-Intermediate%20Programming-555555)
[![License: MIT](https://img.shields.io/badge/License-MIT-2d4a7c.svg)](LICENSE)

</div>

## Contents
- [About](#about)
- [Features](#features)
- [Run it](#run-it)
- [Project structure](#project-structure)
- [What I would change today](#what-i-would-change-today)
- [Changes after submission](#changes-after-submission)
- [Source file headers](#source-file-headers)
- [License](#license)
- [Authors](#authors)

## About
NCTN Bank covers the basics of a bank: open an account, log in, deposit, withdraw, transfer and read your transaction history. An admin menu can unlock accounts. Two first-year BSIT students built it as a team project for an intermediate programming course.

## Features
- **Accounts.** Create one with a starting deposit. Account numbers start at 1001, and amounts take up to two decimals.
- **Login with lockout.** Log in with a password. After 3 wrong attempts the account locks, and only the admin menu can unlock it.
- **Everyday banking.** Check the balance, deposit, withdraw, transfer to another account and view the transaction list.
- **Admin menu.** Unlock or reset an account, and view all accounts.
- **Two-class design.** `Bank` manages the accounts, `BankAccount` holds one account's data and rules, and `BankingAppGUI` is the menus.

## Run it
Java 9 or newer, because the project uses `module-info.java`.

Open the folder in Eclipse or IntelliJ and run `BankingAppGUI`, or build from the command line:

```bash
javac -d out $(find src -name "*.java" ! -name module-info.java)
java -cp out socit.inprola.project.BankingAppGUI
```

The admin password in this copy is the demo value `demo-admin`.

## Project structure

| File | Role |
|---|---|
| `src/socit/inprola/project/BankingAppGUI.java` | Entry point and menus, built from JOptionPane dialogs |
| `src/socit/inprola/project/Bank.java` | The list of accounts, and transfers between them |
| `src/socit/inprola/project/BankAccount.java` | One account: balance, history and login lockout |
| `src/module-info.java` | Module descriptor, which needs `java.desktop` for Swing |

## What I would change today
- Data lives in memory, so closing the app erases every account. It needs a file or a database.
- Passwords are compared as plain text. They should be hashed.
- Money is stored as a `double`. A real bank would use `BigDecimal` or whole centavos, because binary floating point cannot hold 0.1 exactly.
- The admin password was written in the code. It belongs in configuration, not source.
- The menus are numbered text boxes. A real layout with buttons and tables would be easier to use.

## Changes after submission
The code is first-year coursework, shared as it was submitted apart from the demo admin password and these changes:
- A header block and comments on every source file.
- Amounts accept up to two decimals. Before, only whole numbers worked, and a negative deposit got the wrong message.
- The balance prints with two decimals.
- An account number longer than nine digits is refused. Before, it threw an error.

## Source file headers
Every source file opens with the block below. A commit that changes a file adds a row to its history table.

```text
Program name: <file name>
Description: <what the file does>
Author: Exanes, Evan Prens; Prudenciado, Rhona-mae P.
Date Compiled: MM/DD/YYYY
======================================================================
Date           Description                      Programmer name
======================================================================
MM/DD/YYYY     Initial Implementation           EPPE, RMPP
======================================================================
```

| Initials | Person |
|---|---|
| `EPPE` | Evan Prens P. Exanes |
| `RMPP` | Rhona-mae P. Prudenciado |
| `with Claude` | Added after the initials when the change was written with AI help |

## License
Released under the [MIT License](LICENSE).

## Authors
- **Evan Prens P. Exanes** · [GitHub](https://github.com/evanexanes) · [Portfolio](https://evanexanes.github.io/)
- **Rhona-mae P. Prudenciado**

See the [case study](https://evanexanes.github.io/10-case-study-nctn-bank.html) on the portfolio.
