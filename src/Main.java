import java.util.Scanner;
import customer.Customer;
import customer.RegularCustomer;
import customer.PremiumCustomer;
import account.Account;
import account.SavingsAccount;
import account.CheckingAccount;
import account.AccountManager;
import manager.TransactionManager;
import transaction.Transaction;

public class Main {
    private static AccountManager accountManager = new AccountManager();
    private static TransactionManager transactionManager = new TransactionManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        createSampleAccounts();

        int choice;
        do {
            displayMainMenu();
            System.out.print("Enter choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: createAccount(); break;
                    case 2: viewAccounts(); break;
                    case 3: processTransaction(); break;
                    case 4: viewTransactionHistory(); break;
                    case 5: exitApplication(); break;
                    default: System.out.println("Invalid choice! Please enter 1-5.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                choice = 0;
            }

            if (choice != 5) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 5);

        scanner.close();
    }

    private static void displayMainMenu() {
        int width = 50;
        System.out.println();
        System.out.print("┌"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┐");
        String title = "BANK ACCOUNT MANAGEMENT - MAIN MENU";
        int padding = (width - title.length()) / 2;
        System.out.print("│"); System.out.print(" ".repeat(padding)); System.out.print(title);
        System.out.print(" ".repeat(width - padding - title.length())); System.out.println("│");
        System.out.print("└"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┘");
        System.out.println("\n1. Create Account");
        System.out.println("2. View Accounts");
        System.out.println("3. Process Transaction");
        System.out.println("4. View Transaction History");
        System.out.println("5. Exit");
        System.out.println();
    }

    private static void createAccount() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("ACCOUNT CREATION");
        System.out.println("─".repeat(50));

        try {
            System.out.print("Enter customer name: ");
            String name = scanner.nextLine();

            System.out.print("Enter customer age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter customer contact: ");
            String contact = scanner.nextLine();

            System.out.print("Enter customer address: ");
            String address = scanner.nextLine();

            System.out.println("\nCustomer type:");
            System.out.println("1. Regular Customer (Standard banking services)");
            System.out.println("2. Premium Customer (Enhanced benefits, min balance $10,000)");
            System.out.print("Select type (1-2): ");
            int customerType = scanner.nextInt();
            scanner.nextLine();

            Customer customer;
            if (customerType == 1) {
                customer = new RegularCustomer(name, age, contact, address);
            } else if (customerType == 2) {
                customer = new PremiumCustomer(name, age, contact, address);
            } else {
                System.out.println("Invalid customer type selection!");
                return;
            }

            System.out.println("\nAccount type:");
            System.out.println("1. Savings Account (Interest: 3.5%, Min Balance: $500)");
            System.out.println("2. Checking Account (Overdraft: $1,000, Monthly Fee: $10)");
            System.out.print("Select type (1-2): ");
            int accountType = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter initial deposit amount: $");
            double openingBalance = scanner.nextDouble();
            scanner.nextLine();

            Account account;
            if (accountType == 1) {
                account = new SavingsAccount(customer, openingBalance);
            } else if (accountType == 2) {
                account = new CheckingAccount(customer, openingBalance);
            } else {
                System.out.println("Invalid account type selection!");
                return;
            }

            if (accountManager.addAccount(account)) {
                System.out.println("\n✓ Account created successfully!");
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Customer: " + customer.getName() + " (" + customer.getCustomerType() + ")");
                System.out.println("Account Type: " + account.getAccountType());
                System.out.println("Initial Balance: $" + String.format("%.2f", account.getBalance()));

                if (account instanceof SavingsAccount) {
                    SavingsAccount savings = (SavingsAccount) account;
                    System.out.println("Interest Rate: " + savings.getInterestRate() + "%");
                    System.out.println("Minimum Balance: $" + String.format("%.2f", savings.getMinimumBalance()));
                } else if (account instanceof CheckingAccount) {
                    CheckingAccount checking = (CheckingAccount) account;
                    System.out.println("Overdraft Limit: $" + String.format("%.2f", checking.getOverdraftLimit()));
                    System.out.println("Monthly Fee: $" + String.format("%.2f", checking.getMonthlyFee()));
                    if (customer.getCustomerType().equals("Premium")) {
                        System.out.println("Monthly Fee Status: WAIVED");
                    }
                }
                System.out.println("Status: " + account.getStatus());
            } else {
                System.out.println("Cannot create more accounts. Maximum limit reached.");
            }

        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static void viewAccounts() {
        accountManager.viewAllAccounts();
    }

    private static void processTransaction() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("PROCESS TRANSACTION");
        System.out.println("─".repeat(50));

        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();

            Account account = accountManager.findAccount(accountNumber);
            if (account == null) {
                System.out.println("Account not found!");
                return;
            }

            // Display account details
            System.out.println("\nAccount Details:");
            System.out.println("Customer: " + account.getCustomer().getName());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));

            System.out.println("\nTransaction type:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.print("Select type (1-2): ");
            int transactionType = scanner.nextInt();
            scanner.nextLine();

            if (transactionType != 1 && transactionType != 2) {
                System.out.println("Invalid transaction type!");
                return;
            }

            System.out.print("Enter amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();

            // Transaction confirmation
            String type = (transactionType == 1) ? "DEPOSIT" : "WITHDRAWAL";
            double previousBalance = account.getBalance();
            double newBalance = (transactionType == 1) ? previousBalance + amount : previousBalance - amount;

            System.out.println("\nTRANSACTION CONFIRMATION");
            System.out.println("Transaction ID: TXN" + String.format("%03d", Transaction.getTransactionCounter() + 1));
            System.out.println("Account: " + accountNumber);
            System.out.println("Type: " + type);
            System.out.println("Amount: $" + String.format("%.2f", amount));
            System.out.println("Previous Balance: $" + String.format("%.2f", previousBalance));
            System.out.println("New Balance: $" + String.format("%.2f", newBalance));
            System.out.println("Date/Time: " + java.time.LocalDateTime.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")));

            System.out.print("\nConfirm transaction? (Y/N): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("Y")) {
                boolean success = account.processTransaction(amount, type);
                if (success) {
                    // Create and record transaction
                    Transaction transaction = new Transaction(accountNumber, type, amount, account.getBalance());
                    transactionManager.addTransaction(transaction);

                    System.out.println("\n✓ Transaction completed successfully!");
                    System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
                } else {
                    System.out.println("\n✗ Transaction failed!");
                }
            } else {
                System.out.println("Transaction cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error processing transaction: " + e.getMessage());
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("VIEW TRANSACTION HISTORY");
        System.out.println("─".repeat(50));

        try {
            System.out.print("Enter Account Number: ");
            String accountNumber = scanner.nextLine();

            // Verify account exists
            Account account = accountManager.findAccount(accountNumber);
            if (account == null) {
                System.out.println("Account not found!");
                return;
            }

            transactionManager.viewTransactionsByAccount(accountNumber);

        } catch (Exception e) {
            System.out.println("Error viewing transaction history: " + e.getMessage());
        }
    }

    private static void exitApplication() {
        System.out.println("\nThank you for using Bank Account Management System!");
        System.out.println("Goodbye!");
    }

    private static void createSampleAccounts() {
        try {
            Customer customer1 = new RegularCustomer("John Smith", 35, "+1-555-0101", "123 Main St");
            Customer customer2 = new RegularCustomer("Sarah Johnson", 28, "+1-555-0102", "456 Oak Ave");
            Customer customer3 = new PremiumCustomer("Michael Chen", 42, "+1-555-0103", "789 Pine Rd");
            Customer customer4 = new RegularCustomer("Emily Brown", 31, "+1-555-0104", "321 Elm St");
            Customer customer5 = new PremiumCustomer("David Wilson", 55, "+1-555-0105", "654 Maple Dr");

            accountManager.addAccount(new SavingsAccount(customer1, 5250.00));
            accountManager.addAccount(new CheckingAccount(customer2, 3450.00));
            accountManager.addAccount(new SavingsAccount(customer3, 15750.00));
            accountManager.addAccount(new CheckingAccount(customer4, 890.00));
            accountManager.addAccount(new SavingsAccount(customer5, 25300.00));

            // Add some sample transactions
            transactionManager.addTransaction(new Transaction("ACC001", "DEPOSIT", 1000, 6250));
            transactionManager.addTransaction(new Transaction("ACC001", "WITHDRAWAL", 500, 5750));
            transactionManager.addTransaction(new Transaction("ACC002", "DEPOSIT", 200, 3650));
            transactionManager.addTransaction(new Transaction("ACC002", "WITHDRAWAL", 1000, 2650));

        } catch (Exception e) {
            System.out.println("Error creating sample data: " + e.getMessage());
        }
    }
}