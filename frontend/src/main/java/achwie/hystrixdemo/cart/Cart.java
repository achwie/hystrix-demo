package achwie.hystrixdemo.cart;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 23.12.2015, Achim Wiedemann
 */
public class Cart {
  private final List<CartItem> cartItems;

  @JsonCreator
  public Cart(@JsonProperty("items") List<CartItem> cartItems) {
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

  public boolean isEmpty() {
    return getTotalItemCount() < 1;
  }
}
