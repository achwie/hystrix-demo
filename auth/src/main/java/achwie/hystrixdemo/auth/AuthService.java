package achwie.hystrixdemo.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Component
public class AuthService {
  private final Object lock = new Object();
  private final Map<String, User> sessions = new HashMap<>();
  private final UserRepository userRepo;

  @Autowired
  public AuthService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public User createSession(String sessionId, String username, String password) {
    final User user = userRepo.findUserByCredentials(username, password);

    if (user == null) {
      return User.ANONYMOUS;
    }

    synchronized (lock) {
      if (sessions.containsKey(sessionId))
        throw new IllegalStateException("Can't create new session for existing ID!");

      sessions.put(sessionId, user);
    }

    return user;
  }

  public void destroySession(String sessionId) {
    synchronized (lock) {
      sessions.remove(sessionId);
    }
  }

  public User getUserForSession(String sessionId) {
    final User user = sessions.get(sessionId);

    return (user != null) ? user : User.ANONYMOUS;
  }
}
