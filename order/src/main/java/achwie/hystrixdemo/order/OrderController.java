package achwie.hystrixdemo.order;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
  private static final ResponseEntity<String> RESPONSE_SUCCESS = new ResponseEntity<String>("OK", HttpStatus.OK);
  private final OrderService orderService;
  private final AuthService authService;

  @Autowired
  public OrderController(OrderService orderService, AuthService authService) {
    this.orderService = orderService;
    this.authService = authService;
  }

  @RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
  public ResponseEntity<List<Order>> viewOrders(@PathVariable String sessionId) {
    final String sessionUserId = authService.getUserIdForSession(sessionId);

    final List<Order> orders;
    if (sessionUserId != null) {
      orders = orderService.getOrdersForUser(sessionUserId);
    } else {
      orders = Collections.emptyList();
    }

    return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
  }

  @RequestMapping(value = "/{sessionId}", method = RequestMethod.POST)
  public ResponseEntity<String> placeOrder(@PathVariable String sessionId, @RequestBody Cart cart) {
    final String sessionUserId = authService.getUserIdForSession(sessionId);

    if (sessionUserId == null)
      return new ResponseEntity<String>("Insufficient privileges for operation!", HttpStatus.UNAUTHORIZED);

    if (cart == null || cart.isEmpty())
      return RESPONSE_SUCCESS;

    final Order order = createOrderFromCart(sessionUserId, cart);

    final boolean success = orderService.placeOrder(order);
    if (success) {
      return RESPONSE_SUCCESS;
    } else {
      return new ResponseEntity<String>("ERROR: Order could not be processed! Maybe some requested items were unavailable.", HttpStatus.CONFLICT);
    }
  }

  private Order createOrderFromCart(String userId, Cart cart) {
    final Order order = new Order(userId);

    for (CartItem cartItem : cart.getItems())
      order.addOrderItem(createOrderItemFromCartItem(cartItem));

    return order;
  }

  private OrderItem createOrderItemFromCartItem(CartItem cartItem) {
    return new OrderItem(cartItem.getProductId(), cartItem.getProductName(), cartItem.getQuantity());
  }
}
