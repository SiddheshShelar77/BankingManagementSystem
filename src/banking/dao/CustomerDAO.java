package banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import banking.model.Customer;
import banking.util.DBConnection;

public class CustomerDAO {

  public boolean registerCustomer(Customer customer) {

    String query = "INSERT INTO customers(name,email,password,phone) VALUES (?,?,?,?)";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setString(1, customer.getName());
      pstmt.setString(2, customer.getEmail());
      pstmt.setString(3, customer.getPassword());
      pstmt.setString(4, customer.getPhone());

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      System.out.println("Failed To Insert into Database.");
      e.printStackTrace();
    }

    return false;
  }

  public boolean emailExists(String email) {

    String query = "SELECT * FROM customers WHERE email = ?";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setString(1, email);
      ResultSet rs = pstmt.executeQuery();

      return rs.next();
    } catch (SQLException e) {
      System.out.println("Error while checking email.");
      e.printStackTrace();
    }

    return false;
  }

  public Customer login(String email, String password) {

    String query = "SELECT * FROM customers WHERE email = ? AND password = ?";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {
      pstmt.setString(1, email);
      pstmt.setString(2, password);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        Customer customer = new Customer();

        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setPhone(rs.getString("phone"));

        return customer;
      }

    } catch (SQLException e) {
      System.out.println("Error during login.");
      e.printStackTrace();
    }

    return null;
  }

  public Customer getCustomerById(int customerId) {

    String query = "SELECT * FROM customers WHERE customer_id = ?";

    try (Connection con = DBConnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

      pstmt.setInt(1, customerId);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        Customer customer = new Customer();

        customer.setCustomerId(rs.getInt("customer_id"));
        customer.setName(rs.getString("name"));
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        customer.setPhone(rs.getString("phone"));

        return customer;
      }

    } catch (SQLException e) {
      System.out.println("Error in finding customer");
      e.printStackTrace();
    }

    return null;
  }
}
