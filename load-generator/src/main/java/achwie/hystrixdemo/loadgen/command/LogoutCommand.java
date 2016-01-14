package achwie.hystrixdemo.loadgen.command;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 14.01.2016, Achim Wiedemann
 *
 */
public class LogoutCommand implements Callable<Void> {
  private final String logoutUrl;

  public LogoutCommand(String authServiceBaseUrl, String sessionId) {
    this.logoutUrl = authServiceBaseUrl + "/" + sessionId;
  }

  @Override
  public Void call() throws Exception {
    try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {

      httpClient.execute(new HttpDelete(logoutUrl));

      return null;
    }
  }

  public static void main(String[] args) throws Exception {
    final String sessionId = "1";
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String authServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_AUTH_BASEURL);

    new LogoutCommand(authServiceBaseUrl, sessionId).call();
  }
}
