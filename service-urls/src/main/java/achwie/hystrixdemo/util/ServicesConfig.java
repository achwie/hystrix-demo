package achwie.hystrixdemo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 *
 */
public class ServicesConfig {
  public static final String FILENAME_SERVICES_PROPERTIES = "/services.properties";
  public static final String PROP_FRONTEND_BASEURL = "service.frontend.baseurl";
  public static final String PROP_STOCK_BASEURL = "service.stock.baseurl";
  public static final String PROP_CATALOG_BASEURL = "service.catalog.baseurl";
  public static final String PROP_CART_BASEURL = "service.cart.baseurl";
  public static final String PROP_AUTH_BASEURL = "service.auth.baseurl";
  public static final String PROP_ORDER_BASEURL = "service.order.baseurl";
  private final Properties props;

  public ServicesConfig(Properties props) {
    this.props = new Properties(props);
  }

  public ServicesConfig(File file) throws FileNotFoundException, IOException {
    this.props = new Properties();
    props.load(new FileReader(file));
  }

  public ServicesConfig(String filenameInClasspath) throws IOException {
    this.props = new Properties();
    props.load(ServicesConfig.class.getResourceAsStream(filenameInClasspath));
  }

  public String getProperty(String key) {
    return props.getProperty(key);
  }

  public URI getPropertyAsURI(String key) throws URISyntaxException {
    final String propValue = props.getProperty(key);
    URI uri = new URI(propValue);

    if (uri.getPort() == -1) {
      final int port;

      switch (uri.getScheme()) {
      case "http":
        port = 80;
        break;
      case "https":
        port = 443;
        break;
      default:
        port = -1;
      }

      uri = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), port, uri.getPath(), uri.getQuery(), uri.getFragment());
    }

    return uri;
  }
}
