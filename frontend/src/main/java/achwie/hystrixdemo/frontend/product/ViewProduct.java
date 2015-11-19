package achwie.hystrixdemo.frontend.product;

/**
 * 
 * @author 11.11.2015, Achim Wiedemann
 */
public class ViewProduct {
  private String id;
  private String name;
  private int stockQuantity;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public void setStockQuantity(int quantity) {
    this.stockQuantity = quantity;
  }
}
