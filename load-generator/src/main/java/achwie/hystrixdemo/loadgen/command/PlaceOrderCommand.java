package achwie.hystrixdemo.loadgen.command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.Cart;
import achwie.hystrixdemo.loadgen.entities.CartItem;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class PlaceOrderCommand implements Callable<Void> {
  private final String orderUrl;
  private final Cart cart;

  public PlaceOrderCommand(String orderServiceBaseUrl, String sessionId, Cart cart) {
    this.orderUrl = orderServiceBaseUrl + "/" + sessionId;
    this.cart = cart;
  }

  @Override
  public Void call() throws Exception {
    try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final StringEntity cartEntity = new StringEntity(cart.toJsonString());
      cartEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

      final HttpPost httpPost = new HttpPost(orderUrl);
      httpPost.setEntity(cartEntity);

      httpClient.execute(httpPost);

      return null;
    }
  }

  public static void main(String[] args) throws Exception {
    final List<CartItem> cartItems = new ArrayList<>();
    cartItems.add(new CartItem("1", "Product 1", 2));
    cartItems.add(new CartItem("2", "Product 2", 3));
    cartItems.add(new CartItem("3", "Product 3", 4));
    final Cart cart = new Cart(cartItems);

    final String sessionId = "1";
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String orderServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_ORDER_BASEURL);
    new PlaceOrderCommand(orderServiceBaseUrl, sessionId, cart).call();
  }
}
