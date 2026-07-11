package banking;

import java.util.List;
import java.util.Scanner;

import banking.exception.AccountNotFoundException;
import banking.exception.InsufficientBalanceException;
import banking.exception.InvalidCredentialsException;
import banking.model.Account;
import banking.model.Customer;
import banking.model.Transaction;
import banking.service.AccountService;
import banking.service.CustomerService;
import banking.service.TransactionService;
import banking.thread.FundTransferThread;
import banking.util.AccountNumberGenerator;
import banking.util.InputValidator;

public class BankingApp {

  private static Scanner sc = new Scanner(System.in);

  private static CustomerService customerService = new CustomerService();
  private static AccountService accountService = new AccountService();
  private static TransactionService transactionService = new TransactionService();

  // Stores the currently logged-in customer
  private static Customer loggedInCustomer = null;

  public static void main(String[] args) {

    showMainMenu();

  }

  public static void showMainMenu() {

    while (true) {

      System.out.println("\n====================================");
      System.out.println("      BANKING MANAGEMENT SYSTEM");
      System.out.println("====================================");
      System.out.println("1. Register");
      System.out.println("2. Login");
      System.out.println("3. Exit");

      System.out.print("Enter your choice : ");

      int choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {

        case 1:
          registerCustomer();
          break;

        case 2:
          loginCustomer();
          break;

        case 3:
          System.out.println("Thank You For Using Banking Management System.");
          System.exit(0);
          break;

        default:
          System.out.println("Invalid Choice! Please try again.");
      }
    }
  }

  public static void registerCustomer() {

    System.out.println("\n========== CUSTOMER REGISTRATION ==========");

    System.out.print("Enter Name: ");
    String name = sc.nextLine();

    System.out.print("Enter Email: ");
    String email = sc.nextLine();

    if (!InputValidator.isValidEmail(email)) {
      System.out.println("Invalid Email Format.");
      return;
    }

    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    if (!InputValidator.isValidPassword(password)) {
      System.out.println("Password must be at least 6 characters.");
      return;
    }

    System.out.print("Enter Phone Number: ");
    String phone = sc.nextLine();

    if (!InputValidator.isValidPhone(phone)) {
      System.out.println("Phone number must contain exactly 10 digits.");
      return;
    }

    Customer customer = new Customer(
        0,
        name,
        email,
        password,
        phone);

    boolean success = customerService.register(customer);

    if (success) {
      System.out.println("Registration Successful.");
    } else {
      System.out.println("Registration Failed.");
    }
  }

  public static void loginCustomer() {

    System.out.println("\n========== CUSTOMER LOGIN ==========");

    System.out.print("Enter Email: ");
    String email = sc.nextLine();

    System.out.print("Enter Password: ");
    String password = sc.nextLine();

    try {

      loggedInCustomer = customerService.login(email, password);

      System.out.println("\nLogin Successful.");
      System.out.println("Welcome " + loggedInCustomer.getName());

      customerMenu();

    } catch (InvalidCredentialsException e) {

      System.out.println(e.getMessage());
    }
  }

  public static void customerMenu() {

    while (true) {

      System.out.println("\n====================================");
      System.out.println("        CUSTOMER MENU");
      System.out.println("====================================");

      System.out.println("Welcome " + loggedInCustomer.getName());

      System.out.println("\n1. Create Account");
      System.out.println("2. Check Balance");
      System.out.println("3. Deposit");
      System.out.println("4. Withdraw");
      System.out.println("5. Transfer Money");
      System.out.println("6. Transaction History");
      System.out.println("7. Logout");

      System.out.print("\nEnter Choice : ");

      int choice = sc.nextInt();
      sc.nextLine();

      switch (choice) {

        case 1:
          createAccount();
          break;

        case 2:
          checkBalance();
          break;

        case 3:
          deposit();
          break;

        case 4:
          withdraw();
          break;

        case 5:
          transferMoney();
          break;

        case 6:
          transactionHistory();
          break;

        case 7:
          loggedInCustomer = null;
          System.out.println("Logged out successfully.");
          return;

        default:
          System.out.println("Invalid Choice.");
      }
    }
  }

