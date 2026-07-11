package banking.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private static final String url = "jdbc:mysql://localhost:3306/banking_system";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "Siddhesh@1603";

  private DBConnection() {
    // Constructor Private
    // because we never want anyone to create objects like:
    // DBConnection db = new DBConnection();

    // This class is only meant to provide connections through:
    // DBConnection.getConnection();
  }

  public static Connection getConnection() { // COnnection datatyep because we are returning con
    Connection con = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection(url, USERNAME, PASSWORD);
    } catch (ClassNotFoundException e) {
      System.out.println("MySQL Driver Not Found.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("Failed To Connect Database.");
      e.printStackTrace();
    }

    return con;
  }
}
