package banking.service;

import java.util.List;

import banking.dao.TransactionDAO;
import banking.model.Transaction;

public class TransactionService {

  private TransactionDAO transactionDAO = new TransactionDAO();

  public List<Transaction> getTransactionHistory(long accountNo) {

    return transactionDAO.getTransactionHistory(accountNo);
  }
}
