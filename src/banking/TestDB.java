package banking;

import java.sql.Connection;
import banking.util.DBConnection;

public class TestDB {
  public static void main(String args[]) {
    Connection con = DBConnection.getConnection();

    if (con != null) {
      System.err.println("Database Connected Successfully!");
    } else {
      System.out.println("Connection Failed!");
    }
  }
}
