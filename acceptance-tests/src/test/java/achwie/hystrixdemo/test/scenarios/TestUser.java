package achwie.hystrixdemo.test.scenarios;

public enum TestUser {
  USER_JOHN("john", "doe"), USER_TEST("test", "test");
  public final String username;
  public final String password;

  private TestUser(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
