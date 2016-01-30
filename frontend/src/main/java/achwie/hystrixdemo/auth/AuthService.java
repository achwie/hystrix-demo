package achwie.hystrixdemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Component
public class AuthService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String authServiceBaseUrl;
  private final SessionService sessionService;

  @Autowired
  public AuthService(@Value("${service.auth.baseurl}") String authServiceBaseUrl, SessionService sessionService) {
    this.authServiceBaseUrl = authServiceBaseUrl;
    this.sessionService = sessionService;
  }

  /**
   * <p>
   * Authenticates the user with the given credentials and creates a session for
   * him. If the given session was already established for the user, it will be
   * picked up.
   * </p>
   * <p>
   * <strong>NOTE:</strong> This method must only be executed on the
   * request-thread!
   * </p>
   * 
   * @param username The user to create a session for
   * @param password The password to authenticate the user
   * @return The user object. If the user could not be authenticated, the
   *         {@link User#ANONYMOUS anonymous user} will be returned.
   */
  public User login(String username, String password) {
    final String sessionId = sessionService.getSessionId();

    return new LoginCommand(restTemplate, authServiceBaseUrl, sessionId, username, password).execute();
  }

  /**
   * <p>
   * Logs out the user in the given session. If the session does not exist,
   * nothing happens.
   * </p>
   * <p>
   * <strong>NOTE:</strong> This method must only be executed on the
   * request-thread!
   * </p>
   */
  public void logout() {
    final String sessionId = sessionService.getSessionId();
    new LogoutCommand(restTemplate, authServiceBaseUrl, sessionId).execute();
  }
}
