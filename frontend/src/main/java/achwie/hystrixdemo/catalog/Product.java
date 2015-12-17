package achwie.hystrixdemo.catalog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 10.11.2015, Achim Wiedemann
 */
public class Product {
  private final String id;
  private final String name;

  @JsonCreator
  public Product(@JsonProperty("id") String id, @JsonProperty("name") String name) {
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
