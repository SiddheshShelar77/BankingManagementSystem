package banking.util;

public class InputValidator {
  public static boolean isValidEmail(String email) {
    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    return email.matches(regex);
  }

  public static boolean isValidPhone(String phone) {
    return phone.matches("\\d{10}");
  }

  public static boolean isValidPassword(String password) {
    return password.length() >= 6;
  }
}
