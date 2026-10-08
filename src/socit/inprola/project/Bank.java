package socit.inprola.project;

import java.util.ArrayList;

public class Bank {
	
	/*
	* Manages all accounts in NCTN Bank.
	* Demonstrates ArrayList, loops, and composition.
	*/

    // ArrayList to store all bank accounts
    private ArrayList<BankAccount> accounts;

    // Starting account number
    private int nextAccountNumber;

    // Constructor
    public Bank() {

        accounts = new ArrayList<>();
        nextAccountNumber = 1001;
    }

    // Create new account
    public int createAccount(String name, String password, double initialDeposit) {

        BankAccount account = new BankAccount(
                nextAccountNumber, name, password, initialDeposit);

        accounts.add(account);

        nextAccountNumber++;

        return account.getAccountNumber();
    }

    // Find account using account number
    public BankAccount findAccount(int accountNumber) {

        // Using loop to search in ArrayList
        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber() == accountNumber) {
                return accounts.get(i);
            }
        }

        return null;
    }

    // Find account using account holder name
    public BankAccount findAccountByName(String name) {

        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountHolder()
                    .equalsIgnoreCase(name)) {

                return accounts.get(i);
            }
        }

        return null;
    }

    // Transfer money between two accounts
    public boolean transfer(int fromAcc, int toAcc, double amount) {

        BankAccount sender = null;
        BankAccount receiver = null;

        // Loop to find both accounts
        for (int i = 0; i < accounts.size(); i++) {

            if (accounts.get(i).getAccountNumber() == fromAcc) {
                sender = accounts.get(i);
            }

            if (accounts.get(i).getAccountNumber() == toAcc) {
                receiver = accounts.get(i);
            }
        }

        // If both accounts exist
        if (sender != null && receiver != null) {

            if (sender.withdraw(amount)) {
                receiver.deposit(amount);
                return true;
            }
        }

        return false;
    }

    // Getter to return all accounts for admin viewing
    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }
}