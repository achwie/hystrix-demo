package achwie.hystrixdemo.loadgen.entities;

import org.json.JSONObject;

/**
 * 
 * @author 13.01.2016, Achim Wiedemann
 *
 */
public class User {
  public static final String ANONYMOUS_ID = null;
  public static final String FIELD_USER_ID = "id";
  public static final String FIELD_USER_NAME = "userName";
  private final JSONObject userJson;

  private User(JSONObject userJson) {
    this.userJson = userJson;
  }

  public String getId() {
    return userJson.getString(FIELD_USER_ID);
  }

  public String getUserName() {
    return userJson.getString(FIELD_USER_NAME);
  }

  public boolean isAnonymous() {
    return getId() == ANONYMOUS_ID;
  }

  public static User fromJson(String jsonStr) {
    return new User(new JSONObject(jsonStr));
  }

  public String toString() {
    return "User[id: " + getId() + ", userName: " + getUserName() + "]";
  }
}
