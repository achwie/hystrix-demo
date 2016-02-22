package achwie.hystrixdemo.catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CatalogService {
  private final RestTemplate restTemplate;
  private final String productServiceBaseUrl;

  @Autowired
  public CatalogService(@Value("${service.catalog.baseurl}") String productServiceBaseUrl, RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.productServiceBaseUrl = productServiceBaseUrl;
  }

  /**
   * Returns all items of the catalog.
   * 
   * @return All items of the catalog or an empty list if the catalog was empty.
   */
  public List<CatalogItem> findAll() {
    return new GetCatalogCommand(restTemplate, productServiceBaseUrl).execute();
  }

  /**
   * Returns the catalog item for the given ID.
   * 
   * @param itemId The ID of the item to get.
   * @return The catalog item for the given ID or {@code null} if there was no
   *         item with the given ID.
   */
  public CatalogItem findById(String itemId) {
    return new GetCatalogItemCommand(restTemplate, productServiceBaseUrl, itemId).execute();
  }
}
