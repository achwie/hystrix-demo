package achwie.hystrixdemo.cart;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import achwie.hystrixdemo.catalog.CatalogItem;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private static final Logger LOG = LoggerFactory.getLogger(CartService.class);
  private final RestTemplate restTemplate = new RestTemplate();
  private final String cartServiceBaseUrl;

  @Autowired
  public CartService(@Value("${service.cart.baseurl}") String cartServiceBaseUrl) {
    this.cartServiceBaseUrl = cartServiceBaseUrl;
  }

  /**
   * Adds a catalog item to a cart. If the cart did not exist before, it will be
   * created.
   * 
   * @param cartId The cart to add the item to
   * @param catalogItem The item to add to the cart
   * @param quantity How many to add
   * @throws IOException If something went wrong with the remote call.
   */
  public void addToCart(String cartId, CatalogItem catalogItem, int quantity) throws IOException {
    final String url = cartServiceBaseUrl + "/" + cartId;

    final CartItem cartItem = new CartItem(catalogItem.getId(), catalogItem.getName(), quantity);

    try {
      restTemplate.postForObject(url, cartItem, String.class);
    } catch (HttpStatusCodeException e) {
      final String itemId = (catalogItem != null) ? catalogItem.getId() : "null";
      LOG.error("Unexpected response while adding item {} to cart at {} (status: {}, response body: '{}')", itemId, url, e.getStatusCode(),
          e.getResponseBodyAsString());
    } catch (RestClientException e) {
      throw new IOException(String.format("Could not add item %s to cart at %s", catalogItem.getId(), url), e);
    }
  }

  /**
   * Returns the contents of a certain cart.
   * 
   * @param cartId The ID of the cart to retrieve.
   * @return The cart (empty cart if there was no cart with the given ID).
   * @throws IOException If something went wrong with the remote call.
   */
  public Cart getCart(String cartId) throws IOException {
    final String url = cartServiceBaseUrl + "/" + cartId;

    try {
      return restTemplate.getForObject(url, Cart.class);
    } catch (HttpStatusCodeException e) {
      // Returns 404 for non-existent cart
      if (e.getStatusCode() != HttpStatus.NOT_FOUND) {
        LOG.error("Unexpected response while getting cart at {} (status: {}, response body: '{}')", url, e.getStatusCode(), e.getResponseBodyAsString());
      }
      return Cart.EMPTY_CART;
    } catch (RestClientException e) {
      throw new IOException("Could not get cart at " + url, e);
    }
  }

  /**
   * Clears the cart with the given ID.
   * 
   * @param cartId The cart to clear.
   * @throws IOException If something went wrong with the remote call.
   */
  public void clearCart(String cartId) throws IOException {
    final String url = cartServiceBaseUrl + "/" + cartId;

    try {
      restTemplate.delete(url);
    } catch (RestClientException e) {
      throw new IOException("Could not clear cart at " + url, e);
    }
  }
}
