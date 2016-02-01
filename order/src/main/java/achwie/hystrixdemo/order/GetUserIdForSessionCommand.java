package achwie.hystrixdemo.order;

import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 01.02.2016, Achim Wiedemann
 *
 */
public class GetUserIdForSessionCommand extends HystrixRestCommand<String> {
  private final String url;

  protected GetUserIdForSessionCommand(RestOperations restOps, String authServiceBaseUrl, String sessionId) {
    super(CommandGroup.AUTH_GET_USERID_FOR_SESSION, restOps);
    this.url = authServiceBaseUrl + "/" + sessionId;
  }

  @Override
  protected String run() throws Exception {
    final User user = restOps.getForObject(url, User.class);
    return user.userId;
  }

  @Override
  protected String getFallback() {
    return null;
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
