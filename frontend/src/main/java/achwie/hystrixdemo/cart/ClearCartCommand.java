package achwie.hystrixdemo.cart;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class ClearCartCommand extends HystrixRestCommand<Void> {
  private final String url;

  protected ClearCartCommand(RestOperations restOps, String cartServiceBaseUrl, String cartId) {
    super(CommandGroup.CART_SERVICE, restOps);
    this.url = cartServiceBaseUrl + "/" + cartId;
  }

  @Override
  protected Void run() throws Exception {
    try {
      restOps.delete(url);
      return null;
    } catch (RestClientException e) {
      LOG.error("Could not clear cart at {}", url);
      throw e;
    }
  }

  @Override
  protected Void getFallback() {
    return null;
  }
}
