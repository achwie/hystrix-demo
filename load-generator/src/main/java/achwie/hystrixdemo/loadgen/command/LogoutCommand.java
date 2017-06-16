package achwie.hystrixdemo.loadgen.command;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 14.01.2016, Achim Wiedemann
 *
 */
public class LogoutCommand implements HttpClientCommand<Void> {
  private final String logoutUrl;

  public LogoutCommand(String frontendBaseUrl) {
    this.logoutUrl = frontendBaseUrl + "/logout";
  }

  @Override
  public Void run(HttpClient httpClient) throws Exception {
    final HttpResponse resp = httpClient.execute(new HttpGet(logoutUrl));
    // Even if we don't care for the content: make sure resources are released!
    // We consume the HTTP entity in contrast to simply closing the response,
    // because this way HttpClient will try to re-use the connection, whereas
    // when closing the response the connection will be discarded. When doing
    // lots of fast subsequent requests this prevents the OS from using up
    // all it's client-sockets, which will manifest itself in a
    // SocketException. This is subtle, but it's mentioned in the docs at
    // https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
    EntityUtils.consume(resp.getEntity());

    return null;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String authServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_AUTH_BASEURL);

      new LogoutCommand(authServiceBaseUrl).run(httpClient);
    }
  }
}
