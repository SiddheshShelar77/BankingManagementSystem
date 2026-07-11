package banking.exception;

public class InsufficientBalanceException extends Exception {
  // constructor
  public InsufficientBalanceException(String message) {
    super(message);
  }
}
