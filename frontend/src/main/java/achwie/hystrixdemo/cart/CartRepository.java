package achwie.hystrixdemo.cart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import achwie.hystrixdemo.catalog.Product;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartRepository {
  private final Object lock = new Object();
  private final Map<String, List<CartItem>> carts = new HashMap<>();

  public void addToCart(String cartId, Product product, int quantity) {
    synchronized (lock) {
      List<CartItem> itemsForCart = carts.get(cartId);

      if (itemsForCart == null) {
        itemsForCart = new ArrayList<>();
        carts.put(cartId, itemsForCart);
      }

      final CartItem cartItem = findCartItemForProduct(cartId, product);
      if (cartItem == null)
        itemsForCart.add(new CartItem(product, quantity));
      else
        cartItem.increaseQuantity(quantity);
    }
  }

  public void removeFromCart(String cartId, Product product, int quantity) {
    synchronized (lock) {
      List<CartItem> itemsForCart = carts.get(cartId);
      if (itemsForCart == null)
        return;

      final CartItem cartItem = findCartItemForProduct(cartId, product);
      if (cartItem == null)
        return;

      cartItem.decreaseQuantity(quantity);
      if (cartItem.getQuantity() == 0)
        itemsForCart.remove(cartItem);
    }
  }

  private CartItem findCartItemForProduct(String cartId, Product product) {
    synchronized (lock) {
      final List<CartItem> itemsForCart = carts.get(cartId);

      if (itemsForCart != null)
        for (CartItem cartItem : itemsForCart)
          if (cartItem.getProduct().getId().equals(product.getId()))
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

  public void clearCart(String userId) {
    synchronized (lock) {
      final List<CartItem> cartItems = carts.get(userId);
      cartItems.clear();
    }
  }
}
