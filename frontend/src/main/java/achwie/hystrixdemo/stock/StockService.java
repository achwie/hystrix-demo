package achwie.hystrixdemo.stock;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class StockService {
  private static final Logger LOG = LoggerFactory.getLogger(StockService.class);
  private final RestTemplate restTemplate = new RestTemplate();
  private final String stockServiceBaseUrl;

  @Autowired
  public StockService(@Value("${service.stock.baseurl}") String stockServiceBaseUrl) {
    this.stockServiceBaseUrl = stockServiceBaseUrl;

  }

  /**
   * Returns the stock quantity for a product.
   * 
   * @param productId The product ID to get the stock quantity for.
   * @return The stock quantity ({@code >= 0}) or {@code -1} if there was no
   *         stock count entry for the given product ID.
   * @throws IOException If something went wrong with the remote call.
   */
  public int getStockQuantity(String productId) throws IOException {
    final String url = stockServiceBaseUrl + "/{productId}";

    try {
      final Integer quantity = restTemplate.getForObject(url, Integer.class, productId);
      return quantity != null ? quantity.intValue() : -1;
    } catch (HttpStatusCodeException e) {
      // Returns 404 for non-existent stock count entry
      if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
        LOG.error("Unexpected response while getting stock quantity for product at {} (status: {}, response body: '{}')", url, e.getStatusCode(),
            e.getResponseBodyAsString());
      }
      return -1;
    } catch (RestClientException e) {
      throw new IOException("Could not get stock quantity for product at " + url, e);
    }
  }

  /**
   * Checks if all passed products are available in the requested quantities and
   * places a hold on them if, and only if, all of them are available. The
   * indices of the {@code productIds} array have to correspond to the indices
   * of the {@code quantities} array.
   * 
   * @param productIds The products to put a hold on.
   * @param quantities The quantities of the products to put a hold on.
   * @return {@code true} if a hold could be placed on all products in the
   *         requested quantities, {@code false} else.
   * @throws IllegalArgumentException If one of the arrays is {@code null} or
   *           they differ in length.
   */
  public boolean putHoldOnAll(String[] productIds, int[] quantities) {
    final PutProductsOnHoldRequest request = new PutProductsOnHoldRequest();
    request.setProductIds(productIds);
    request.setQuantities(quantities);

    final String url = stockServiceBaseUrl + "/put-hold-on-all";
    final ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

    return response.getStatusCode() == HttpStatus.OK;
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