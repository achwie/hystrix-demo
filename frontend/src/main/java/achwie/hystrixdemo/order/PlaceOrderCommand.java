package achwie.hystrixdemo.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;
import achwie.hystrixdemo.cart.Cart;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 *
 */
class PlaceOrderCommand extends HystrixRestCommand<Boolean> {
  private final String url;
  private final Cart cart;

  protected PlaceOrderCommand(RestOperations restOps, String orderServiceBaseUrl, String sessionId, Cart cart) {
    super(CommandGroup.ORDER_SERVICE, restOps);
    this.url = orderServiceBaseUrl + "/" + sessionId;
    this.cart = cart;
  }

  @Override
  protected Boolean run() throws Exception {
    try {
      restOps.postForObject(url, cart, String.class);
      return true;
    } catch (HttpStatusCodeException e) {
      // Returns 409 for status errors, e.g. if not enough items were available
      // anymore
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        LOG.warn("Could not place order at {}! Maybe there were not enough items on stock anymore?", url);
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while placing order at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected Boolean getFallback() {
    return false;
  }
}
