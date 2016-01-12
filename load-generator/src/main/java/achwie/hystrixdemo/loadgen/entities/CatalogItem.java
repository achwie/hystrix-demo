package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONArray;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class CatalogItem {
  public static final String FIELD_ID = "id";
  public static final String FIELD_NAME = "name";
  private final JSONArray catalogJson;
  private final int index;

  CatalogItem(JSONArray catalogJson, int index) {
    this.catalogJson = catalogJson;
    this.index = index;
  }

  public String getId() {
    return catalogJson.getJSONObject(index).getString(FIELD_ID);
  }

  public String getName() {
    return catalogJson.getJSONObject(index).getString(FIELD_NAME);
  }

  public String toString() {
    return "CatalogItem[id: " + getId() + ", name: " + getName() + "]";
  }
}
