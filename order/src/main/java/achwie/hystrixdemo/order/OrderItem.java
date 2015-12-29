package achwie.hystrixdemo.order;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
public class OrderItem {
  private final String productId;
  private final String productName;
  private final int quantity;

  public OrderItem(String productId, String productName, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public int getQuantity() {
    return quantity;
  }
}
