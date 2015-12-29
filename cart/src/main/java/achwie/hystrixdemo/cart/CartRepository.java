package achwie.hystrixdemo.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartRepository {
  private final Object lock = new Object();
  private final Map<String, List<CartItem>> carts = new HashMap<>();

  public void addToCart(String cartId, String productId, String productName, int quantity) {
    synchronized (lock) {
      List<CartItem> itemsForCart = carts.get(cartId);

      if (itemsForCart == null) {
        itemsForCart = new ArrayList<>();
        carts.put(cartId, itemsForCart);
      }

      final CartItem cartItem = findCartItemForProduct(cartId, productId);
      if (cartItem == null)
        itemsForCart.add(new CartItem(productId, productName, quantity));
      else
        cartItem.increaseQuantity(quantity);
    }
  }

  public void removeFromCart(String cartId, String productId, int quantity) {
    synchronized (lock) {
      List<CartItem> itemsForCart = carts.get(cartId);
      if (itemsForCart == null)
        return;

      final CartItem cartItem = findCartItemForProduct(cartId, productId);
      if (cartItem == null)
        return;

      cartItem.decreaseQuantity(quantity);
      if (cartItem.getQuantity() == 0)
        itemsForCart.remove(cartItem);
    }
  }

  private CartItem findCartItemForProduct(String cartId, String productId) {
    synchronized (lock) {
      final List<CartItem> itemsForCart = carts.get(cartId);

      if (itemsForCart != null)
        for (CartItem cartItem : itemsForCart)
          if (cartItem.getProductId().equals(productId))
            return cartItem;

      return null;
    }
  }

  public List<CartItem> getItemsForCart(String cartId) {
    synchronized (lock) {
      final List<CartItem> itemsForCart = carts.get(cartId);

      return itemsForCart != null ? Collections.unmodifiableList(carts.get(cartId)) : Collections.emptyList();
    }
  }

  public void clearCart(String cartId) {
    synchronized (lock) {
      final List<CartItem> cartItems = carts.get(cartId);
      if (cartItems != null)
        cartItems.clear();
    }
  }
}
