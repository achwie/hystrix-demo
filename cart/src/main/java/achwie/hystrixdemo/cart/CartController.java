package achwie.hystrixdemo.cart;

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
 * @author 11.11.2015, Achim Wiedemann
 */
@RestController
@RequestMapping("/cart")
public class CartController {
  private final CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
  public ResponseEntity<Cart> viewCart(@PathVariable String cartId) {
    final Cart cart = cartService.getCart(cartId);

    final HttpStatus code = (cart != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

    return new ResponseEntity<Cart>(cart, code);
  }

  @RequestMapping(value = "/{cartId}", method = RequestMethod.POST)
  public ResponseEntity<String> addToCart(@PathVariable String cartId, @RequestBody CartItem cartItem) {
    if (cartItem != null && cartItem.getProductId() != null) {
      cartService.addToCart(cartId, cartItem.getProductId(), cartItem.getQuantity());
      return new ResponseEntity<String>("OK", HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Invalid request!", HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
  public void clearCart(@PathVariable String cartId) {
    cartService.clearCart(cartId);
  }

}
