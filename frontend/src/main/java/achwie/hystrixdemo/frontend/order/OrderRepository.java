package achwie.hystrixdemo.frontend.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 */
@Component
public class OrderRepository {
  private final Object lock = new Object();
  private final List<Order> orders = new ArrayList<>();

  public void addOrder(Order order) {
    synchronized (lock) {
      orders.add(order);
    }
  }

  public List<Order> getOrdersForUser(String userId) {
    if (userId == null)
      return Collections.emptyList();

    synchronized (lock) {
      return orders.stream().filter((order) -> userId.equals(order.getUserId())).collect(Collectors.toList());
    }
  }
}
