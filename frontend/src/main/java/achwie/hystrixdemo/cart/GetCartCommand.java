package achwie.hystrixdemo.cart;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class GetCartCommand extends HystrixRestCommand<Cart> {
  private final String url;

  protected GetCartCommand(RestOperations restOps, String cartServiceBaseUrl, String cartId) {
    super(CommandGroup.CART_SERVICE, restOps);
    this.url = cartServiceBaseUrl + "/" + cartId;
  }

  @Override
  protected Cart run() throws Exception {
    try {
      return restOps.getForObject(url, Cart.class);
    } catch (HttpStatusCodeException e) {
      // Returns 404 for non-existent cart - otherwise we have an error
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while getting cart at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected Cart getFallback() {
    return Cart.EMPTY_CART;
  }

  @Override
  protected String getCacheKey() {
    return url;
  }
}
