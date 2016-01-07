package achwie.hystrixdemo.order;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.auth.User;
import achwie.hystrixdemo.cart.Cart;
import achwie.hystrixdemo.cart.CartService;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
@Controller
public class OrderController {
  private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
  private final OrderService orderService;
  private final CartService cartService;
  private final SessionService sessionService;

  @Autowired
  public OrderController(OrderService orderService, CartService cartService, SessionService sessionService) {
    this.orderService = orderService;
    this.cartService = cartService;
    this.sessionService = sessionService;
  }

  @RequestMapping(value = "order-address", method = RequestMethod.GET)
  public String enterShippingAddress() {
    SessionService.ensureAuthenticatedUser(sessionService, "enter shipping address");

    return "order-address";
  }

  @RequestMapping(value = "place-order", method = RequestMethod.POST)
  public String placeOrder(Model model, HttpServletRequest req) {
    SessionService.ensureAuthenticatedUser(sessionService, "place order");

    final String sessionId = sessionService.getSessionId();
    Cart cart;
    try {
      cart = cartService.getCart(sessionId);
    } catch (IOException e) {
      LOG.error(e.getMessage());
      cart = Cart.EMPTY_CART;
    }

    if (cart.isEmpty()) {
      LOG.warn("Can't place an order with an empty cart!");
      return "redirect:order-address";
    }

    boolean success;
    try {
      success = orderService.placeOrder(sessionId, cart);

      if (success) {
        cartService.clearCart(sessionId);
        return "redirect:order-placed";
      } else {
        final User user = sessionService.getSessionUser();
        LOG.warn("Order with session {} for user {} could not be placed (this might be because of insufficient availability of some products)!", sessionId,
            user.getUserName());
      }
    } catch (IOException e) {
      LOG.error(e.getMessage());
    }

    // TODO: User feedback
    return "redirect:order-address";
  }

  @RequestMapping(value = "order-placed", method = RequestMethod.GET)
  public String orderPlaced() {
    return "order-placed";
  }

  @RequestMapping(value = "my-orders", method = RequestMethod.GET)
  public String viewOrders(Model model, HttpServletRequest req) {
    SessionService.ensureAuthenticatedUser(sessionService, "view my orders");
    final String sessionId = sessionService.getSessionId();
    List<Order> ordersForUser;
    try {
      ordersForUser = orderService.getOrdersForUser(sessionId);
    } catch (IOException e) {
      LOG.error(e.getMessage());
      ordersForUser = Collections.emptyList();
    }

    model.addAttribute("orders", ordersForUser);

    return "my-orders";
  }
}
