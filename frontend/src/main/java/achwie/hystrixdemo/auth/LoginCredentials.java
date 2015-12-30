package achwie.hystrixdemo.auth;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
public class LoginCredentials {
  private String username;
  private String password;
  private String referrer;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getReferrer() {
    return referrer;
  }

  public void setReferrer(String referrer) {
    this.referrer = referrer;
  }
}
