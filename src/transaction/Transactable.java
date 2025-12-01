package transaction;

import account.Account;

public interface Transactable {
    boolean processTransaction(double amount, String type);
    boolean transfer(Account targetAccount, double amount);
}