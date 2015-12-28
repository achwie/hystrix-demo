package achwie.hystrixdemo.catalog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.auth.User;
import achwie.hystrixdemo.cart.CartService;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {
  private final CatalogService catalogService;
  private final CartService cartService;
  private final SessionService sessionService;

  @Autowired
  public CatalogController(CatalogService catalogService, CartService cartService, SessionService sessionService) {
    this.catalogService = catalogService;
    this.cartService = cartService;
    this.sessionService = sessionService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getPerson(Model model, HttpServletRequest req) {
    final User user = sessionService.getSessionUser();
    final String sessionId = sessionService.getSessionId();
    model.addAttribute("products", catalogService.findAllProducts());
    model.addAttribute("cart", cartService.getCart(sessionId));
    model.addAttribute("user", user);
    return "catalog";
  }
}
