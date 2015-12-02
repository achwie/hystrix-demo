package achwie.hystrixdemo.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 30.11.2015, Achim Wiedemann
 *
 */
@RestController
@RequestMapping(value = "/stock", produces = { MediaType.APPLICATION_JSON_VALUE })
public class StockController {
  private final StockService stockService;

  @Autowired
  public StockController(StockService stockService) {
    this.stockService = stockService;
  }

  @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
  public ResponseEntity<Integer> getQuantity(@PathVariable String productId) {
    final int quantity = stockService.getStockQuantity(productId);

    return new ResponseEntity<Integer>(quantity, HttpStatus.OK);
  }

  @RequestMapping(value = "/put-hold-on-all", method = RequestMethod.POST)
  public ResponseEntity<String> putHoldOnAll(@RequestBody PutProductsOnHoldRequest holdRequest) {
    String msg = "OK";
    HttpStatus code = HttpStatus.OK;
    try {
      final boolean success = stockService.putHoldOnAll(holdRequest.getProductIds(), holdRequest.getQuantities());

      if (!success) {
        msg = "Could not put a hold on one or more products since they are not available in the requested quantity!";
        code = HttpStatus.CONFLICT;
      }
    } catch (IllegalArgumentException e) {
      msg = e.getMessage();
      code = HttpStatus.BAD_REQUEST;
    }

    return new ResponseEntity<>(msg, code);
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
