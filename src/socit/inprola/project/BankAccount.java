package socit.inprola.project;

import java.util.ArrayList;

public class BankAccount {
	
	/*
	* Represents one account in NCTN Bank.
	* Handles balance, transactions, and login security.
	*/

    // Basic account information
    private int accountNumber;
    private String accountHolder;
    private String password;
    private double balance;

    // ArrayList to store transaction history
    private ArrayList<String> transactions;

    // Security variables
    private int failedAttempts;
    private boolean locked;

    // Final constant for maximum login attempts
    private static final int MAX_ATTEMPTS = 3;

    // Constructor
    public BankAccount(int accountNumber, String accountHolder,
                       String password, double initialBalance) {

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.password = password;
        this.balance = initialBalance;

        transactions = new ArrayList<>();

        failedAttempts = 0;
        locked = false;

        transactions.add("Account created with deposit: " + initialBalance);
    }

    // Getter methods
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public ArrayList<String> getTransactions() {
        return transactions;
    }

    // Setter methods
    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    // Check if entered password matches
    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    // Reset failed attempts after successful login
    public void resetFailedAttempts() {
        failedAttempts = 0;
    }

    // Increase failed attempts and lock if needed
    public void incrementFailedAttempts() {
        failedAttempts++;

        if (failedAttempts >= MAX_ATTEMPTS) {
            locked = true;
        }
    }

    // Deposit money
    public boolean deposit(double amount) {

        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount);
            return true;
        }

        return false;
    }

    // Withdraw money
    public boolean withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew: " + amount);
            return true;
        }

        return false;
    }
}