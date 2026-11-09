/*
Program name: BankAccount.java
Description: Models one bank account: holder, password, balance, transaction history
   and login lockout. Three wrong passwords lock the account until an admin unlocks
   it.
Author: Exanes, Evan Prens; Prudenciado, Rhona-mae P.
Date Compiled: 10/08/2026
======================================================================
Date           Description                      Programmer name
======================================================================
10/08/2026     Initial Implementation           EPPE, RMPP
11/09/2026     Added header and comments        EPPE with Claude
11/09/2026     Added RMPP initials              EPPE with Claude
======================================================================
*/

package socit.inprola.project;

import java.util.ArrayList;

/**
 * One customer account: balance, transaction history and login lockout.
 * Deposits and withdrawals return false for an invalid amount instead of throwing.
 */
public class BankAccount {
	

    private int accountNumber;
    private String accountHolder;
    // Plain text, as submitted. A real bank would store a salted hash.
    private String password;
    private double balance;

    // Each entry is a display string such as "Deposited: 50.0", not a structured record.
    private ArrayList<String> transactions;

    // The third wrong password sets locked. Only an admin reset clears it.
    private int failedAttempts;
    private boolean locked;

    // BankingAppGUI repeats this 3 in its messages, so change both together.
    private static final int MAX_ATTEMPTS = 3;

    /** Opens the account unlocked and logs the starting balance as its first transaction. */
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    /** Called after a successful login and by the admin reset. */
    public void resetFailedAttempts() {
        failedAttempts = 0;
    }

    /** Counts one wrong password and locks the account when the count reaches three. */
    public void incrementFailedAttempts() {
        failedAttempts++;

        if (failedAttempts >= MAX_ATTEMPTS) {
            locked = true;
        }
    }

    /** Adds the amount if it is above zero. Returns false and changes nothing otherwise. */
    public boolean deposit(double amount) {

        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: " + amount);
            return true;
        }

        return false;
    }

    /** Takes the amount out if it is above zero and no more than the balance. Returns false otherwise. */
    public boolean withdraw(double amount) {

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrew: " + amount);
            return true;
        }

        return false;
    }
}