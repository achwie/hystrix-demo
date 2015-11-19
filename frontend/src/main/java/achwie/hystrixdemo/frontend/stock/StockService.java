package achwie.hystrixdemo.frontend.stock;

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
}
