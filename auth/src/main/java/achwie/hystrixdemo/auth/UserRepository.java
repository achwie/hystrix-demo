package achwie.hystrixdemo.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import achwie.hystrixdemo.util.SimpleCsvReader;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
// DON'T DO THIS AT HOME!
@Component
public class UserRepository {
  private final Map<String, User> users = new HashMap<>();

  {
    try (final InputStream is = UserRepository.class.getResourceAsStream("/test-data-users.csv")) {
      SimpleCsvReader.readLines(is, values -> {
        if (values.length != 3)
          return;

        String id = values[0];
        String name = values[1];
        String pass = values[2];
        users.put(key(name, pass), new User(id, name));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public User findUserByCredentials(String username, String password) {
    final String key = key(username, password);

    return users.get(key);
  }

  private String key(String username, String password) {
    return username + ":" + password;
  }
}
