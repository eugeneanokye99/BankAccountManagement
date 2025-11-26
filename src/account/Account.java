package account;

import customer.Customer;
import transaction.Transactable;

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

    @Override
    public String toString() {
        return String.format("%s | %s | Balance: $%.2f | %s",
                accountNumber, getAccountType(), balance, status);
    }
}