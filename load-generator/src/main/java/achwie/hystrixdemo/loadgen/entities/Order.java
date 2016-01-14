package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONArray;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class Order {
  public static final String FIELD_USER_ID = "userId";
  public static final String FIELD_ORDER_ITEMS = "orderItems";
  private final JSONArray orders;
  private final int index;

  Order(JSONArray orders, int index) {
    this.orders = orders;
    this.index = index;
  }

  public String getUserId() {
    return orders.getJSONObject(index).getString(FIELD_USER_ID);
  }

  public int getOrderItemCount() {
    return orders.getJSONObject(index).getJSONArray(FIELD_ORDER_ITEMS).length();
  }
}
