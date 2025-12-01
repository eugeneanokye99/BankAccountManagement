package ui;

import customer.Customer;
import account.Account;
import account.AccountManager;
import java.util.Scanner;
import java.util.HashSet;
import java.util.ArrayList;

public class CustomerUI {
    private AccountManager accountManager;
    private Scanner scanner;

    public CustomerUI(AccountManager accountManager, Scanner scanner) {
        this.accountManager = accountManager;
        this.scanner = scanner;
    }

    // Main entry point for customer viewing
    public void viewCustomersMenu() {
        int customerChoice;

        do {
            displayCustomerMenu();
            System.out.print("Enter choice: ");

            try {
                customerChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (customerChoice) {
                    case 1: viewAllCustomers(); break;
                    case 2: viewCustomerDetails(); break;
                    case 3: searchCustomers(); break;
                    case 4: return; // Go back to main menu
                    default: System.out.println("Invalid choice! Please enter 1-4.");
                }

                if (customerChoice != 4) {
                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                customerChoice = 0;
            }

        } while (customerChoice != 4);
    }

    private void displayCustomerMenu() {
        int width = 50;
        System.out.println();
        System.out.print("┌"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┐");
        String title = "CUSTOMER MANAGEMENT";
        int padding = (width - title.length()) / 2;
        System.out.print("│"); System.out.print(" ".repeat(padding)); System.out.print(title);
        System.out.print(" ".repeat(width - padding - title.length())); System.out.println("│");
        System.out.print("└"); for (int i = 0; i < width; i++) System.out.print("─"); System.out.println("┘");
        System.out.println("\n1. View All Customers");
        System.out.println("2. View Customer Details");
        System.out.println("3. Search Customers");
        System.out.println("4. Back to Main Menu");
        System.out.println();
    }

    public void viewAllCustomers() {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        if (accountCount == 0) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println("\n" + "─".repeat(80));
        System.out.println("ALL CUSTOMERS");
        System.out.println("─".repeat(80));

        System.out.printf("%-10s %-20s %-10s %-15s %-12s %-15s%n",
                "ID", "Name", "Age", "Contact", "Type", "Accounts");
        System.out.println("─".repeat(80));

        HashSet<String> processedCustomerIds = new HashSet<>();
        int regularCount = 0;
        int premiumCount = 0;

        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();
            String customerId = customer.getCustomerId();

            if (processedCustomerIds.contains(customerId)) {
                continue;
            }

            // Count accounts for this customer
            int accountCountForCustomer = 0;
            for (int j = 0; j < accountCount; j++) {
                if (accounts[j].getCustomer().getCustomerId().equals(customerId)) {
                    accountCountForCustomer++;
                }
            }

            // Display customer info
            System.out.printf("%-10s %-20s %-10d %-15s %-12s %-15s%n",
                    customerId,
                    customer.getName(),
                    customer.getAge(),
                    customer.getContact(),
                    customer.getCustomerType(),
                    accountCountForCustomer + " account(s)");

            processedCustomerIds.add(customerId);

            // Count customer types
            if (customer.getCustomerType().equals("Regular")) {
                regularCount++;
            } else {
                premiumCount++;
            }
        }

        System.out.println("─".repeat(80));
        System.out.println("Total Customers: " + processedCustomerIds.size());
        System.out.println("Regular Customers: " + regularCount);
        System.out.println("Premium Customers: " + premiumCount);
    }

    private void viewCustomerDetails() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("VIEW CUSTOMER DETAILS");
        System.out.println("─".repeat(50));

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        viewCustomerDetailsById(customerId);
    }

    public void viewCustomerDetailsById(String customerId) {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        Customer foundCustomer = null;
        ArrayList<Account> customerAccounts = new ArrayList<>();

        // Find customer and their accounts
        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();
            if (customer.getCustomerId().equals(customerId)) {
                if (foundCustomer == null) {
                    foundCustomer = customer;
                }
                customerAccounts.add(accounts[i]);
            }
        }

        if (foundCustomer == null) {
            System.out.println("Customer not found!");
            return;
        }

