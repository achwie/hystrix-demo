package achwie.hystrixdemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
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
   * <strong>NOTE:</strong> This method must only be executed on the
   * request-thread!
   * </p>
   */
  public User login(String username, String password) {
    final String sessionId = sessionService.getSessionId();
    final String url = authServiceBaseUrl + "/" + sessionId;

    try {
      return restTemplate.postForObject(url, new LoginRequest(username, password), User.class);
    } catch (HttpClientErrorException e) {
      // TODO: Log
      // Only log on unexpected error - 406 is returned for wrong credentials
      if (e.getStatusCode() != HttpStatus.NOT_ACCEPTABLE)
        System.err.println("ERROR: " + e.getStatusText());

      return User.ANONYMOUS;
    }
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be executed on the
   * request-thread!
   * </p>
   */
  public void logout() {
    final String sessionId = sessionService.getSessionId();
    final String url = authServiceBaseUrl + "/" + sessionId;

    restTemplate.delete(url);
  }

  // ---------------------------------------------------------------------------
  private static final class LoginRequest {
    @SuppressWarnings("unused") // Field gets serialized
    public final String username;
    @SuppressWarnings("unused") // Field gets serialized
    public final String password;

    public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }
}
