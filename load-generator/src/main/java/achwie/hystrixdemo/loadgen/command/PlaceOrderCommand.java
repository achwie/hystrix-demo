package achwie.hystrixdemo.loadgen.command;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class PlaceOrderCommand implements Callable<Void> {
  private final CloseableHttpClient httpClient;
  private final String orderUrl;

  public PlaceOrderCommand(CloseableHttpClient httpClient, String frontendBaseUrl) {
    this.httpClient = httpClient;
    this.orderUrl = frontendBaseUrl + "/place-order";
  }

  @Override
  public Void call() throws Exception {
    final HttpPost httpPost = new HttpPost(orderUrl);

    final CloseableHttpResponse response = httpClient.execute(httpPost);
    // Even if we don't care for the content: make sure resources are released!
    // We consume the HTTP entity in contrast to simply closing the response,
    // because this way HttpClient will try to re-use the connection, whereas
    // when closing the response the connection will be discarded. When doing
    // lots of fast subsequent requests this prevents you from the OS using up
    // all it's client-sockets, which will manifest it self in a
    // SocketException. This is subtle, but it's mentioned in the docs at
    // https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
    EntityUtils.consume(response.getEntity());

    return null;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {

      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String frontendBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);
      new LoginCommand(httpClient, frontendBaseUrl, new LoginCredentials("test", "test")).call();
      new AddToCartCommand(httpClient, frontendBaseUrl, "2", 12).call();
      new PlaceOrderCommand(httpClient, frontendBaseUrl).call();
    }
  }
}
