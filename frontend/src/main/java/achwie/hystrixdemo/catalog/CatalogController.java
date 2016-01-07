package achwie.hystrixdemo.catalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.auth.SessionService;
import achwie.hystrixdemo.auth.User;
import achwie.hystrixdemo.cart.Cart;
import achwie.hystrixdemo.cart.CartService;
import achwie.hystrixdemo.stock.StockService;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
@Controller
public class CatalogController {
  private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
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

  @RequestMapping("/")
  public String entryPage(Model model, HttpServletRequest req) {
    return "redirect:catalog";
  }

  @RequestMapping(value = "/catalog", method = RequestMethod.GET)
  public String viewCatalog(Model model, HttpServletRequest req) {
    final User user = sessionService.getSessionUser();
    final String sessionId = sessionService.getSessionId();
    Cart cart;
    try {
      cart = cartService.getCart(sessionId);
    } catch (IOException e) {
      LOG.error(e.getMessage());
      cart = Cart.emptyCart();
    }

    List<CatalogItem> catalogItems;
    try {
      catalogItems = catalogService.findAll();
    } catch (IOException e) {
      LOG.error(e.getMessage());
      catalogItems = Collections.emptyList();
    }

    model.addAttribute("catalogItems", toCatalogItems(catalogItems));
    model.addAttribute("cart", cart);
    model.addAttribute("user", user);

    return "catalog";
  }

  private List<Product> toCatalogItems(List<CatalogItem> catalogItems) {
    final List<Product> products = new ArrayList<>();
    // TODO: Fetch stock quantities in bulk for better performance
    for (CatalogItem item : catalogItems) {
      int productStockQuantity;
      try {
        productStockQuantity = stockService.getStockQuantity(item.getId());
      } catch (IOException e) {
        LOG.error(e.getMessage());
        productStockQuantity = -1;
      }

      final Product product = new Product();
      product.setId(item.getId());
      product.setName(item.getName());
      product.setStockQuantity(productStockQuantity);

      products.add(product);
    }

    return products;
  }
}
