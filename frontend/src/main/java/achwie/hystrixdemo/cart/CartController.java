package achwie.hystrixdemo.cart;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.auth.User;
import achwie.hystrixdemo.catalog.CatalogItem;
import achwie.hystrixdemo.catalog.CatalogService;
import achwie.hystrixdemo.stock.StockService;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Controller
public class CartController {
  private static final Logger LOG = LoggerFactory.getLogger(CartController.class);
  private final CartService cartService;
  private final CatalogService catalogService;
  private final StockService stockService;
  private final SessionService sessionService;

  @Autowired
  public CartController(CartService cartService, CatalogService catalogService, StockService stockService, SessionService sessionService) {
    this.cartService = cartService;
    this.catalogService = catalogService;
    this.stockService = stockService;
    this.sessionService = sessionService;
  }

  @RequestMapping(value = "view-cart", method = RequestMethod.GET)
  public String viewCart(Model model, HttpServletRequest req) {
    final User user = sessionService.getSessionUser();
    final String sessionId = sessionService.getSessionId();
    final Cart cart = cartService.getCart(sessionId);

    model.addAttribute("cart", cart);
    model.addAttribute("user", user);

    return "cart";
  }

  @RequestMapping(value = "add-to-cart", method = RequestMethod.POST)
  public String addToCart(@ModelAttribute CartItem cartItem, HttpServletRequest req) {
    final String sessionId = sessionService.getSessionId();
    final CatalogItem catalogItem = catalogService.findById(cartItem.getProductId());

    final int itemQuantity = stockService.getStockQuantity(cartItem.getProductId());

    if (catalogItem != null && itemQuantity > 0) {
      cartService.addToCart(sessionId, catalogItem, cartItem.getQuantity());
    } else {
      // TODO: User feedback
      LOG.error("Couldn't add unknown product to cart (product ID: {})!", cartItem.getProductId());
    }

    return "redirect:catalog";
  }
}
