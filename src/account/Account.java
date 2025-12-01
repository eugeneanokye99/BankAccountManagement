package account;

import customer.Customer;
import transaction.Transactable;
import utils.CustomUtils;

public abstract class Account implements Transactable {
    private final String accountNumber;
    private Customer customer;
    private double balance;
    private String status;

    private static int accountCounter = 0;

    public Account(Customer customer, double openingBalance) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }

        this.accountNumber = generateAccountNumber();
        this.customer = customer;
        this.balance = openingBalance;
        this.status = "Active";
    }

    private String generateAccountNumber() {
        accountCounter++;
        return String.format("ACC%03d", accountCounter);
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
    public static int getAccountCounter() { return accountCounter; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setStatus(String status) { this.status = status; }

    // Abstract methods
    public abstract void displayAccountDetails();
    public abstract String getAccountType();

    // Transaction methods
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }
        balance -= amount;
        return true;
    }

    public boolean transfer(Account targetAccount, double amount) {
        if (targetAccount == null) {
            CustomUtils.printError("Target account cannot be null");
            return false;
        }

        if (this == targetAccount) {
            CustomUtils.printError("Cannot transfer to the same account");
            return false;
        }

        if (amount <= 0) {
            CustomUtils.printError("Transfer amount must be positive");
            return false;
        }

        // Check if source account has sufficient funds
        if (amount > this.balance) {
            CustomUtils.printError("Insufficient funds for transfer");
            return false;
        }

        try {
            // Withdraw from source account
            boolean withdrawalSuccess = this.withdraw(amount);
            if (!withdrawalSuccess) {
                return false;
            }

            // Deposit to target account
            targetAccount.deposit(amount);

            return true;
        } catch (Exception e) {
            CustomUtils.printError("Transfer failed: " + e.getMessage());
            return false;
        }
    }

    // Implement Transactable interface
    @Override
    public boolean processTransaction(double amount, String type) {
        try {
            if (type.equalsIgnoreCase("DEPOSIT")) {
                deposit(amount);
                return true;
            } else if (type.equalsIgnoreCase("WITHDRAWAL")) {
                return withdraw(amount);
            }
            return false;
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
            return false;
        }
    }

}