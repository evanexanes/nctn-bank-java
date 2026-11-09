/*
Program name: BankingAppGUI.java
Description: Entry point and menus for NCTN Bank. JOptionPane dialogs drive account
   creation, login, deposit, withdrawal, transfer, transaction history and the admin
   menu.
Author: Exanes, Evan Prens; Prudenciado, Rhona-mae P.
Date Compiled: 10/08/2026
======================================================================
Date           Description                      Programmer name
======================================================================
10/08/2026     Initial Implementation           EPPE, RMPP
11/09/2026     Added header and comments        EPPE
11/09/2026     Amounts accept decimals          EPPE
11/09/2026     Added RMPP initials              EPPE
11/09/2026     Added method comments            EPPE
======================================================================
*/

package socit.inprola.project;

import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Entry point and menus for NCTN Bank, built from JOptionPane dialogs.
 * Cancel steps back one menu. On the main menu it ends the program.
 */
public class BankingAppGUI {
	

    /** Program entry point. Shows the main menu (create, access, admin, exit) until the user chooses Exit or closes the dialog. */
    public static void main(String[] args) {

        Bank bank = new Bank();
        boolean running = true;

        while (running) {

            String[] mainMenu = {
                    "1. Create Account",
                    "2. Access Account",
                    "3. Admin",
                    "4. Exit"
            };

            String menuText = "Welcome To NCTN Bank\n\n";

            for (int i = 0; i < mainMenu.length; i++) {
                menuText += mainMenu[i] + "\n";
            }

            String choice = JOptionPane.showInputDialog(menuText);

            if (choice == null) break;

            switch (choice) {

                case "1":
                    createAccount(bank);
                    break;

                case "2":
                    accessAccount(bank);
                    break;

                case "3":
                    adminAccess(bank);
                    break;

                case "4":
                    running = false;
                    JOptionPane.showMessageDialog(null,
                            "Thank You For Banking With NCTN Bank.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Invalid Option.");
            }
        }
    }

