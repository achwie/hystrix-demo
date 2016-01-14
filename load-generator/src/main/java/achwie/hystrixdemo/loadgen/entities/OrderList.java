package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONArray;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class OrderList {
  private final JSONArray ordersJson;

  public OrderList(JSONArray ordersJson) {
    this.ordersJson = ordersJson;
  }

  public Order getOrder(int i) {
    if (i < 0)
      throw new IndexOutOfBoundsException(String.format("Invalid index: %d (must be >= 0)", i));

    if (i >= size())
      throw new IndexOutOfBoundsException(String.format("Invalid index: %d (list length: %d)", i, size()));

    return new Order(ordersJson, i);

  }

  public int size() {
    return ordersJson.length();
  }

  public static OrderList fromJson(String json) {
    return new OrderList(new JSONArray(json));
  }
}
