package achwie.hystrixdemo.frontend.stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
// TODO: Make thread-safe
@Component
public class StockRepository {
  private final Random rand = new Random();
  private final Map<String, Integer> quantitiesByProduct = new HashMap<String, Integer>();

  public int[] getQuantities(List<String> productIds) {
    int[] quantities = new int[productIds.size()];

    for (int i = 0; i < productIds.size(); i++)
      quantities[i] = getQuantityForProduct(productIds.get(i));

    return quantities;
  }

  private int getQuantityForProduct(String productId) {
    Integer quantityForProduct = quantitiesByProduct.get(productId);

    if (quantityForProduct == null) {
      quantityForProduct = rand.nextInt(1000);
      quantitiesByProduct.put(productId, quantityForProduct);
    }

    return quantityForProduct;
  }
}
