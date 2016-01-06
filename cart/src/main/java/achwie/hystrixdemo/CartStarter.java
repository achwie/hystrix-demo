package achwie.hystrixdemo;

import static achwie.hystrixdemo.util.ServicesConfig.FILENAME_SERVICES_PROPERTIES;
import static achwie.hystrixdemo.util.ServicesConfig.PROP_CART_BASEURL;

import java.net.URI;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
public class CartStarter {
  public static void main(String[] args) throws Exception {
    final ServicesConfig servicesConfig = new ServicesConfig(FILENAME_SERVICES_PROPERTIES);
    final URI cartServiceBaseUri = servicesConfig.getPropertyAsURI(PROP_CART_BASEURL);
    final JettyStarter starter = new JettyStarter(cartServiceBaseUri.getPort());

    starter.start();
  }
}
