package achwie.hystrixdemo.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.auth.User;
import achwie.hystrixdemo.cart.CartService;
import achwie.hystrixdemo.stock.StockService;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {
  private final CatalogService catalogService;
  private final CartService cartService;
  private final StockService stockService;
  private final SessionService sessionService;

  @Autowired
  public CatalogController(CatalogService catalogService, CartService cartService, StockService stockService, SessionService sessionService) {
    this.catalogService = catalogService;
    this.cartService = cartService;
    this.stockService = stockService;
    this.sessionService = sessionService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getPerson(Model model, HttpServletRequest req) {
    final User user = sessionService.getSessionUser();
    final String sessionId = sessionService.getSessionId();
    model.addAttribute("catalogItems", toCatalogItems(catalogService.findAll()));
    model.addAttribute("cart", cartService.getCart(sessionId));
    model.addAttribute("user", user);
    return "catalog";
  }

  private List<Product> toCatalogItems(List<CatalogItem> catalogItems) {
    final List<Product> products = new ArrayList<>();
    // TODO: Fetch stock quantities in bulk for better performance
    for (CatalogItem item : catalogItems) {
      final Product product = new Product();
      product.setId(item.getId());
      product.setName(item.getName());
      product.setStockQuantity(stockService.getStockQuantity(item.getId()));

      products.add(product);
    }

    return products;
  }
}
