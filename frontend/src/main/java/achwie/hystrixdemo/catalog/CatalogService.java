package achwie.hystrixdemo.catalog;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CatalogService {
  private static final Logger LOG = LoggerFactory.getLogger(CatalogService.class);
  private final RestTemplate restTemplate = new RestTemplate();
  private final String productServiceBaseUrl;

  @Autowired
  public CatalogService(@Value("${service.catalog.baseurl}") String productServiceBaseUrl) {
    this.productServiceBaseUrl = productServiceBaseUrl;
  }

  /**
   * Returns all items of the catalog.
   * 
   * @return All items of the catalog or an empty list if the catalog was empty.
   * @throws IOException If something went wrong with the remote call.
   */
  public List<CatalogItem> findAll() throws IOException {
    final String url = productServiceBaseUrl;
    try {
      final CatalogItem[] products = restTemplate.getForObject(url, CatalogItem[].class);

      return Arrays.asList(products);
    } catch (RestClientException e) {
      throw new IOException("Could not get catalog items at " + url, e);
    }
  }

  /**
   * Returns the catalog item for the given ID.
   * 
   * @param itemId The ID of the item to get.
   * @return The catalog item for the given ID or {@code null} if there was no
   *         item with the given ID.
   * @throws IOException If something went wrong with the remote call.
   */
  public CatalogItem findById(String itemId) throws IOException {
    String url = productServiceBaseUrl + "/" + itemId;
    try {
      return restTemplate.getForObject(url, CatalogItem.class);
    } catch (HttpStatusCodeException e) {
      // Returns 404 if item could not be found
      if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
        LOG.error("Unexpected response while getting product at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
      }

      return null;
    } catch (RestClientException e) {
      throw new IOException("Could not get catalog item at " + url, e);
    }
  }
}
