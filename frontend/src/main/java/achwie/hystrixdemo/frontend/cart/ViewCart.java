package achwie.hystrixdemo.frontend.cart;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class ViewCart {
  private final String userId;
  private final List<CartItem> cartItems;

  public ViewCart(String userId, List<CartItem> cartItems) {
    this.userId = userId;
    this.cartItems = cartItems;
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(cartItems);
  }

  public int getTotalItemCount() {
    int totalCount = 0;

    for (CartItem cartItem : cartItems)
      totalCount += cartItem.getQuantity();

    return totalCount;
  }

  public String getUserId() {
    return userId;
  }
}
