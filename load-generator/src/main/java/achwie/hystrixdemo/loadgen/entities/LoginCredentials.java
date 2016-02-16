package achwie.hystrixdemo.loadgen.entities;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class LoginCredentials {
  public static LoginCredentials USER_TEST = new LoginCredentials("test", "test");
  private final String username;
  private final String password;

  public LoginCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String toString() {
    return "LoginCredentials[username: " + getUsername() + ", password: " + getPassword() + "]";
  }
}
