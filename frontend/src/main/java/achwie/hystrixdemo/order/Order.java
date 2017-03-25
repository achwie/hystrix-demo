package achwie.hystrixdemo.order;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
public class Order {
  private final String userId;
  private final List<OrderItem> orderItems;
  private final Calendar orderDate;

  @JsonCreator
  Order(@JsonProperty("userId") String userId, @JsonProperty("orderItems") List<OrderItem> orderItems, @JsonProperty("orderDate") Calendar orderDate) {
    this.userId = userId;
    this.orderItems = (orderItems != null) ? orderItems : Collections.emptyList();
    this.orderDate = orderDate;
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

  public String getOrderDateFormatted() {
    return String.format("%TF", getOrderDate());
  }

  public Object getUserId() {
    return userId;
  }
}
