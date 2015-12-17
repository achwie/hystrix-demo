package achwie.hystrixdemo.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import achwie.hystrixdemo.catalog.Product;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
@Component
public class CartService {
  private CartRepository cartRepo;

  @Autowired
  public CartService(CartRepository cartRepo) {
    this.cartRepo = cartRepo;
  }

  public void addToCart(String cartId, Product product, int quantity) {
    cartRepo.addToCart(cartId, product, quantity);
  }

  public ViewCart getCart(String cartId) {
    List<CartItem> cartItems = cartRepo.getItemsForCart(cartId);

    return new ViewCart(cartItems);
  }

  public void clearCart(String cartId) {
    cartRepo.clearCart(cartId);
  }
}
