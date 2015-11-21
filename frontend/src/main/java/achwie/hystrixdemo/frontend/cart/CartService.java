package achwie.hystrixdemo.frontend.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import achwie.hystrixdemo.frontend.product.Product;

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

  public void addToCart(String userId, Product product, int quantity) {
    cartRepo.addToCart(userId, product, quantity);
  }

  public ViewCart getCart(String userId) {
    List<CartItem> cartItems = cartRepo.getItemsForCart(userId);

    return new ViewCart(userId, cartItems);
  }

  public void clearCart(String userId) {
    cartRepo.clearCart(userId);
  }
}
