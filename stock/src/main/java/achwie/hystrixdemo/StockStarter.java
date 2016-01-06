package achwie.hystrixdemo;

import static achwie.hystrixdemo.util.ServicesConfig.FILENAME_SERVICES_PROPERTIES;
import static achwie.hystrixdemo.util.ServicesConfig.PROP_STOCK_BASEURL;

import java.net.URI;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
public class StockStarter {
  public static void main(String[] args) throws Exception {
    final ServicesConfig servicesConfig = new ServicesConfig(FILENAME_SERVICES_PROPERTIES);
    final URI stockServiceBaseUri = servicesConfig.getPropertyAsURI(PROP_STOCK_BASEURL);
    final JettyStarter starter = new JettyStarter(stockServiceBaseUri.getPort());

    starter.start();
  }
}
