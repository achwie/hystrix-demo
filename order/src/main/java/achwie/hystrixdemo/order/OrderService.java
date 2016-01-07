package achwie.hystrixdemo.order;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 20.11.2015, Achim Wiedemann
 *
 */
@Component
public class OrderService {
  private final OrderRepository orderRepo;
  private final StockService stockService;

  @Autowired
  public OrderService(OrderRepository orderRepo, StockService stockService) {
    this.orderRepo = orderRepo;
    this.stockService = stockService;
  }

  /**
   * Places an order.
   * 
   * @param order The order to place
   * @return Whether the placement of the order was successful or not (e.g.
   *         because an item was not on stock)
   * @throws IOException If something went wrong with the remote call.
   */
  public boolean placeOrder(Order order) throws IOException {
    final List<OrderItem> orderItems = order.getOrderItems();
    final String[] productIds = new String[orderItems.size()];
    final int[] quantities = new int[orderItems.size()];

    for (int i = 0; i < orderItems.size(); i++) {
      final OrderItem orderItem = orderItems.get(i);
      productIds[i] = orderItem.getProductId();
      quantities[i] = orderItem.getQuantity();
    }

    final boolean success = stockService.putHoldOnAll(productIds, quantities);

    if (success)
      orderRepo.addOrder(order);

    return success;
  }

  public List<Order> getOrdersForUser(String userId) {
    return orderRepo.getOrdersForUser(userId);
  }
}
