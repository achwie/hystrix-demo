package achwie.hystrixdemo.order;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import achwie.hystrixdemo.cart.Cart;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 *
 */
@Component
public class OrderService {
  private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
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
   * @throws IOException If something went wrong with the remote call.
   */
  public boolean placeOrder(String sessionId, Cart cart) throws IOException {
    final String url = orderServiceBaseUrl + "/" + sessionId;

    try {
      restTemplate.postForObject(url, cart, String.class);
      return true;
    } catch (HttpStatusCodeException e) {
      // Returns 409 for status errors, e.g. if not enough items were available
      // anymore
      if (e.getStatusCode() != HttpStatus.CONFLICT) {
        LOG.error("Unexpected response while placing order at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
      }

      return false;
    } catch (RestClientException e) {
      throw new IOException("Could not place order at " + url, e);
    }
  }

  /**
   * Returns the order history for the session's user.
   * 
   * @param sessionId The session of the user to get the orders for.
   * @return The list with orders made by the user or an empty list if the user
   *         didn't make any orders so far.
   * @throws IOException If something went wrong with the remote call.
   */
  public List<Order> getOrdersForUser(String sessionId) throws IOException {
    final String url = orderServiceBaseUrl + "/" + sessionId;

    try {
      final Order[] orders = restTemplate.getForObject(url, Order[].class);
      return Arrays.asList(orders);
    } catch (RestClientException e) {
      throw new IOException("Could not get order for user at " + url, e);
    }
  }
}
