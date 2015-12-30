package achwie.hystrixdemo.catalog;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CatalogService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String productServiceBaseUrl;

  @Autowired
  public CatalogService(@Value("${service.catalog.baseurl}") String productServiceBaseUrl) {
    this.productServiceBaseUrl = productServiceBaseUrl;
  }

  public List<CatalogItem> findAll() {
    final String url = productServiceBaseUrl;
    try {
      ResponseEntity<CatalogItem[]> productsResponse = restTemplate.getForEntity(url, CatalogItem[].class);

      if (productsResponse.getStatusCode() == HttpStatus.OK) {
        return Arrays.asList(productsResponse.getBody());
      }
    } catch (RestClientException e) {
      // TODO: Log
      e.printStackTrace();
    }
    // TODO: Log
    System.err.println(String.format("ERROR: Couldn't get catalog-items using URL %s!", url));
    return Collections.emptyList();
  }

  public CatalogItem findById(String itemId) {
    try {
      String url = productServiceBaseUrl + "/" + UriUtils.encodePathSegment(itemId, "UTF-8");
      ResponseEntity<CatalogItem> catalogResponse = restTemplate.getForEntity(url, CatalogItem.class);
      if (catalogResponse.getStatusCode() == HttpStatus.OK) {
        return catalogResponse.getBody();
      } else {
        // TODO: Log
        System.err.println(String.format("ERROR: Couldn't get catalog-item using URL %s!", url));
      }
    } catch (UnsupportedEncodingException e) {
      // TODO: Log
      System.err.println("ERROR: Could not create URL for fetching catalog-items!");
      e.printStackTrace();
    }

    return null;
  }
}
