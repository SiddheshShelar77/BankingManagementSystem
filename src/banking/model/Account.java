package banking.model;

public class Account {
  private long accountNo;
  private int customerId;
  private double balance;
  private String accountType;

  public Account() {

  }

  public Account(long accountNo, int customerId, double balance, String accountType) {
    this.accountNo = accountNo;
    this.customerId = customerId;
    this.balance = balance;
    this.accountType = accountType;
  }

  public void setAccountNo(long accountNo) {
    this.accountNo = accountNo;
  }

  public long getAccountNo() {
    return accountNo;
  }

  public void setCustomerId(int customerId) {
    this.customerId = customerId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public double getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "Account [accountNo=" + accountNo
        + ", customerId=" + customerId
        + ", accountType=" + accountType
        + ", balance=" + balance
        + "]";
  }
}
