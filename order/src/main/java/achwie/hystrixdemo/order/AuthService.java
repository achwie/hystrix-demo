package achwie.hystrixdemo.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

  public String getUserIdForSession(String sessionId) {
    final String url = authServiceBaseUrl + "/" + sessionId;

    final User user = restTemplate.getForObject(url, User.class);
    return user.userId;
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
