package manager;

import transaction.Transaction;
import java.util.Arrays;
import java.util.Comparator;

public class TransactionManager {
    private Transaction[] transactions;
    private int transactionCount;

    public TransactionManager() {
        this.transactions = new Transaction[200];
        this.transactionCount = 0;
    }

    public TransactionManager(int capacity) {
        this.transactions = new Transaction[capacity];
        this.transactionCount = 0;
    }

    // Add transaction to array
    public boolean addTransaction(Transaction transaction) {
        if (transactionCount < transactions.length) {
            transactions[transactionCount] = transaction;
            transactionCount++;
            return true;
        }
        return false; // Array is full
    }

    // View transactions for a specific account (newest first)
    public void viewTransactionsByAccount(String accountNumber) {
        Transaction[] accountTransactions = getTransactionsForAccount(accountNumber);

        if (accountTransactions.length == 0) {
            System.out.println("No transactions found for account: " + accountNumber);
            return;
        }

        // Sort by timestamp in reverse chronological order (newest first)
        Arrays.sort(accountTransactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                return t2.getTimestamp().compareTo(t1.getTimestamp());
            }
        });

        System.out.println("\n" + "─".repeat(90));
        System.out.println("TRANSACTION HISTORY - Account: " + accountNumber);
        System.out.println("─".repeat(90));

        System.out.printf("%-10s %-12s %-10s %-12s %-15s %-20s%n",
                "ID", "Account", "Type", "Amount", "Balance After", "Timestamp");
        System.out.println("─".repeat(90));

        for (Transaction transaction : accountTransactions) {
            System.out.printf("%-10s %-12s %-10s $%-11.2f $%-14.2f %-20s%n",
                    transaction.getTransactionId(),
                    transaction.getAccountNumber(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getBalanceAfter(),
                    transaction.getTimestamp());
        }

        // Display summary
        System.out.println("─".repeat(90));
        double totalDeposits = calculateTotalDeposits(accountNumber);
        double totalWithdrawals = calculateTotalWithdrawals(accountNumber);
        double netChange = totalDeposits - totalWithdrawals;

        System.out.printf("Summary: Total Deposits: $%.2f | Total Withdrawals: $%.2f | Net Change: $%.2f%n",
                totalDeposits, totalWithdrawals, netChange);
        System.out.println("Total Transactions: " + accountTransactions.length);
    }

    // Calculate total deposits for an account
    public double calculateTotalDeposits(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactions[i];
            if (transaction.getAccountNumber().equals(accountNumber) &&
                    transaction.getType().equals("DEPOSIT")) {
                total += transaction.getAmount();
            }
        }
        return total;
    }

    // Calculate total withdrawals for an account
    public double calculateTotalWithdrawals(String accountNumber) {
        double total = 0;
        for (int i = 0; i < transactionCount; i++) {
            Transaction transaction = transactions[i];
            if (transaction.getAccountNumber().equals(accountNumber) &&
                    transaction.getType().equals("WITHDRAWAL")) {
                total += transaction.getAmount();
            }
        }
        return total;
    }

    // Get transaction count
    public int getTransactionCount() {
        return transactionCount;
    }

    // Helper method to get transactions for a specific account
    private Transaction[] getTransactionsForAccount(String accountNumber) {
        int count = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                count++;
            }
        }

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getAccountNumber().equals(accountNumber)) {
                result[index++] = transactions[i];
            }
        }
        return result;
    }
}