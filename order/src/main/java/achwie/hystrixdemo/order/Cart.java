package achwie.hystrixdemo.order;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class Cart {
  private final List<CartItem> cartItems;

  @JsonCreator
  public Cart(@JsonProperty("items") List<CartItem> cartItems) {
    this.cartItems = (cartItems != null) ? cartItems : Collections.emptyList();
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(cartItems);
  }

  public boolean isEmpty() {
    return cartItems.isEmpty();
  }
}
