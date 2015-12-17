package achwie.hystrixdemo.catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Component
public class CatalogRepository {
  private final List<Product> products = new ArrayList<>();

  {
    int id = 0;
    products.add(new Product(String.valueOf(id++), "A <b>Book</b>"));
    products.add(new Product(String.valueOf(id++), "A Coat"));
    products.add(new Product(String.valueOf(id++), "A Knife"));
    products.add(new Product(String.valueOf(id++), "An Umbrella"));
    products.add(new Product(String.valueOf(id++), "A Hat"));
  }

  public List<Product> findAllProducts() {
    return Collections.unmodifiableList(products);
  }

  public Product findById(String productId) {
    if (productId != null)
      for (Product p : products)
        if (productId.equals(p.getId()))
          return p;

    return null;
  }
}
