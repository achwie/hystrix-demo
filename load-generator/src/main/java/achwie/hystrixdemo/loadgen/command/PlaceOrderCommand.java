package achwie.hystrixdemo.loadgen.command;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
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
public class PlaceOrderCommand implements HttpClientCommand<Void> {
  private final String orderUrl;

  public PlaceOrderCommand(String frontendBaseUrl) {
    this.orderUrl = frontendBaseUrl + "/place-order";
  }

  @Override
  public Void run(HttpClient httpClient) throws Exception {
    final HttpPost httpPost = new HttpPost(orderUrl);

    final HttpResponse response = httpClient.execute(httpPost);
    // Even if we don't care for the content: make sure resources are released!
    // We consume the HTTP entity in contrast to simply closing the response,
    // because this way HttpClient will try to re-use the connection, whereas
    // when closing the response the connection will be discarded. When doing
    // lots of fast subsequent requests this prevents the OS from using up
    // all it's client-sockets, which will manifest itself in a
    // SocketException. This is subtle, but it's mentioned in the docs at
    // https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
    EntityUtils.consume(response.getEntity());

    return null;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {

      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String frontendBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);
      new LoginCommand(frontendBaseUrl, LoginCredentials.USER_TEST).run(httpClient);
      new AddToCartCommand(frontendBaseUrl, "2", 12).run(httpClient);
      new PlaceOrderCommand(frontendBaseUrl).run(httpClient);
    }
  }
}
