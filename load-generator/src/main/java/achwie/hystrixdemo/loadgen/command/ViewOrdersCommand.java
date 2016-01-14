package achwie.hystrixdemo.loadgen.command;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.Order;
import achwie.hystrixdemo.loadgen.entities.OrderList;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class ViewOrdersCommand implements Callable<OrderList> {
  private final String viewOrdersUrl;

  public ViewOrdersCommand(String orderServiceBaseUrl, String sessionId) {
    this.viewOrdersUrl = orderServiceBaseUrl + "/" + sessionId;
  }

  @Override
  public OrderList call() throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final HttpGet httpGet = new HttpGet(viewOrdersUrl);

      final CloseableHttpResponse resp = httpClient.execute(httpGet);
      final HttpEntity responseEntity = resp.getEntity();

      final byte[] content = HttpClientUtils.getContent(responseEntity);
      final Charset charset = HttpClientUtils.getCharset(responseEntity, HttpClientUtils.DEFAULT_CHARSET);

      final String ordersJsonStr = new String(content, charset);

      return OrderList.fromJson(ordersJsonStr);
    }
  }

  public static void main(String[] args) throws Exception {
    final String sessionId = "1";
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String orderServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_ORDER_BASEURL);
    final OrderList orders = new ViewOrdersCommand(orderServiceBaseUrl, sessionId).call();

    System.out.println("Orders in session " + sessionId + ":");
    for (int i = 0; i < orders.size(); i++) {
      final Order order = orders.getOrder(i);
      System.out.println(String.format("  * userId: %s, items ordered: %d", order.getUserId(), order.getOrderItemCount()));
    }
  }

}
