package achwie.hystrixdemo.catalog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CatalogService {
  private final CatalogRepository productRepo;

  @Autowired
  public CatalogService(CatalogRepository productRepo) {
    this.productRepo = productRepo;
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<Product> getAllProducts() {
    return productRepo.findAllProducts();
  }

  @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
  public Product getById(@PathVariable String productId) {
    return productRepo.findById(productId);
  }
}
