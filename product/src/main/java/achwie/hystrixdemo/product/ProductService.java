package achwie.hystrixdemo.product;

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
@RequestMapping("/products")
public class ProductService {
  private final ProductRepository productRepo;

  @Autowired
  public ProductService(ProductRepository productRepo) {
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
