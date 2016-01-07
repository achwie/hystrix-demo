package achwie.hystrixdemo.catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
  private final CatalogRepository productRepo;

  @Autowired
  public CatalogController(CatalogRepository productRepo) {
    this.productRepo = productRepo;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<Product> getAllProducts() {
    return productRepo.findAllProducts();
  }

  @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
  public ResponseEntity<Product> getById(@PathVariable String productId) {
    final Product product = productRepo.findById(productId);

    return new ResponseEntity<Product>(product, product != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
  }
}
