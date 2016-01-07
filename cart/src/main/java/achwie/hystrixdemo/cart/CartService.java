package achwie.hystrixdemo.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private CartRepository cartRepo;

  @Autowired
  public CartService(CartRepository cartRepo) {
    this.cartRepo = cartRepo;
  }

  public void addToCart(String cartId, String productId, String productName, int quantity) {
    cartRepo.addToCart(cartId, productId, productName, quantity);
  }

  /**
   * Returns the cart for a given ID.
   * 
   * @param cartId The cart ID.
   * @return The cart for the given ID or {@code null} if not existent.
   */
  public Cart getCart(String cartId) {
    List<CartItem> cartItems = cartRepo.getItemsForCart(cartId);

    return !cartItems.isEmpty() ? new Cart(cartItems) : null;
  }

  public void clearCart(String cartId) {
    cartRepo.clearCart(cartId);
  }
}
