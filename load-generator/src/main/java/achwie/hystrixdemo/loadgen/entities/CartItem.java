package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONObject;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class CartItem {
  public static final String FIELD_PRODUCT_ID = "productId";
  public static final String FIELD_PRODUCT_NAME = "productName";
  public static final String FIELD_QUANTITY = "quantity";

  private final String productId;
  private final String productName;
  private final int quantity;

  public CartItem(String productId, String productName, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
  }

  public String toJson() {
    JSONObject json = new JSONObject();

    json.put(FIELD_PRODUCT_ID, productId);
    json.put(FIELD_PRODUCT_NAME, productName);
    json.put(FIELD_QUANTITY, quantity);

    return json.toString();
  }

  public String toString() {
    return "CartItem[productId: " + productId + ", productName: " + productName + ", quantity: " + quantity + "]";
  }
}
