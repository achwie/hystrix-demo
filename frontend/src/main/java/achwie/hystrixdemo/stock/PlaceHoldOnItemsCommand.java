package achwie.hystrixdemo.stock;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class PlaceHoldOnItemsCommand extends HystrixRestCommand<Boolean> {
  private final String url;
  private String[] productIds;
  private int[] quantities;

  protected PlaceHoldOnItemsCommand(RestOperations restOps, String stockServiceBaseUrl, String[] productIds, int[] quantities) {
    super(CommandGroup.STOCK_SERVICE, restOps);
    this.url = stockServiceBaseUrl + "/put-hold-on-all";
    this.productIds = productIds;
    this.quantities = quantities;
  }

  @Override
  protected Boolean run() throws Exception {
    final PutProductsOnHoldRequest request = new PutProductsOnHoldRequest();
    request.setProductIds(productIds);
    request.setQuantities(quantities);

    final ResponseEntity<String> response = restOps.postForEntity(url, request, String.class);

    return response.getStatusCode() == HttpStatus.OK;
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
