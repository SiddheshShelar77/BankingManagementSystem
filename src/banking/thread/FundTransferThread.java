package banking.thread;

import banking.exception.AccountNotFoundException;
import banking.exception.InsufficientBalanceException;
import banking.service.AccountService;

public class FundTransferThread extends Thread {

  private AccountService accountService;

  private long fromAccount;
  private long toAccount;
  private double amount;

  public FundTransferThread(AccountService accountService,
      long fromAccount,
      long toAccount,
      double amount) {

    this.accountService = accountService;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
  }

  @Override
  public void run() {

    try {

      accountService.transferMoney(
          fromAccount,
          toAccount,
          amount);

      System.out.println(
          "Transfer Successful by "
              + Thread.currentThread().getName());

    }

    catch (AccountNotFoundException | InsufficientBalanceException e) {

      System.out.println(e.getMessage());
    }
  }
}