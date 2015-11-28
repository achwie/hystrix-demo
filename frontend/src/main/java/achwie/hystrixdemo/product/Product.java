package achwie.hystrixdemo.product;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
public class Product {
  private final String id;
  private final String name;

  public Product(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
