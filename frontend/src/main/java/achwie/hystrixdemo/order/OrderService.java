package achwie.hystrixdemo.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import achwie.hystrixdemo.cart.Cart;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 *
 */
@Component
public class OrderService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String orderServiceBaseUrl;

  @Autowired
  public OrderService(@Value("${service.order.baseurl}") String orderServiceBaseUrl) {
    this.orderServiceBaseUrl = orderServiceBaseUrl;
  }

  /**
   * Places an order with the contents in the given cart for the user associated
   * with the given session ID.
   * 
   * @param sessionId The session ID to place the order for
   * @param cart The cart with the items to order
   * @return {@code true} if the order was successful, {@code false} else (e.g.
   *         because there were not enough items on stock).
   */
  public boolean placeOrder(String sessionId, Cart cart) {
    return new PlaceOrderCommand(restTemplate, orderServiceBaseUrl, sessionId, cart).execute();
  }

  /**
   * Returns the order history for the session's user.
   * 
   * @param sessionId The session of the user to get the orders for.
   * @return The list with orders made by the user or an empty list if the user
   *         didn't make any orders so far.
   */
  public List<Order> getOrdersForUser(String sessionId) {
    return new GetOrdersForUserCommand(restTemplate, orderServiceBaseUrl, sessionId).execute();
  }
}
