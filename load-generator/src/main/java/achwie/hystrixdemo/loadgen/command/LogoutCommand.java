package achwie.hystrixdemo.loadgen.command;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 14.01.2016, Achim Wiedemann
 *
 */
public class LogoutCommand implements Callable<Void> {
  private final CloseableHttpClient httpClient;
  private final String logoutUrl;

  public LogoutCommand(CloseableHttpClient httpClient, String authServiceBaseUrl, String sessionId) {
    this.httpClient = httpClient;
    this.logoutUrl = authServiceBaseUrl + "/" + sessionId;
  }

  @Override
  public Void call() throws Exception {
    final CloseableHttpResponse resp = httpClient.execute(new HttpDelete(logoutUrl));
    // Even if we don't care for the content: make sure resources are released!
    // We consume the HTTP entity in contrast to simply closing the response,
    // because this way HttpClient will try to re-use the connection, whereas
    // when closing the response the connection will be discarded. When doing
    // lots of fast subsequent requests this prevents you from the OS using up
    // all it's client-sockets, which will manifest it self in a
    // SocketException. This is subtle, but it's mentioned in the docs at
    // https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
    EntityUtils.consume(resp.getEntity());

    return null;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final String sessionId = "1";
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String authServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_AUTH_BASEURL);

      new LogoutCommand(httpClient, authServiceBaseUrl, sessionId).call();
    }
  }
}
