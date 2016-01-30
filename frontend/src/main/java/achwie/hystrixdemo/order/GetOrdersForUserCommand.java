package achwie.hystrixdemo.order;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class GetOrdersForUserCommand extends HystrixRestCommand<List<Order>> {
  private final String url;

  protected GetOrdersForUserCommand(RestOperations restOps, String orderServiceBaseUrl, String sessionId) {
    super(CommandGroup.ORDER_GET_FOR_USER, restOps);
    this.url = orderServiceBaseUrl + "/" + sessionId;
  }

  @Override
  protected List<Order> run() throws Exception {
    final Order[] orders = restOps.getForObject(url, Order[].class);
    return Arrays.asList(orders);
  }

  @Override
  protected List<Order> getFallback() {
    return Collections.emptyList();
  }

  @Override
  protected String getCacheKey() {
    return url;
  }
}
