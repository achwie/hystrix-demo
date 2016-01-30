package achwie.hystrixdemo.auth;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class LogoutCommand extends HystrixRestCommand<Void> {
  private final String url;

  protected LogoutCommand(RestOperations restOps, String authServiceBaseUrl, String sessionId) {
    super(CommandGroup.AUTH_LOGOUT, restOps);
    this.url = authServiceBaseUrl + "/" + sessionId;
  }

  @Override
  protected Void run() throws Exception {
    try {
      restOps.delete(url);
      return null;
    } catch (RestClientException e) {
      LOG.error("Could not log out user at " + url, e);
      throw e;
    }
  }
}
