package achwie.hystrixdemo.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class GetStockQuantityCommand extends HystrixRestCommand<Integer> {
  private final String url;

  protected GetStockQuantityCommand(RestOperations restOps, String stockServiceBaseUrl, String productId) {
    super(CommandGroup.STOCK_GET_QUANTITY, restOps);
    this.url = stockServiceBaseUrl + "/" + productId;
  }

  @Override
  protected Integer run() throws Exception {
    try {
      final Integer quantity = restOps.getForObject(url, Integer.class);
      return quantity != null ? quantity.intValue() : getFallback();
    } catch (HttpStatusCodeException e) {
      // Returns 404 for non-existent stock count entry - otherwise we have an
      // error
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        LOG.warn("Could not get stock quantity for product at {}", url);
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while getting stock quantity for product at {} (status: {}, response body: '{}')", url, e.getStatusCode(),
            e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected Integer getFallback() {
    return -1;
  }

  @Override
  protected String getCacheKey() {
    return url;
  }
}
