package achwie.hystrixdemo.frontend.cart;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.frontend.auth.IdentityService;
import achwie.hystrixdemo.frontend.product.Product;
import achwie.hystrixdemo.frontend.product.ProductService;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Controller
public class CartController {
  private final CartService cartService;
  private final ProductService productService;
  private final IdentityService idService;

  @Autowired
  public CartController(CartService cartService, ProductService productService, IdentityService idService) {
    this.cartService = cartService;
    this.productService = productService;
    this.idService = idService;
  }

  @RequestMapping(value = "view-cart", method = RequestMethod.GET)
  public String viewCart(Model model, HttpServletRequest req) {
    final String userId = idService.getUserId(req);
    final ViewCart cart = cartService.getCart(userId);

    model.addAttribute("cart", cart);

    return "cart";
  }

  @RequestMapping(value = "add-to-cart", method = RequestMethod.POST)
  public String addToCart(@ModelAttribute ViewCartItem cartItem, Model model, HttpServletRequest req) {
    final String userId = idService.getUserId(req);
    final Product product = productService.getById(cartItem.getProductId());

    if (product != null) {
      cartService.addToCart(userId, product, cartItem.getQuantity());
    } else {
      // TODO: User feedback
      System.err.println("Couldn't add unknown product to cart!" + cartItem);
    }

    return "redirect:catalog";
  }
}
