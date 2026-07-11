package banking.model;

import java.time.LocalDateTime;

public class Transaction {
  private int transactionId;
  private Long fromAccount;
  private Long toAccount;
  private double amount;
  private String transactionType;
  private LocalDateTime transactionDate;

  public Transaction() {

  }

  public Transaction(int transactionId,
      Long fromAccount,
      Long toAccount,
      double amount,
      String transactionType,
      LocalDateTime transactionDate) {

    this.transactionId = transactionId;
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
    this.transactionType = transactionType;
    this.transactionDate = transactionDate;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setFromAccount(Long fromAccount) {
    this.fromAccount = fromAccount;
  }

  public Long getFromAccount() {
    return fromAccount;
  }

  public void setToAccount(Long toAccount) {
    this.toAccount = toAccount;
  }

  public Long getToAccount() {
    return toAccount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getAmount() {
    return amount;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionDate(LocalDateTime transactionDate) {
    this.transactionDate = transactionDate;
  }

  public LocalDateTime getTransactionDate() {
    return transactionDate;
  }

  @Override
  public String toString() {
    return "Transaction [transactionId=" + transactionId
        + ", fromAccount=" + fromAccount
        + ", toAccount=" + toAccount
        + ", amount=" + amount
        + ", transactionType=" + transactionType
        + ", transactionDate=" + transactionDate
        + "]";
  }
}
