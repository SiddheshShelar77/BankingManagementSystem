package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import banking.model.Transaction;
import banking.util.DBConnection;

public class TransactionDAO {

  public boolean addTransaction(Transaction transaction) {

    String query = "INSERT INTO transactions (from_account, to_account, amount, transaction_type, transaction_date) VALUES (?,?,?,?,?)";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      // from_account
      if (transaction.getFromAccount() == null) {
        pstmt.setNull(1, Types.BIGINT);
      } else {
        pstmt.setLong(1, transaction.getFromAccount());
      }

      // to_account
      if (transaction.getToAccount() == null) {
        pstmt.setNull(2, Types.BIGINT);
      } else {
        pstmt.setLong(2, transaction.getToAccount());
      }

      pstmt.setDouble(3, transaction.getAmount());
      pstmt.setString(4, transaction.getTransactionType());
      pstmt.setTimestamp(5, Timestamp.valueOf(transaction.getTransactionDate()));

      int rowsAffected = pstmt.executeUpdate();

      return rowsAffected > 0;

    } catch (SQLException e) {
      System.out.println("Error while adding transaction.");
      e.printStackTrace();
    }

    return false;
  }

  public List<Transaction> getTransactionHistory(long accountNo) {

    List<Transaction> transactions = new ArrayList<>();

    String query = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ? ORDER BY transaction_date DESC";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setLong(1, accountNo);
      pstmt.setLong(2, accountNo);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        Transaction transaction = new Transaction();

        transaction.setTransactionId(
            rs.getInt("transaction_id"));

        // from_account
        long from = rs.getLong("from_account");
        if (rs.wasNull()) {
          transaction.setFromAccount(null);
        } else {
          transaction.setFromAccount(from);
        }

        // to_account
        long to = rs.getLong("to_account");
        if (rs.wasNull()) {
          transaction.setToAccount(null);
        } else {
          transaction.setToAccount(to);
        }

        transaction.setAmount(
            rs.getDouble("amount"));

        transaction.setTransactionType(
            rs.getString("transaction_type"));

        Timestamp timestamp = rs.getTimestamp("transaction_date");

        transaction.setTransactionDate(
            timestamp.toLocalDateTime());

        transactions.add(transaction);
      }

    } catch (SQLException e) {
      System.out.println("Error while getting transaction history.");
      e.printStackTrace();
    }

    return transactions;
  }
}