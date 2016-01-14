package achwie.hystrixdemo.loadgen.entities;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class Cart {
  private final List<CartItem> cartItems;

  public Cart(List<CartItem> cartItems) {
    this.cartItems = cartItems;
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(cartItems);
  }

  public void addItem(CartItem cartItem) {
    cartItems.add(cartItem);
  }

  public String toJsonString() {
    final JSONObject json = new JSONObject(this);

    return json.toString();
  }
}
