package achwie.hystrixdemo.cart;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class ViewCart {
  private final List<ViewCartItem> cartItems;

  @JsonCreator
  public ViewCart(@JsonProperty("items") List<ViewCartItem> cartItems) {
    this.cartItems = cartItems;
  }

  public List<ViewCartItem> getItems() {
    return Collections.unmodifiableList(cartItems);
  }

  public int getTotalItemCount() {
    int totalCount = 0;

    for (ViewCartItem cartItem : cartItems)
      totalCount += cartItem.getQuantity();

    return totalCount;
  }

  public boolean isEmpty() {
    return getTotalItemCount() < 1;
  }
}
