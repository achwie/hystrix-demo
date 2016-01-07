package achwie.hystrixdemo.auth;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Component
public class AuthService {
  private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
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
   * @throws IOException If something went wrong with the remote call.
   */
  public User login(String username, String password) throws IOException {
    final String sessionId = sessionService.getSessionId();
    final String url = authServiceBaseUrl + "/" + sessionId;

    try {
      return restTemplate.postForObject(url, new LoginRequest(username, password), User.class);
    } catch (HttpStatusCodeException e) {
      // Returns 406 for wrong credentials
      if (e.getStatusCode() != HttpStatus.NOT_ACCEPTABLE) {
        LOG.error("Unexpected response while trying to authenticate user {} at {} (status: {}, response body: '{}')", username, url, e.getStatusCode(),
            e.getResponseBodyAsString());
      }
      return User.ANONYMOUS;
    } catch (RestClientException e) {
      throw new IOException(String.format("Could not log in user %s at %s", username, url), e);
    }
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
  public void logout() throws IOException {
    final String sessionId = sessionService.getSessionId();
    final String url = authServiceBaseUrl + "/" + sessionId;

    try {
      restTemplate.delete(url);
    } catch (RestClientException e) {
      throw new IOException("Could not log out user at " + url, e);
    }
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
