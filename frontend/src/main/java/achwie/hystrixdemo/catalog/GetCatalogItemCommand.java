package achwie.hystrixdemo.catalog;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class GetCatalogItemCommand extends HystrixRestCommand<CatalogItem> {
  private final String url;

  public GetCatalogItemCommand(RestOperations restOps, String productServiceBaseUrl, String itemId) {
    super(CommandGroup.CATALOG_GET_ITEM, restOps);
    this.url = productServiceBaseUrl + "/" + itemId;
  }

  @Override
  protected CatalogItem run() throws Exception {
    try {
      return restOps.getForObject(url, CatalogItem.class);
    } catch (HttpStatusCodeException e) {
      // Returns 404 if item could not be found - otherwise we have an error
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while getting product at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected CatalogItem getFallback() {
    return null;
  }

  @Override
  protected String getCacheKey() {
    return url;
  }
}
