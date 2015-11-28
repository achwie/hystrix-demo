package achwie.hystrixdemo.order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
public class Order {
  private final String userId;
  private final List<OrderItem> orderItems = new ArrayList<>();
  private final Calendar orderDate = Calendar.getInstance();

  public Order(String userId) {
    this.userId = userId;
  }

  public void addOrderItem(OrderItem item) {
    orderItems.add(item);
  }

  public List<OrderItem> getOrderItems() {
    return Collections.unmodifiableList(orderItems);
  }

  public Calendar getOrderDate() {
    final Calendar copy = Calendar.getInstance();

    copy.setTimeInMillis(orderDate.getTimeInMillis());
    copy.setTimeZone(orderDate.getTimeZone());

    return copy;
  }

  public Object getUserId() {
    return userId;
  }
}
