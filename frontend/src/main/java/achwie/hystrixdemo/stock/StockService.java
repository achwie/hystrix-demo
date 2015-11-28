package achwie.hystrixdemo.stock;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class StockService {
  private final StockRepository stockRepo;

  @Autowired
  public StockService(StockRepository stockRepo) {
    this.stockRepo = stockRepo;
  }

  public int getStockQuantity(String productId) {
    final int[] quantities = stockRepo.getQuantities(Arrays.asList(productId));

    return quantities[0];
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
   * @throws IllegalArgumentException If one of the array if {@code null} or
   *           they differ in length.
   */
  public boolean putHoldOnAll(String[] productIds, int[] quantities) {
    if (productIds == null || quantities == null || productIds.length != quantities.length) {
      // TODO: More helpful message
      throw new IllegalArgumentException("Neither product-ids or quantities must be null and both arrays need to have the same length!");
    }

    final String[] productIdsPlacedOnHold = new String[productIds.length];
    final int[] quantitiesPlacedOnHold = new int[quantities.length];

    for (int i = 0; i < productIds.length; i++) {
      final String productId = productIds[i];
      final int quantity = quantities[i];

      final int quantityPlacedOnHold = stockRepo.placeHoldOnAvailable(productId, quantity);

      productIdsPlacedOnHold[i] = productId;
      quantitiesPlacedOnHold[i] = quantityPlacedOnHold;

      if (quantity != quantityPlacedOnHold) {
        rollbackProductHolds(productIdsPlacedOnHold, quantitiesPlacedOnHold);
        return false;
      }
    }

    return true;
  }

  private void rollbackProductHolds(String[] productIds, int[] quantities) {
    for (int i = 0; i < productIds.length; i++)
      stockRepo.removeHold(productIds[i], quantities[i]);
  }
}
