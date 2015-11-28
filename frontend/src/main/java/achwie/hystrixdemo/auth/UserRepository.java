package achwie.hystrixdemo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
// DON'T DO THIS AT HOME!
@Component
public class UserRepository {
  private final Map<String, User> users = new HashMap<>();

  public UserRepository() {
    initTestData();
  }

  public User findUserByCredentials(String username, String password) {
    final String key = key(username, password);

    return users.get(key);
  }

  private String key(String username, String password) {
    return username + ":" + password;
  }

  private void initTestData() {
    final User john = new User("1", "john");
    final User jane = new User("2", "jane");
    final User test = new User("3", "test");

    users.put(key(john.getUserName(), "doe"), john);
    users.put(key(jane.getUserName(), "doe"), jane);
    users.put(key(test.getUserName(), "test"), test);
  }
}
