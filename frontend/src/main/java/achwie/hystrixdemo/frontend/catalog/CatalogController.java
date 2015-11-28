package achwie.hystrixdemo.frontend.catalog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.frontend.auth.IdentityService;
import achwie.hystrixdemo.frontend.auth.User;
import achwie.hystrixdemo.frontend.cart.CartService;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {
  private final CatalogService catalogService;
  private final CartService cartService;
  private final IdentityService idService;

  @Autowired
  public CatalogController(CatalogService catalogService, CartService cartService, IdentityService idService) {
    this.catalogService = catalogService;
    this.cartService = cartService;
    this.idService = idService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getPerson(Model model, HttpServletRequest req) {
    final User user = idService.getSessionUser();
    final String sessionId = idService.getSessionId();
    model.addAttribute("products", catalogService.findAllProducts());
    model.addAttribute("cart", cartService.getCart(sessionId));
    model.addAttribute("user", user);
    return "catalog";
  }
}