  private static void transactionHistory() {

    System.out.println("\n========== TRANSACTION HISTORY ==========");

    System.out.print("Enter Account Number: ");
    long accountNo = sc.nextLong();

    List<Transaction> transactions = transactionService.getTransactionHistory(accountNo);

    if (transactions.isEmpty()) {

      System.out.println("No Transactions Found.");
      return;
    }

    System.out.println("\n---------------------------------------------------------------");
    System.out.printf("%-5s %-12s %-12s %-10s %-12s %-20s%n",
        "ID", "FROM", "TO", "AMOUNT", "TYPE", "DATE");
    System.out.println("---------------------------------------------------------------");

    for (Transaction transaction : transactions) {

      System.out.printf("%-5d %-12d %-12d %-10.2f %-12s %-20s%n",
          transaction.getTransactionId(),
          transaction.getFromAccount(),
          transaction.getToAccount(),
          transaction.getAmount(),
          transaction.getTransactionType(),
          transaction.getTransactionDate());

    }
  }

  private static void transferMoney() {

    System.out.println("\n========== TRANSFER MONEY ==========");

    System.out.print("Enter Sender Account Number: ");
    long fromAccount = sc.nextLong();

    System.out.print("Enter Receiver Account Number: ");
    long toAccount = sc.nextLong();

    System.out.print("Enter Amount: ");
    double amount = sc.nextDouble();

    if (amount <= 0) {
      System.out.println("Amount must be greater than zero.");
      return;
    }

    FundTransferThread transferThread = new FundTransferThread(
        accountService,
        fromAccount,
        toAccount,
        amount);

    transferThread.start();

    try {

      transferThread.join();

    } catch (InterruptedException e) {

      System.out.println("Transfer interrupted.");

    }
  }

  private static void withdraw() {

    System.out.println("\n========== WITHDRAW ==========");

    System.out.print("Enter Account Number: ");
    long accountNo = sc.nextLong();

    System.out.print("Enter Amount: ");
    double amount = sc.nextDouble();

    if (amount <= 0) {
      System.out.println("Amount must be greater than zero.");
      return;
    }

    try {

      boolean success = accountService.withdraw(accountNo, amount);

      if (success) {

        System.out.println("Withdrawal Successful.");

      } else {

        System.out.println("Withdrawal Failed.");
      }

    } catch (AccountNotFoundException | InsufficientBalanceException e) {

      System.out.println(e.getMessage());

    }
  }

  private static void deposit() {

    System.out.println("\n========== DEPOSIT ==========");

    System.out.print("Enter Account Number: ");
    long accountNo = sc.nextLong();

    System.out.print("Enter Amount: ");
    double amount = sc.nextDouble();

    try {

      boolean success = accountService.deposit(accountNo, amount);

      if (success) {

        System.out.println("Deposit Successful.");

      } else {

        System.out.println("Deposit Failed.");
      }

    } catch (AccountNotFoundException e) {

      System.out.println(e.getMessage());
    }
  }

  public static void checkBalance() {

    System.out.println("\n========== CHECK BALANCE ==========");

    System.out.print("Enter Account Number: ");
    long accountNo = sc.nextLong();

    try {

      double balance = accountService.checkBalance(accountNo);

      System.out.println("Current Balance: ₹" + balance);

    } catch (AccountNotFoundException e) {

      System.out.println(e.getMessage());

    }
  }

  public static void createAccount() {

    System.out.println("\n========== CREATE ACCOUNT ==========");

    System.out.println("1. Savings");
    System.out.println("2. Current");

    System.out.print("Choose Account Type : ");

    int choice = sc.nextInt();
    sc.nextLine();

    String accountType;

    switch (choice) {

      case 1:
        accountType = "SAVINGS";
        break;

      case 2:
        accountType = "CURRENT";
        break;

      default:
        System.out.println("Invalid Choice.");
        return;
    }

    long accountNo = AccountNumberGenerator.generateAccountNumber();

    Account account = new Account(
        accountNo,
        loggedInCustomer.getCustomerId(),
        0,
        accountType);

    boolean success = accountService.createAccount(account);

    if (success) {

      System.out.println("\nAccount Created Successfully.");

      System.out.println("Account Number : " + accountNo);

    }

    else {

      System.out.println("Failed To Create Account.");

    }
  }

}
