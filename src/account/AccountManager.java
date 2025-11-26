package account;

public class AccountManager {
    private Account[] accounts;
    private int accountCount;

    public AccountManager() {
        this.accounts = new Account[50];
        this.accountCount = 0;
    }

    public AccountManager(int capacity) {
        this.accounts = new Account[capacity];
        this.accountCount = 0;
    }

    // Add account to array
    public boolean addAccount(Account account) {
        if (accountCount < accounts.length) {
            accounts[accountCount] = account;
            accountCount++;
            return true;
        }
        return false; // Array is full
    }

    // Find account by account number
    public Account findAccount(String accountNumber) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null; // Account not found
    }

    // Display all accounts
    public void viewAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts found.");
            return;
        }

        double totalBalance = 0;
        System.out.println("\n" + "─".repeat(80));
        System.out.println("ACCOUNT LISTING");
        System.out.println("─".repeat(80));

        for (int i = 0; i < accountCount; i++) {
            Account account = accounts[i];
            System.out.printf("%s | %s | %s | $%.2f | %s%n",
                    account.getAccountNumber(),
                    account.getCustomer().getName(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.getStatus());

            // Display account-specific details
            if (account instanceof account.SavingsAccount) {
                account.SavingsAccount savings = (account.SavingsAccount) account;
                System.out.printf("  Interest Rate: %.1f%% Min Balance: $%.2f%n",
                        savings.getInterestRate(), savings.getMinimumBalance());
            } else if (account instanceof account.CheckingAccount) {
                account.CheckingAccount checking = (account.CheckingAccount) account;
                System.out.printf("  Overdraft Limit: $%.2f Monthly Fee: $%.2f%n",
                        checking.getOverdraftLimit(), checking.getMonthlyFee());
            }

            System.out.println("─".repeat(80));
            totalBalance += account.getBalance();
        }

        System.out.println("Total Accounts: " + accountCount);
        System.out.println("Total Bank Balance: $" + String.format("%.2f", totalBalance));
    }

    // Get total balance of all accounts
    public double getTotalBalance() {
        double total = 0;
        for (int i = 0; i < accountCount; i++) {
            total += accounts[i].getBalance();
        }
        return total;
    }

    // Get number of accounts
    public int getAccountCount() {
        return accountCount;
    }

    // Get all accounts (for TransactionManager)
    public Account[] getAccounts() {
        return accounts;
    }

    // Get account count (for TransactionManager)
    public int getActualAccountCount() {
        return accountCount;
    }
}