package achwie.hystrixdemo.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class ProductService {
  private final ProductRepository productRepo;

  @Autowired
  public ProductService(ProductRepository productRepo) {
    this.productRepo = productRepo;
  }

  public List<Product> getAllProducts() {
    return productRepo.findAllProducts();
  }

  public Product getById(String productId) {
    return productRepo.findById(productId);
  }
}
