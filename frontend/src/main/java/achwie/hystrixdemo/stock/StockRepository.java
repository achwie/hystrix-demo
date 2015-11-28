package achwie.hystrixdemo.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class StockRepository {
  private final Object lock = new Object();
  private final Random rand = new Random();
  private final Map<String, Integer> quantitiesByProduct = new HashMap<String, Integer>();

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
      Integer quantityForProduct = quantitiesByProduct.get(productId);

      if (quantityForProduct == null) {
        quantityForProduct = rand.nextInt(1000);
        quantitiesByProduct.put(productId, quantityForProduct);
      }

      return quantityForProduct;
    }
  }
}
