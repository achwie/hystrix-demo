package achwie.hystrixdemo.frontend.auth;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
public class User {
  public static final User ANONYMOUS = new User();
  private final String userId;
  private final String userName;

  private User() {
    userId = null; // Anonymous user
    userName = "Anonymous";
  }

  public User(String userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  public String getId() {
    return userId;
  }

  public boolean isLoggedIn() {
    return this != ANONYMOUS;
  }

  public String getUserName() {
    return userName;
  }
}
