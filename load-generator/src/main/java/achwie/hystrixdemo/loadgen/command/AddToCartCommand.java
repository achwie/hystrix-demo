package achwie.hystrixdemo.loadgen.command;

import java.util.concurrent.Callable;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.CartItem;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class AddToCartCommand implements Callable<Void> {
  private final String addToCartUrl;
  private final CartItem itemToAdd;

  public AddToCartCommand(String cartServiceBaseUrl, String cartId, CartItem itemToAdd) {
    this.addToCartUrl = cartServiceBaseUrl + "/" + cartId;
    this.itemToAdd = itemToAdd;
  }

  @Override
  public Void call() throws Exception {
    try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {

      final StringEntity entity = new StringEntity(itemToAdd.toJson());
      entity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

      final HttpPost httpPost = new HttpPost(addToCartUrl);
      httpPost.setEntity(entity);

      httpClient.execute(httpPost);

      return null;
    }
  }

  public static void main(String[] args) throws Exception {
    final String sessionId = "1";
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String cartServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_CART_BASEURL);

    final CartItem itemToAdd = new CartItem("A123", "Something awesome", 1);

    new AddToCartCommand(cartServiceBaseUrl, sessionId, itemToAdd).call();
  }
}
