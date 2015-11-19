package achwie.hystrixdemo.frontend.cart;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
// TODO: rename to be more distinct from CartItem
public class ViewCartItem {
  private String productId;
  private int quantity;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String toString() {
    return String.format("%s[productId: %s, quantity: %d]", getClass().getSimpleName(), productId, quantity);
  }
}
