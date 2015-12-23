package achwie.hystrixdemo.cart;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import achwie.hystrixdemo.catalog.CatalogService;
import achwie.hystrixdemo.catalog.Product;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private final CatalogService catalogService;
  private final RestTemplate restTemplate = new RestTemplate();
  private final String cartServiceBaseUrl;

  @Autowired
  public CartService(@Value("${service.cart.baseurl}") String cartServiceBaseUrl, CatalogService catalogService) {
    this.cartServiceBaseUrl = cartServiceBaseUrl;
    this.catalogService = catalogService;
  }

  public void addToCart(String cartId, Product product, int quantity) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    final ViewCartItem cartItem = new ViewCartItem(product.getId(), quantity);

    final ResponseEntity<String> response = restTemplate.postForEntity(url, cartItem, String.class);

    if (response.getStatusCode() != HttpStatus.OK) {
      // TODO: Handle properly
      System.err.println("ERROR: " + response.getBody());
    }
  }

  public Cart getCart(String cartId) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    final ViewCart viewCart = restTemplate.getForObject(url, ViewCart.class);

    // TODO: This is neither readable nor performant
    final List<CartItem> cartItems = viewCart.getItems().stream()
        .map(viewCartItem -> new CartItem(catalogService.getById(viewCartItem.getProductId()), viewCartItem.getQuantity())).collect(Collectors.toList());

    return new Cart(cartItems);
  }

  public void clearCart(String cartId) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    restTemplate.delete(url);
  }
}
