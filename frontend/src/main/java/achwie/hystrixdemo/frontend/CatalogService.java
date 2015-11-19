package achwie.hystrixdemo.frontend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import achwie.hystrixdemo.frontend.product.Product;
import achwie.hystrixdemo.frontend.product.ProductService;
import achwie.hystrixdemo.frontend.product.ViewProduct;
import achwie.hystrixdemo.frontend.stock.StockService;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CatalogService {
  private final ProductService productService;
  private final StockService stockService;

  @Autowired
  public CatalogService(ProductService productService, StockService stockService) {
    this.productService = productService;
    this.stockService = stockService;
  }

  public List<ViewProduct> findAllProducts() {
    final List<Product> products = productService.getAllProducts();

    return toViewProducts(products);
  }

  private List<ViewProduct> toViewProducts(List<Product> products) {
    final List<ViewProduct> viewProducts = new ArrayList<>();
    for (Product p : products) {
      final ViewProduct viewProduct = new ViewProduct();
      viewProduct.setId(p.getId());
      viewProduct.setName(p.getName());
      viewProduct.setStockQuantity(stockService.getStockQuantity(p.getId()));

      viewProducts.add(viewProduct);
    }

    return viewProducts;
  }
}
