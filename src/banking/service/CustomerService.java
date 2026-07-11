package banking.service;

import banking.dao.CustomerDAO;
import banking.exception.InvalidCredentialsException;
import banking.model.Customer;

public class CustomerService {
  private CustomerDAO customerDAO = new CustomerDAO();

  public boolean register(Customer customer) {

    if (customerDAO.emailExists(customer.getEmail())) {
      System.out.println("Email already Exist.");
      return false;
    }
    return customerDAO.registerCustomer(customer);
  }

  public Customer login(String email, String password) throws InvalidCredentialsException {

    Customer customer = customerDAO.login(email, password);

    if (customer == null) {
      throw new InvalidCredentialsException("Invalid Email or Password.");
    }

    return customer;
  }
}
