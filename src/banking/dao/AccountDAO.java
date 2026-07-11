package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banking.model.Account;
import banking.util.DBConnection;

public class AccountDAO {

  public boolean createAccount(Account account) {

    String query = "INSERT INTO accounts(account_no, customer_id, balance, account_type) VALUES (?,?,?,?)";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setLong(1, account.getAccountNo());
      pstmt.setInt(2, account.getCustomerId());
      pstmt.setDouble(3, account.getBalance());
      pstmt.setString(4, account.getAccountType());

      int rowsAffected = pstmt.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      System.out.println("Error while creating account.");
      e.printStackTrace();
    }

    return false;
  }

  public Account getAccount(long accontNo) {

    String query = "SELECT * FROM accounts WHERE account_no = ?";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setLong(1, accontNo);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        Account account = new Account();

        account.setAccountNo(rs.getLong("account_no"));
        account.setCustomerId(rs.getInt("customer_id"));
        account.setBalance(rs.getDouble("balance"));
        account.setAccountType(rs.getString("account_type"));

        return account;
      }

    } catch (SQLException e) {
      System.out.println("Error in finding account.");
      e.printStackTrace();
    }

    return null;
  }

  public double getBalance(long accountNo) {
    Account account = getAccount(accountNo);

    if (account != null) {
      return account.getBalance();
    }

    return -1;
  }

  public boolean deposit(long accountNo, double amount) {

    String query = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setDouble(1, amount);
      pstmt.setLong(2, accountNo);

      int rowsAffected = pstmt.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      System.out.println("Error while depositing money.");
      e.printStackTrace();
    }

    return false;
  }

  public boolean withdraw(long accountNo, double amount) {

    Account account = getAccount(accountNo);

    if (account == null) {
      return false;
    }

    if (account.getBalance() < amount) {
      return false;
    }

    String query = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setDouble(1, amount);
      pstmt.setLong(2, accountNo);

      int rowsAffected = pstmt.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      System.out.println("Error while withdrawing money.");
      e.printStackTrace();
    }

    return false;
  }

  public boolean transferMoney(long fromAccount,
      long toAccount,
      double amount) {

    String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";

    String depositQuery = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

    try (Connection con = DBConnection.getConnection()) {

      con.setAutoCommit(false);

      // Check sender account
      Account sender = getAccount(fromAccount);

      if (sender == null || sender.getBalance() < amount) {
        return false;
      }

      // Withdraw from sender
      PreparedStatement pstmt1 = con.prepareStatement(withdrawQuery);

      pstmt1.setDouble(1, amount);
      pstmt1.setLong(2, fromAccount);

      int withdrawRows = pstmt1.executeUpdate();

      // Deposit to receiver
      PreparedStatement pstmt2 = con.prepareStatement(depositQuery);

      pstmt2.setDouble(1, amount);
      pstmt2.setLong(2, toAccount);

      int depositRows = pstmt2.executeUpdate();

      if (withdrawRows > 0 && depositRows > 0) {

        con.commit();

        return true;
      }

      con.rollback();

    } catch (SQLException e) {

      e.printStackTrace();
    }

    return false;
  }
}
