package banking.service;

import java.time.LocalDateTime;

import banking.dao.AccountDAO;
import banking.dao.TransactionDAO;
import banking.exception.AccountNotFoundException;
import banking.exception.InsufficientBalanceException;
import banking.model.Account;
import banking.model.Transaction;

public class AccountService {

  private AccountDAO accountDAO = new AccountDAO();
  private TransactionDAO transactionDAO = new TransactionDAO();

  // Create Account
  public boolean createAccount(Account account) {
    return accountDAO.createAccount(account);
  }

  // Check Balance
  public double checkBalance(long accountNo)
      throws AccountNotFoundException {

    double balance = accountDAO.getBalance(accountNo);

    if (balance == -1) {
      throw new AccountNotFoundException("Account not found");
    }

    return balance;
  }

  // Deposit Money
  public boolean deposit(long accountNo, double amount)
      throws AccountNotFoundException {

    Account account = accountDAO.getAccount(accountNo);

    if (account == null) {
      throw new AccountNotFoundException("Account not found");
    }

    boolean success = accountDAO.deposit(accountNo, amount);

    if (success) {

      Transaction transaction = new Transaction(
          0,
          null,
          accountNo,
          amount,
          "DEPOSIT",
          LocalDateTime.now());

      transactionDAO.addTransaction(transaction);
    }

    return success;
  }

  // Withdraw Money
  public boolean withdraw(long accountNo, double amount)
      throws AccountNotFoundException,
      InsufficientBalanceException {

    Account account = accountDAO.getAccount(accountNo);

    if (account == null) {
      throw new AccountNotFoundException("Account not found");
    }

    if (account.getBalance() < amount) {
      throw new InsufficientBalanceException("Insufficient Balance");
    }

    boolean success = accountDAO.withdraw(accountNo, amount);

    if (success) {

      Transaction transaction = new Transaction(
          0,
          accountNo,
          null,
          amount,
          "WITHDRAW",
          LocalDateTime.now());

      transactionDAO.addTransaction(transaction);
    }

    return success;
  }

  // Transfer Money
  public boolean transferMoney(long fromAccount,
      long toAccount,
      double amount)
      throws AccountNotFoundException,
      InsufficientBalanceException {

    Account sender = accountDAO.getAccount(fromAccount);
    Account receiver = accountDAO.getAccount(toAccount);

    if (sender == null) {
      throw new AccountNotFoundException("Sender account not found.");
    }

    if (receiver == null) {
      throw new AccountNotFoundException("Receiver account not found.");
    }

    if (sender.getBalance() < amount) {
      throw new InsufficientBalanceException("Insufficient Balance.");
    }

    boolean success = accountDAO.transferMoney(
        fromAccount,
        toAccount,
        amount);

    if (success) {

      Transaction transaction = new Transaction(
          0,
          fromAccount,
          toAccount,
          amount,
          "TRANSFER",
          LocalDateTime.now());

      transactionDAO.addTransaction(transaction);
    }

    return success;
  }
}