package achwie.hystrixdemo.product;

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
public class ProductService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String productServiceBaseUrl;

  @Autowired
  public ProductService(@Value("${service.product.baseurl}") String productServiceBaseUrl) {
    this.productServiceBaseUrl = productServiceBaseUrl;
  }

  public List<Product> getAllProducts() {
    final String url = productServiceBaseUrl + "/products";
    try {
      ResponseEntity<Product[]> productsResponse = restTemplate.getForEntity(url, Product[].class);

      if (productsResponse.getStatusCode() == HttpStatus.OK) {
        return Arrays.asList(productsResponse.getBody());
      }
    } catch (RestClientException e) {
      // TODO: Log
      e.printStackTrace();
    }
    // TODO: Log
    System.err.println(String.format("ERROR: Couldn't get products using URL %s!", url));
    return Collections.emptyList();
  }

  public Product getById(String productId) {
    try {
      String url = productServiceBaseUrl + "/products/" + UriUtils.encodePathSegment(productId, "UTF-8");
      ResponseEntity<Product> productsResponse = restTemplate.getForEntity(url, Product.class);
      if (productsResponse.getStatusCode() == HttpStatus.OK) {
        return productsResponse.getBody();
      } else {
        // TODO: Log
        System.err.println(String.format("ERROR: Couldn't get product using URL %s!", url));
      }
    } catch (UnsupportedEncodingException e) {
      // TODO: Log
      System.err.println("ERROR: Could not create URL for fetching product!");
      e.printStackTrace();
    }

    return null;
  }
}
