package achwie.hystrixdemo.cart;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class Cart {
  public static final Cart EMPTY_CART = new Cart(Collections.emptyList());
  private final List<CartItem> cartItems;

  @JsonCreator
  Cart(@JsonProperty("items") List<CartItem> cartItems) {
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
