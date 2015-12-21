package achwie.hystrixdemo.catalog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import achwie.hystrixdemo.util.SimpleCsvReader;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Component
public class CatalogRepository {
  private final List<Product> products = new ArrayList<>();

  {
    try (final InputStream is = CatalogRepository.class.getResourceAsStream("/test-data-catalog.csv")) {
      SimpleCsvReader.readLines(is, values -> {
        if (values.length == 2)
          products.add(new Product(values[0], values[1]));
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
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
