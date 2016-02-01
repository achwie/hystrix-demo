package achwie.hystrixdemo.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 01.02.2016, Achim Wiedemann
 */
class PlaceHoldOnItemsCommand extends HystrixRestCommand<Boolean> {
  private final String url;
  private String[] productIds;
  private int[] quantities;

  protected PlaceHoldOnItemsCommand(RestOperations restOps, String stockServiceBaseUrl, String[] productIds, int[] quantities) {
    super(CommandGroup.STOCK_PLACE_HOLD, restOps);
    this.url = stockServiceBaseUrl + "/put-hold-on-all";
    this.productIds = productIds;
    this.quantities = quantities;
  }

  @Override
  protected Boolean run() throws Exception {
    final PutProductsOnHoldRequest request = new PutProductsOnHoldRequest();
    request.setProductIds(productIds);
    request.setQuantities(quantities);

    try {
      restOps.postForObject(url, request, String.class);
      return true;
    } catch (HttpStatusCodeException e) {
      // Returns 409 if there's insufficient stock for one of the ordered items
      // - else we have an error
      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while putting a hold on products for order at {} (status: {}, response body: '{}')", url, e.getStatusCode(),
            e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected Boolean getFallback() {
    return false;
  }

  // ---------------------------------------------------------------------------
  public static class PutProductsOnHoldRequest {
    private String[] productIds;
    private int[] quantities;

    public String[] getProductIds() {
      return productIds;
    }

    public void setProductIds(String[] productsIds) {
      this.productIds = productsIds;
    }

    public int[] getQuantities() {
      return quantities;
    }

    public void setQuantities(int[] quantities) {
      this.quantities = quantities;
    }
  }
}
