package achwie.hystrixdemo.cart;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private static final Cart EMPTY_CART = new Cart(Collections.emptyList());
  private CartRepository cartRepo;

  @Autowired
  public CartService(CartRepository cartRepo) {
    this.cartRepo = cartRepo;
  }

  public void addToCart(String cartId, String productId, int quantity) {
    cartRepo.addToCart(cartId, productId, quantity);
  }

  public Cart getCart(String cartId) {
    List<CartItem> cartItems = cartRepo.getItemsForCart(cartId);

    return !cartItems.isEmpty() ? new Cart(cartItems) : EMPTY_CART;
  }

  public void clearCart(String cartId) {
    cartRepo.clearCart(cartId);
  }
}
