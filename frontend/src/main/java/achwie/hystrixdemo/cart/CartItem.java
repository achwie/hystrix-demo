package achwie.hystrixdemo.cart;

import achwie.hystrixdemo.product.Product;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class CartItem {
  private final Product product;
  private int quantity;

  public CartItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void decreaseQuantity(int by) {
    quantity = Math.max(0, quantity - by); // make sure to be > 0
  }

  public void increaseQuantity(int by) {
    quantity = Math.max(0, quantity + by); // make sure to be > 0
  }
}
