package achwie.hystrixdemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Component
public class AuthService {
  private final UserRepository userRepo;

  @Autowired
  public AuthService(UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public User findUser(String username, String password) {
    final User user = userRepo.findUserByCredentials(username, password);

    return (user != null) ? user : User.ANONYMOUS;
  }
}
