package achwie.hystrixdemo.order;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
   * @throws IOException If something went wrong with the remote call.
   */
  public String getUserIdForSession(String sessionId) throws IOException {
    final String url = authServiceBaseUrl + "/" + sessionId;

    try {
      final User user = restTemplate.getForObject(url, User.class);
      return user.userId;
    } catch (RestClientException e) {
      throw new IOException("Could not get user at " + url, e);
    }
  }

  // ---------------------------------------------------------------------------
  private static final class User {
    public final String userId;

    @JsonCreator
    public User(@JsonProperty("id") String userId) {
      this.userId = userId;
    }
  }
}
