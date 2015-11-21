package achwie.hystrixdemo.frontend.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.frontend.auth.IdentityService;
import achwie.hystrixdemo.frontend.cart.CartItem;
import achwie.hystrixdemo.frontend.cart.CartService;
import achwie.hystrixdemo.frontend.cart.ViewCart;
import achwie.hystrixdemo.frontend.product.Product;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
@Controller
public class OrderController {
  private final OrderService orderService;
  private final CartService cartService;
  private final IdentityService idService;

  @Autowired
  public OrderController(OrderService orderService, CartService cartService, IdentityService idService) {
    this.orderService = orderService;
    this.cartService = cartService;
    this.idService = idService;
  }

  @RequestMapping(value = "order-address", method = RequestMethod.GET)
  public String enterShippingAddress(Model model) {
    return "order-address";
  }

  @RequestMapping(value = "place-order", method = RequestMethod.POST)
  public String placeOrder(Model model, HttpServletRequest req) {
    final String userId = idService.getUserId(req);
    final ViewCart cart = cartService.getCart(userId);

    final Order order = createOrderFromCart(cart);
    final boolean success = orderService.placeOrder(order);

    if (success) {
      cartService.clearCart(userId);
    } else {
      // TODO: Handle failure
      System.out.println("Order could NOT be placed!");
    }

    return "redirect:order-placed";
  }

  @RequestMapping(value = "order-placed", method = RequestMethod.GET)
  public String orderPlaced(Model model) {
    return "order-placed";
  }

  @RequestMapping(value = "my-orders", method = RequestMethod.GET)
  public String viewOrders(Model model, HttpServletRequest req) {
    final String userId = idService.getUserId(req);
    final List<Order> ordersForUser = orderService.getOrdersForUser(userId);

    model.addAttribute("orders", ordersForUser);

    return "my-orders";
  }

  private Order createOrderFromCart(ViewCart cart) {
    final Order order = new Order(cart.getUserId());

    for (CartItem cartItem : cart.getItems())
      order.addOrderItem(createOrderItemFromCartItem(cartItem));

    return order;
  }

  private OrderItem createOrderItemFromCartItem(CartItem cartItem) {
    final Product product = cartItem.getProduct();
    final int quantity = cartItem.getQuantity();

    return new OrderItem(product.getId(), product.getName(), quantity);
  }
}
