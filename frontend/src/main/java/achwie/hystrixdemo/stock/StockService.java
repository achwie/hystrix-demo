package achwie.hystrixdemo.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class StockService {
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
   */
  public int getStockQuantity(String productId) {
    return new GetStockQuantityCommand(restTemplate, stockServiceBaseUrl, productId).execute();
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
    return new PlaceHoldOnItemsCommand(restTemplate, stockServiceBaseUrl, productIds, quantities).execute();
  }
}