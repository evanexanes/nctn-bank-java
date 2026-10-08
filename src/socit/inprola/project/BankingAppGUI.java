package socit.inprola.project;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class BankingAppGUI {
	
	/*
	* Handles user interaction using JOptionPane.
	* Demonstrates loops, switch, if...else, and input/output.
	* Bank name: NCTN Bank
	*/

    public static void main(String[] args) {

        Bank bank = new Bank();
        boolean running = true;

        // Main system loop
        while (running) {

            // Using array for menu options
            String[] mainMenu = {
                    "1. Create Account",
                    "2. Access Account",
                    "3. Admin",
                    "4. Exit"
            };

            String menuText = "Welcome To NCTN Bank\n\n";

            // Loop through array to build menu
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

    // Check if input is numeric
    private static boolean isNumeric(String input) {

        if (input == null || input.isBlank()) return false;

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Create account method
    private static void createAccount(Bank bank) {

        String name = JOptionPane.showInputDialog("Enter Account Holder Name:");
        if (name == null || name.isBlank()) return;

        String password = JOptionPane.showInputDialog("Create Password:");
        if (password == null || password.isBlank()) return;

        String depositInput = JOptionPane.showInputDialog("Enter Initial Deposit:");

        if (!isNumeric(depositInput)) {
            JOptionPane.showMessageDialog(null,
                    "Deposit must be numeric.");
            return;
        }

        double deposit = Double.parseDouble(depositInput);

        if (deposit < 0) {
            JOptionPane.showMessageDialog(null,
                    "Deposit cannot be negative.");
            return;
        }

        int accNum = bank.createAccount(name, password, deposit);

        JOptionPane.showMessageDialog(null,
                "Account Created!\nAccount Number: " + accNum);
    }

    // Access account method
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

        // Login loop
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
                    int remaining = 3 - account.getFailedAttempts();
                    JOptionPane.showMessageDialog(null,
                            "Incorrect password. Attempts left: " + remaining);
                }
            }
        }
        
        // Account menu loop
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

            // Loop through array again
            for (int i = 0; i < accountMenu.length; i++) {
                menuText += accountMenu[i] + "\n";
            }

            String choice = JOptionPane.showInputDialog(menuText);
            if (choice == null) return;
            
            // Using switch statement for account operations
            switch (choice) {

                case "1":
                    JOptionPane.showMessageDialog(null,
                            "Current Balance: " + account.getBalance());
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
    
    // Separate methods for each account operation to keep main method clean
    private static void handleDeposit(BankAccount account) {

        String input = JOptionPane.showInputDialog("Enter Deposit Amount:");

        if (!isNumeric(input)) {
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

    // Withdraw method with validation
    private static void handleWithdraw(BankAccount account) {

        String input = JOptionPane.showInputDialog("Enter Withdrawal Amount:");

        if (!isNumeric(input)) {
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
    
    // Method to display transaction history
    private static void showTransactions(BankAccount account) {

        String history = "Transaction History:\n";

        for (String record : account.getTransactions()) {
            history += record + "\n";
        }

        JOptionPane.showMessageDialog(null, history);
    }

    // Method to handle fund transfer between accounts
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

        if (!isNumeric(amountInput)) {
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

    // Admin access method with password protection and a feature to view all accounts
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
                    // Existing unlock/reset account feature
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
                    // View all accounts with their numbers and holder names
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