    /**
     * True for a string of one to nine digits, which always fits in an int. Used for account numbers.
     */
    private static boolean isNumeric(String input) {

        if (input == null || input.isBlank()) return false;

        // Nine digits at most, so Integer.parseInt cannot overflow.
        if (input.length() > 9) return false;

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * True for a money amount: an optional minus sign, up to twelve digits and at most two decimal
     * places, such as "50", "12.5" or "-3". The sign is accepted so callers can say why it is refused.
     */
    private static boolean isAmount(String input) {

        return input != null && input.matches("-?\\d{1,12}(\\.\\d{1,2})?");
    }

    /** Asks for a holder name, a password and a whole-number deposit, then opens the account. */
    private static void createAccount(Bank bank) {

        String name = JOptionPane.showInputDialog("Enter Account Holder Name:");
        if (name == null || name.isBlank()) return;

        String password = JOptionPane.showInputDialog("Create Password:");
        if (password == null || password.isBlank()) return;

        String depositInput = JOptionPane.showInputDialog("Enter Initial Deposit:");

        if (!isAmount(depositInput)) {
            JOptionPane.showMessageDialog(null,
                    "Deposit must be numeric.");
            return;
        }

        double deposit = Double.parseDouble(depositInput);

        // isAmount lets a minus sign through so a negative deposit gets its own message. The text is
        // checked, not the number, because "-0" parses to a negative zero that "< 0" would miss.
        if (depositInput.startsWith("-")) {
            JOptionPane.showMessageDialog(null,
                    "Deposit cannot be negative.");
            return;
        }

        int accNum = bank.createAccount(name, password, deposit);

        JOptionPane.showMessageDialog(null,
                "Account Created!\nAccount Number: " + accNum);
    }

    /**
     * Logs in, then runs the account menu until the user logs out. Three wrong passwords lock the
     * account, and the count carries over between visits until a correct login resets it.
     */
    private static void accessAccount(Bank bank) {

        String accInput = JOptionPane.showInputDialog("Enter Account Number:");

        if (!isNumeric(accInput)) {
            JOptionPane.showMessageDialog(null,
                    "Account number must be numeric.");
            return;
        }

        int accNum = Integer.parseInt(accInput);

        BankAccount account = bank.findAccount(accNum);

        if (account == null) {
            JOptionPane.showMessageDialog(null,
                    "Account not found.");
            return;
        }

        if (account.isLocked()) {
            JOptionPane.showMessageDialog(null,
                    "Account is locked. Please contact admin.");
            return;
        }

        boolean loggedIn = false;

        while (!loggedIn && !account.isLocked()) {

            String password = JOptionPane.showInputDialog("Enter Password:");
            if (password == null) return;

            if (account.checkPassword(password)) {

                account.resetFailedAttempts();
                loggedIn = true;

            } else {

                account.incrementFailedAttempts();

                if (account.isLocked()) {
                    JOptionPane.showMessageDialog(null,
                            "Account locked after 3 failed attempts.");
                    return;
                } else {
                    // 3 is MAX_ATTEMPTS from BankAccount.
                    int remaining = 3 - account.getFailedAttempts();
                    JOptionPane.showMessageDialog(null,
                            "Incorrect password. Attempts left: " + remaining);
                }
            }
        }
        
        boolean insideMenu = true;

        while (insideMenu) {

            String[] accountMenu = {
                    "1. Check Balance",
                    "2. Deposit",
                    "3. Withdraw",
                    "4. View Transactions",
                    "5. Transfer Funds",
                    "6. Logout"
            };

            String menuText = "Account Menu\n";

            for (int i = 0; i < accountMenu.length; i++) {
                menuText += accountMenu[i] + "\n";
            }

            String choice = JOptionPane.showInputDialog(menuText);
            if (choice == null) return;
            
            switch (choice) {

                case "1":
                    JOptionPane.showMessageDialog(null,
                            String.format("Current Balance: %.2f", account.getBalance()));
                    break;

                case "2":
                    handleDeposit(account);
                    break;

                case "3":
                    handleWithdraw(account);
                    break;

                case "4":
                    showTransactions(account);
                    break;

                case "5":
                    handleTransfer(bank, account);
                    break;

                case "6":
                    insideMenu = false;
                    JOptionPane.showMessageDialog(null,
                            "Logged Out.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Invalid Option.");
            }
        }
    }
    
    // Each account operation gets its own method so accessAccount stays a menu loop.
    private static void handleDeposit(BankAccount account) {

        String input = JOptionPane.showInputDialog("Enter Deposit Amount:");

        if (!isAmount(input)) {
            JOptionPane.showMessageDialog(null,
                    "Amount must be numeric.");
            return;
        }

        double amount = Double.parseDouble(input);

        if (account.deposit(amount)) {
            JOptionPane.showMessageDialog(null,
                    "Deposit successful.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Invalid deposit amount.");
        }
    }

    /** Asks for an amount and withdraws it. Rejects a non-numeric amount, a zero or negative one, and one larger than the balance. */
    private static void handleWithdraw(BankAccount account) {

        String input = JOptionPane.showInputDialog("Enter Withdrawal Amount:");

        if (!isAmount(input)) {
            JOptionPane.showMessageDialog(null,
                    "Amount must be numeric.");
            return;
        }

        double amount = Double.parseDouble(input);

        if (account.withdraw(amount)) {
            JOptionPane.showMessageDialog(null,
                    "Withdrawal successful.");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Invalid amount or insufficient balance.");
        }
    }
    
    /** Shows the account's transaction history in one dialog. */
    private static void showTransactions(BankAccount account) {

        String history = "Transaction History:\n";

        for (String record : account.getTransactions()) {
            history += record + "\n";
        }

        JOptionPane.showMessageDialog(null, history);
    }

    // Bank.transfer returns one false for a missing account and for too little money,
    // so the message cannot say which.
    private static void handleTransfer(Bank bank, BankAccount sender) {

        String toAccInput = JOptionPane.showInputDialog(
                "Enter Recipient Account Number:");

        if (!isNumeric(toAccInput)) {
            JOptionPane.showMessageDialog(null,
                    "Account number must be numeric.");
            return;
        }

        int toAcc = Integer.parseInt(toAccInput);

        String amountInput = JOptionPane.showInputDialog(
                "Enter Transfer Amount:");

        if (!isAmount(amountInput)) {
            JOptionPane.showMessageDialog(null,
                    "Amount must be numeric.");
            return;
        }

        double amount = Double.parseDouble(amountInput);

        if (bank.transfer(sender.getAccountNumber(), toAcc, amount)) {
            JOptionPane.showMessageDialog(null,
                    "Transfer successful!");
        } else {
            JOptionPane.showMessageDialog(null,
                    "Transfer failed.");
        }
    }

    /**
     * Admin menu: unlock and reset one account, or list all of them. The password is the demo
     * value "demo-admin" written into the code.
     */
    private static void adminAccess(Bank bank) {

        String adminPassword = JOptionPane.showInputDialog("Enter Admin Password:");
        if (adminPassword == null) return;

        if (!adminPassword.equals("demo-admin")) {
            JOptionPane.showMessageDialog(null,
                    "Incorrect admin password.");
            return;
        }

        boolean adminMenu = true;

        while (adminMenu) {

            String[] adminOptions = {
                    "1. Unlock/Reset Account",
                    "2. View All Accounts",
                    "3. Exit Admin Menu"
            };

            String menuText = "Admin Menu:\n";

            for (int i = 0; i < adminOptions.length; i++) {
                menuText += adminOptions[i] + "\n";
            }

            String choice = JOptionPane.showInputDialog(menuText);
            if (choice == null) return;

            switch (choice) {

                case "1":
                    // Unlocks the first account with this holder name, then offers a new password.
                    String username = JOptionPane.showInputDialog("Enter Account Holder Name:");
                    if (username == null || username.isBlank()) break;

                    BankAccount account = bank.findAccountByName(username);

                    if (account == null) {
                        JOptionPane.showMessageDialog(null,
                                "Account not found.");
                        break;
                    }

                    account.setLocked(false);
                    account.resetFailedAttempts();

                    JOptionPane.showMessageDialog(null,
                            "Account unlocked.\nAccount Number: " + account.getAccountNumber());

                    String newPassword = JOptionPane.showInputDialog("Enter New Password:");
                    if (newPassword == null || newPassword.isBlank()) break;

                    account.setPassword(newPassword);

                    JOptionPane.showMessageDialog(null,
                            "Password updated successfully.");
                    break;

                case "2":
                    ArrayList<BankAccount> accounts = bank.getAccounts();
                    StringBuilder allAccounts = new StringBuilder("List of Accounts:\n\n");

                    if (accounts.isEmpty()) {
                        allAccounts.append("No accounts available.\n");
                    } else {
                        for (BankAccount acc : accounts) {
                            allAccounts.append("Account Number: ")
                                    .append(acc.getAccountNumber())
                                    .append(" | Name: ")
                                    .append(acc.getAccountHolder())
                                    .append("\n");
                        }
                    }

                    JOptionPane.showMessageDialog(null, allAccounts.toString());
                    break;

                case "3":
                    adminMenu = false;
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid Option.");
            }
        }
    }
}