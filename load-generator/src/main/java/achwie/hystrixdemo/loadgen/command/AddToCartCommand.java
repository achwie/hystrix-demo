package achwie.hystrixdemo.loadgen.command;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.loadgen.entities.CartItem;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class AddToCartCommand implements Callable<Void> {
  private final CloseableHttpClient httpClient;
  private final String addToCartUrl;
  private final CartItem itemToAdd;

  public AddToCartCommand(CloseableHttpClient httpClient, String cartServiceBaseUrl, String cartId, CartItem itemToAdd) {
    this.httpClient = httpClient;
    this.addToCartUrl = cartServiceBaseUrl + "/" + cartId;
    this.itemToAdd = itemToAdd;
  }

  @Override
  public Void call() throws Exception {
    final StringEntity entity = new StringEntity(itemToAdd.toJson());
    entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

    final HttpPost httpPost = new HttpPost(addToCartUrl);
    httpPost.setEntity(entity);

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
    try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final String sessionId = "1";
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String cartServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_CART_BASEURL);

      final CartItem itemToAdd = new CartItem("A123", "Something awesome", 1);

      new AddToCartCommand(httpClient, cartServiceBaseUrl, sessionId, itemToAdd).call();
    }
  }
}
