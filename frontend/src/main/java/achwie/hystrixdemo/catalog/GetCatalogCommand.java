package achwie.hystrixdemo.catalog;

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
class GetCatalogCommand extends HystrixRestCommand<List<CatalogItem>> {
  private final String url;

  public GetCatalogCommand(RestOperations restOps, String productServiceBaseUrl) {
    super(CommandGroup.CATALOG_SERVICE, restOps);
    this.url = productServiceBaseUrl;
  }

  @Override
  protected List<CatalogItem> run() throws Exception {
    final CatalogItem[] products = restOps.getForObject(url, CatalogItem[].class);

    return Arrays.asList(products);
  }

  @Override
  protected List<CatalogItem> getFallback() {
    return Collections.emptyList();
  }

  @Override
  protected String getCacheKey() {
    return url;
  }
}
