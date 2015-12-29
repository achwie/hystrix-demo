package achwie.hystrixdemo.order;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  public boolean placeOrder(String sessionId, Cart cart) {
    final String url = orderServiceBaseUrl + "/" + sessionId;

    final ResponseEntity<String> response = restTemplate.postForEntity(url, cart, String.class);

    return (response.getStatusCode() == HttpStatus.OK);
  }

  public List<Order> getOrdersForUser(String sessionId) {
    final String url = orderServiceBaseUrl + "/" + sessionId;

    final Order[] orders = restTemplate.getForObject(url, Order[].class);

    return Arrays.asList(orders);
  }
}
