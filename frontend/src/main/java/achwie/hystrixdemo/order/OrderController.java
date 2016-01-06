package achwie.hystrixdemo.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.cart.Cart;
import achwie.hystrixdemo.cart.CartService;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
@Controller
public class OrderController {
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
    final Cart cart = cartService.getCart(sessionId);

    if (cart.isEmpty()) {
      // TODO: Log
      System.err.println("Can't place an order with an empty cart!");
      return "redirect:order-address";
    }

    final boolean success = orderService.placeOrder(sessionId, cart);

    if (success) {
      cartService.clearCart(sessionId);
    } else {
      // TODO: Handle failure
      System.out.println("Order could NOT be placed!");
      return "redirect:order-address";
    }

    return "redirect:order-placed";
  }

  @RequestMapping(value = "order-placed", method = RequestMethod.GET)
  public String orderPlaced() {
    return "order-placed";
  }

  @RequestMapping(value = "my-orders", method = RequestMethod.GET)
  public String viewOrders(Model model, HttpServletRequest req) {
    SessionService.ensureAuthenticatedUser(sessionService, "view my orders");
    final String sessionId = sessionService.getSessionId();
    final List<Order> ordersForUser = orderService.getOrdersForUser(sessionId);

    model.addAttribute("orders", ordersForUser);

    return "my-orders";
  }
}
