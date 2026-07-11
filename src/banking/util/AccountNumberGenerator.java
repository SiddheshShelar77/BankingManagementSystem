package banking.util;

public class AccountNumberGenerator {
  private static long nextAccountNumber = 1000000001L;

  public static long generateAccountNumber() {
    return nextAccountNumber++;
  }
}
