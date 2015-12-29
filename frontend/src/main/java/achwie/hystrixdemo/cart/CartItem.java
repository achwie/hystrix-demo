package achwie.hystrixdemo.cart;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class CartItem {
  private String productId;
  private String productName;
  private int quantity;

  public CartItem() {
  }

  public CartItem(String productId, String productName, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String toString() {
    return String.format("%s[productId: %s, quantity: %d]", getClass().getSimpleName(), getProductId(), quantity);
  }
}
