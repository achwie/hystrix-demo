package achwie.hystrixdemo.loadgen.entities;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class CatalogItem {
  private final String id;
  private final String name;

  public CatalogItem(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return "CatalogItem[id: " + getId() + ", name: " + getName() + "]";
  }
}
