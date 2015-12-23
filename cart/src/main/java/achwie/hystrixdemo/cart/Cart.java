package achwie.hystrixdemo.cart;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class Cart {
  private final List<CartItem> cartItems;

  public Cart(List<CartItem> cartItems) {
    this.cartItems = cartItems;
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(cartItems);
  }
}
