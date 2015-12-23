package achwie.hystrixdemo.cart;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class CartItem {
  private final String productId;
  private int quantity;

  @JsonCreator
  public CartItem(@JsonProperty("productId") String productId, @JsonProperty("quantity") int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void decreaseQuantity(int by) {
    quantity = Math.max(0, quantity - by); // make sure to be > 0
  }

  public void increaseQuantity(int by) {
    quantity = Math.max(0, quantity + by); // make sure to be > 0
  }
}
