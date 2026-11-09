/*
Program name: Bank.java
Description: Holds every account in NCTN Bank in an ArrayList and creates, finds and
   transfers between them. Account numbers start at 1001 and count up. Nothing is
   saved to disk, so closing the app erases the accounts.
Author: Exanes, Evan Prens (two-person team project)
Date Compiled: 10/08/2026
======================================================================
Date           Description                      Programmer name
======================================================================
10/08/2026     Initial Implementation           EPPE
11/09/2026     Added header and comments        EPPE
======================================================================
*/

package socit.inprola.project;

import java.util.ArrayList;

/**
 * Holds every account in NCTN Bank and handles the operations that involve more than one.
 * Course demo of ArrayList, loops and composition. Accounts live in memory only.
 */
public class Bank {
	
    private ArrayList<BankAccount> accounts;

    // Next number to hand out. The first account is 1001.
    private int nextAccountNumber;

    public Bank() {

        accounts = new ArrayList<>();
        nextAccountNumber = 1001;
    }

    /** Opens an account and returns its number. The menu code validates the input before calling this. */
    public int createAccount(String name, String password, double initialDeposit) {

        BankAccount account = new BankAccount(
                nextAccountNumber, name, password, initialDeposit);

        accounts.add(account);

        nextAccountNumber++;

        return account.getAccountNumber();
    }

    /** Returns the account with this number, or null if there is none. */
    public BankAccount findAccount(int accountNumber) {

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber() == accountNumber) {
                return accounts.get(i);
            }
        }

        return null;
    }

    /** Returns the first account whose holder name matches, ignoring case, or null. Names are not unique. */
    public BankAccount findAccountByName(String name) {

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountHolder()
                    .equalsIgnoreCase(name)) {

                return accounts.get(i);
            }
        }

        return null;
    }

    /**
     * Moves money between two accounts. Returns false if either account is missing or the sender
     * cannot cover the amount. Transferring to the same account succeeds and nets to zero.
     */
    public boolean transfer(int fromAcc, int toAcc, double amount) {

        BankAccount sender = null;
        BankAccount receiver = null;

        // One pass finds both accounts.
        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber() == fromAcc) {
                sender = accounts.get(i);
            }

            if (accounts.get(i).getAccountNumber() == toAcc) {
                receiver = accounts.get(i);
            }
        }

        if (sender != null && receiver != null) {

            // The history entries say "Withdrew" and "Deposited" and do not name the other account.
            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                return true;
            }
        }

        return false;
    }

    /** Returns the live list, not a copy. Only the admin menu reads it. */
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}