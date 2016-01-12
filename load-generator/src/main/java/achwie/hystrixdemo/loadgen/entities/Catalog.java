package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONArray;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class Catalog {
  private final JSONArray catalogJson;

  private Catalog(JSONArray catalogJson) {
    this.catalogJson = catalogJson;
  }

  public int getItemCount() {
    return catalogJson.length();
  }

  public CatalogItem getItem(int i) {
    if (i < 0)
      throw new IndexOutOfBoundsException(String.format("Invalid index: %d (must be >= 0)", i));

    if (i >= getItemCount())
      throw new IndexOutOfBoundsException(String.format("Invalid index: %d (list length: %d)", i, getItemCount()));

    return new CatalogItem(catalogJson, i);
  }

  public static Catalog fromJson(String json) {
    return new Catalog(new JSONArray(json));
  }
}