        // Display customer details
        System.out.println("\n" + "─".repeat(60));
        System.out.println("CUSTOMER DETAILS");
        System.out.println("─".repeat(60));

        foundCustomer.displayCustomerDetails();

        // Display customer's accounts
        System.out.println("\nCustomer's Accounts:");
        System.out.println("─".repeat(60));

        if (customerAccounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
        } else {
            double totalBalance = 0;
            for (Account account : customerAccounts) {
                System.out.printf("%s | %s | Balance: $%.2f | Status: %s%n",
                        account.getAccountNumber(),
                        account.getAccountType(),
                        account.getBalance(),
                        account.getStatus());
                totalBalance += account.getBalance();
            }
            System.out.println("─".repeat(60));
            System.out.printf("Total Accounts: %d | Total Balance: $%.2f%n",
                    customerAccounts.size(), totalBalance);
        }
    }

    private void searchCustomers() {
        System.out.println("\n" + "─".repeat(50));
        System.out.println("SEARCH CUSTOMERS");
        System.out.println("─".repeat(50));

        System.out.println("Search by:");
        System.out.println("1. Customer Name");
        System.out.println("2. Customer ID");
        System.out.println("3. Customer Type");
        System.out.print("Select option (1-3): ");

        try {
            int searchOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (searchOption) {
                case 1:
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    searchCustomersByName(customerName);
                    break;

                case 2:
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    viewCustomerDetailsById(customerId);
                    break;

                case 3:
                    System.out.println("Customer Types:");
                    System.out.println("1. Regular");
                    System.out.println("2. Premium");
                    System.out.print("Select type (1-2): ");
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();

                    String customerType = (typeChoice == 1) ? "Regular" : "Premium";
                    searchCustomersByType(customerType);
                    break;

                default:
                    System.out.println("Invalid option!");
            }

        } catch (Exception e) {
            System.out.println("Invalid input!");
            scanner.nextLine();
        }
    }

    private void searchCustomersByName(String customerName) {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        System.out.println("\nSearch Results for: " + customerName);
        System.out.println("─".repeat(80));

        HashSet<String> processedCustomerIds = new HashSet<>();
        boolean found = false;

        System.out.printf("%-10s %-20s %-10s %-15s %-12s%n",
                "ID", "Name", "Age", "Contact", "Type");
        System.out.println("─".repeat(80));

        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();
            String customerId = customer.getCustomerId();

            if (processedCustomerIds.contains(customerId)) {
                continue;
            }

            if (customer.getName().toLowerCase().contains(customerName.toLowerCase())) {
                System.out.printf("%-10s %-20s %-10d %-15s %-12s%n",
                        customerId,
                        customer.getName(),
                        customer.getAge(),
                        customer.getContact(),
                        customer.getCustomerType());
                processedCustomerIds.add(customerId);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No customers found with name: " + customerName);
        }
    }

    private void searchCustomersByType(String customerType) {
        Account[] accounts = accountManager.getAccounts();
        int accountCount = accountManager.getActualAccountCount();

        System.out.println("\n" + customerType + " Customers:");
        System.out.println("─".repeat(80));

        HashSet<String> processedCustomerIds = new HashSet<>();
        int count = 0;

        System.out.printf("%-10s %-20s %-10s %-15s %-12s%n",
                "ID", "Name", "Age", "Contact", "Accounts");
        System.out.println("─".repeat(80));

        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();
            String customerId = customer.getCustomerId();

            if (processedCustomerIds.contains(customerId) ||
                    !customer.getCustomerType().equals(customerType)) {
                continue;
            }

            // Count accounts for this customer
            int accountCountForCustomer = 0;
            for (int j = 0; j < accountCount; j++) {
                if (accounts[j].getCustomer().getCustomerId().equals(customerId)) {
                    accountCountForCustomer++;
                }
            }

            System.out.printf("%-10s %-20s %-10d %-15s %-12s%n",
                    customerId,
                    customer.getName(),
                    customer.getAge(),
                    customer.getContact(),
                    accountCountForCustomer + " account(s)");

            processedCustomerIds.add(customerId);
            count++;
        }

        System.out.println("─".repeat(80));
        System.out.println("Total " + customerType + " Customers: " + count);
    }
}