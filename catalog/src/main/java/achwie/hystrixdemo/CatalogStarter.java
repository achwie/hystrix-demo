package achwie.hystrixdemo;

import static achwie.hystrixdemo.util.ServicesConfig.FILENAME_SERVICES_PROPERTIES;
import static achwie.hystrixdemo.util.ServicesConfig.PROP_CATALOG_BASEURL;

import java.net.URI;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
public class CatalogStarter {
  public static void main(String[] args) throws Exception {
    final ServicesConfig servicesConfig = new ServicesConfig(FILENAME_SERVICES_PROPERTIES);
    final URI catalogServiceBaseUri = servicesConfig.getPropertyAsURI(PROP_CATALOG_BASEURL);
    final JettyStarter starter = new JettyStarter(catalogServiceBaseUri.getPort());

    starter.start();
  }
}
