package achwie.hystrixdemo.loadgen.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.loadgen.entities.Cart;
import achwie.hystrixdemo.loadgen.entities.CartItem;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class PlaceOrderCommand implements Callable<Void> {
  private final CloseableHttpClient httpClient;
  private final String orderUrl;
  private final Cart cart;

  public PlaceOrderCommand(CloseableHttpClient httpClient, String orderServiceBaseUrl, String sessionId, Cart cart) {
    this.httpClient = httpClient;
    this.orderUrl = orderServiceBaseUrl + "/" + sessionId;
    this.cart = cart;
  }

  @Override
  public Void call() throws Exception {
    final StringEntity cartEntity = new StringEntity(cart.toJsonString());
    cartEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

    final HttpPost httpPost = new HttpPost(orderUrl);
    httpPost.setEntity(cartEntity);

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
      final List<CartItem> cartItems = new ArrayList<>();
      cartItems.add(new CartItem("1", "Product 1", 2));
      cartItems.add(new CartItem("2", "Product 2", 3));
      cartItems.add(new CartItem("3", "Product 3", 4));
      final Cart cart = new Cart(cartItems);

      final String sessionId = "1";
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String orderServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_ORDER_BASEURL);
      new PlaceOrderCommand(httpClient, orderServiceBaseUrl, sessionId, cart).call();
    }
  }
}
