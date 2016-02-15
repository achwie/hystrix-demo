package achwie.hystrixdemo.loadgen.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class Catalog {
  private final List<CatalogItem> items = new ArrayList<>();

  public int size() {
    return items.size();
  }

  public void add(CatalogItem item) {
    items.add(item);
  }

  public List<CatalogItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public CatalogItem getItem(int i) {
    return items.get(i);
  }
}