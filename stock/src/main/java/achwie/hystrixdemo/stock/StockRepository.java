package achwie.hystrixdemo.stock;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import achwie.hystrixdemo.util.SimpleCsvReader;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class StockRepository {
  private final Object lock = new Object();
  private final Map<String, Integer> quantitiesByProduct = new HashMap<String, Integer>();

  {
    try (final InputStream is = StockRepository.class.getResourceAsStream("/test-data-stock.csv")) {
      SimpleCsvReader.readLines(is, values -> {
        if (values.length == 2)
          quantitiesByProduct.put(values[0], Integer.valueOf(values[1]));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int[] getQuantities(List<String> productIds) {
    int[] quantities = new int[productIds.size()];

    synchronized (lock) {
      for (int i = 0; i < productIds.size(); i++)
        quantities[i] = getQuantityForProduct(productIds.get(i));
    }

    return quantities;
  }

  public int placeHoldOnAvailable(String productId, int quantityToPutOnHold) {
    synchronized (lock) {
      final int quantityAvailable = getQuantityForProduct(productId);

      final int quantityPutOnHold = Math.min(quantityAvailable, quantityToPutOnHold);
      quantitiesByProduct.put(productId, quantityAvailable - quantityPutOnHold);

      return quantityPutOnHold;
    }
  }

  public void removeHold(String productId, int quantity) {
    synchronized (lock) {
      final int quantityAvailable = getQuantityForProduct(productId);
      quantitiesByProduct.put(productId, quantityAvailable + quantity);
    }
  }

  private int getQuantityForProduct(String productId) {
    synchronized (lock) {
      final Integer quantityForProduct = quantitiesByProduct.get(productId);
      return (quantityForProduct != null) ? quantityForProduct : -1;
    }
  }
}
