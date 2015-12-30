package achwie.hystrixdemo.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import achwie.hystrixdemo.catalog.CatalogItem;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private final RestTemplate restTemplate = new RestTemplate();
  private final String cartServiceBaseUrl;

  @Autowired
  public CartService(@Value("${service.cart.baseurl}") String cartServiceBaseUrl) {
    this.cartServiceBaseUrl = cartServiceBaseUrl;
  }

  public void addToCart(String cartId, CatalogItem catalogItem, int quantity) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    final CartItem cartItem = new CartItem(catalogItem.getId(), catalogItem.getName(), quantity);

    final ResponseEntity<String> response = restTemplate.postForEntity(url, cartItem, String.class);

    if (response.getStatusCode() != HttpStatus.OK) {
      // TODO: Handle properly
      System.err.println("ERROR: " + response.getBody());
    }
  }

  public Cart getCart(String cartId) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    return restTemplate.getForObject(url, Cart.class);
  }

  public void clearCart(String cartId) {
    final String url = cartServiceBaseUrl + "/" + cartId;

    restTemplate.delete(url);
  }
}
