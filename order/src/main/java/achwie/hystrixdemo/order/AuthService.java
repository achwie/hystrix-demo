package achwie.hystrixdemo.order;

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

  @Autowired
  public AuthService(@Value("${service.auth.baseurl}") String authServiceBaseUrl) {
    this.authServiceBaseUrl = authServiceBaseUrl;
  }

  /**
   * Returns the user ID for the given session ID.
   * 
   * @param sessionId
   * @return The user ID for the given session ID or {@code null} if there was
   *         no authenticated user associated with that session.
   */
  public String getUserIdForSession(String sessionId) {
    return new GetUserIdForSessionCommand(restTemplate, authServiceBaseUrl, sessionId).execute();
  }
}
