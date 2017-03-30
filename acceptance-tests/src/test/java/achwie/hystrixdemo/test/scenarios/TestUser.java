package achwie.hystrixdemo.test.scenarios;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public enum TestUser {
  USER_JOHN("john", "doe"), USER_TEST("test", "test");
  public final String username;
  public final String password;

  private TestUser(String username, String password) {
    this.username = username;
    this.password = password;
  }

  @Override
  public String toString() {
    return username;
  }
}
