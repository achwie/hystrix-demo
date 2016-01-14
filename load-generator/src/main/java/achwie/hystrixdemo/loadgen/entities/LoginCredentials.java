package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONObject;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class LoginCredentials {
  public static final String FIELD_USERNAME = "username";
  public static final String FIELD_PASSWORD = "password";
  private final String username;
  private final String password;

  public LoginCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String toJsonString() {
    JSONObject json = new JSONObject();

    json.put(FIELD_USERNAME, username);
    json.put(FIELD_PASSWORD, password);

    return json.toString();
  }

  public String toString() {
    return "LoginCredentials[username: " + username + ", password: " + password + "]";
  }
}
