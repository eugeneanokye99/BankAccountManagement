package ui;

import account.Account;
import account.AccountManager;
import java.util.Scanner;

public class AccountUI {
    private AccountManager accountManager;
    private Scanner scanner;

    public AccountUI(AccountManager accountManager, Scanner scanner) {
        this.accountManager = accountManager;
        this.scanner = scanner;
    }

    // Main entry point for account viewing
    public void viewAccountsMenu() {
        int accountChoice;

        do {
            displayAccountMenu();
            System.out.print("Enter choice: ");

            try {
                accountChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (accountChoice) {
                    case 1: viewAllAccounts(); break;
                    case 2: viewAccountDetails(); break;
                    case 3: searchAccount(); break;
                    case 4: return; // Go back to main menu
                    default: System.out.println("Invalid choice! Please enter 1-4.");
                }

                if (accountChoice != 4) {
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                accountChoice = 0;
            }

        } while (accountChoice != 4);
    }

    private void displayAccountMenu() {
        int width = 50;
        System.out.println();
        System.out.print("┌"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┐");
        String title = "ACCOUNT MANAGEMENT";
        int padding = (width - title.length()) / 2;
        System.out.print("│"); System.out.print(" ".repeat(padding)); System.out.print(title);
        System.out.print(" ".repeat(width - padding - title.length())); System.out.println("│");
        System.out.print("└"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┘");
        System.out.println("\n1. View All Accounts");
        System.out.println("2. View Account Details");
        System.out.println("3. Search Account");
        System.out.println("4. Back to Main Menu");
        System.out.println();
    }

    public void viewAllAccounts() {
        accountManager.viewAllAccounts();
    }

    public void viewAccountDetails() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("VIEW ACCOUNT DETAILS");
        System.out.println("─".repeat(50));

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account = accountManager.findAccount(accountNumber);
        if (account != null) {
            System.out.println("\n=== ACCOUNT DETAILS ===");
            account.displayAccountDetails();
        } else {
            System.out.println("Account not found!");
        }
    }

    private void searchAccount() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("SEARCH ACCOUNT");
        System.out.println("─".repeat(50));

        System.out.println("Search by:");
        System.out.println("1. Account Number");
        System.out.println("2. Customer Name");
        System.out.println("3. Account Type");
        System.out.print("Select option (1-3): ");

        try {
            int searchOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (searchOption) {
                case 1:
                    System.out.print("Enter Account Number: ");
                    String accNumber = scanner.nextLine();
                    searchByAccountNumber(accNumber);
                    break;

                case 2:
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    searchByCustomerName(customerName);
                    break;

                case 3:
                    System.out.println("Account Types:");
                    System.out.println("1. Savings");
                    System.out.println("2. Checking");
                    System.out.print("Select type (1-2): ");
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();

                    String accountType = (typeChoice == 1) ? "Savings" : "Checking";
                    searchByAccountType(accountType);
                    break;

                default:
                    System.out.println("Invalid option!");
            }

        } catch (Exception e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void searchByAccountNumber(String accountNumber) {
        Account account = accountManager.findAccount(accountNumber);
        if (account != null) {
            account.displayAccountDetails();
        } else {
            System.out.println("Account not found!");
        }
    }

    private void searchByCustomerName(String customerName) {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        System.out.println("\nSearch Results for: " + customerName);
        System.out.println("─".repeat(80));

        boolean found = false;
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getCustomer().getName().toLowerCase()
                    .contains(customerName.toLowerCase())) {
                accounts[i].displayAccountDetails();
                System.out.println("─".repeat(40));
                found = true;
            }
        }

        if (!found) {
            System.out.println("No accounts found for customer: " + customerName);
        }
    }

    private void searchByAccountType(String accountType) {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        System.out.println("\n" + accountType + " Accounts:");
        System.out.println("─".repeat(80));

        int count = 0;
        double totalBalance = 0;

        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountType().equals(accountType)) {
                accounts[i].displayAccountDetails();
                System.out.println("─".repeat(40));
                count++;
                totalBalance += accounts[i].getBalance();
            }
        }

        if (count == 0) {
            System.out.println("No " + accountType + " accounts found.");
        } else {
            System.out.println("─".repeat(80));
            System.out.println("Total " + accountType + " Accounts: " + count);
            System.out.println("Total Balance: $" + String.format("%.2f", totalBalance));
        }
    }
}