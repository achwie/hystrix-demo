package achwie.hystrixdemo.cart;

import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;
import achwie.hystrixdemo.catalog.CatalogItem;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class AddToCartCommand extends HystrixRestCommand<Void> {
  private final String url;
  private final CatalogItem catalogItem;
  private final int quantity;

  protected AddToCartCommand(RestOperations restOps, String cartServiceBaseUrl, String cartId, CatalogItem catalogItem, int quantity) {
    super(CommandGroup.CART_SERVICE, restOps);
    this.url = cartServiceBaseUrl + "/" + cartId;
    this.catalogItem = catalogItem;
    this.quantity = quantity;
  }

  @Override
  protected Void run() throws Exception {
    final CartItem cartItem = new CartItem(catalogItem.getId(), catalogItem.getName(), quantity);

    try {
      restOps.postForObject(url, cartItem, String.class);
      return null;
    } catch (HttpStatusCodeException e) {
      final String itemId = (catalogItem != null) ? catalogItem.getId() : "null";
      LOG.error("Unexpected response while adding item {} to cart at {} (status: {}, response body: '{}')", itemId, url, e.getStatusCode(),
          e.getResponseBodyAsString());
      throw e;
    }
  }

  @Override
  protected Void getFallback() {
    return null;
  }
}
