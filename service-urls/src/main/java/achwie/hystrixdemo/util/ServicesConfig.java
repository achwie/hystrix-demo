package achwie.hystrixdemo.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 *
 */
public class ServicesConfig {
  public static final String FILENAME_SERVICES_PROPERTIES = "/config/application.properties";
  public static final String PROP_FRONTEND_BASEURL = "service.frontend.baseurl";
  public static final String PROP_STOCK_BASEURL = "service.stock.baseurl";
  public static final String PROP_CATALOG_BASEURL = "service.catalog.baseurl";
  public static final String PROP_CART_BASEURL = "service.cart.baseurl";
  public static final String PROP_AUTH_BASEURL = "service.auth.baseurl";
  public static final String PROP_ORDER_BASEURL = "service.order.baseurl";
  private static final Pattern REGEX_PROP_VARIABLE = Pattern.compile("\\$\\{([^\\}]+)\\}");
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
    final String rawPropValue = props.getProperty(key);
    final String propValuesWithExpandedVars = expandVariables(rawPropValue, props);

    return propValuesWithExpandedVars;
  }

  public URI getPropertyAsURI(String key) throws URISyntaxException {
    final String propValue = getProperty(key);
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

  private String expandVariables(String propValue, Properties variableProps) {
    String beforeExpand;

    // Support nested properties by expanding until there was no change anymore
    do {
      beforeExpand = propValue;
      propValue = expandVariablesNoNested(propValue, variableProps);
    } while (!beforeExpand.equals(propValue));

    return propValue;
  }

  private String expandVariablesNoNested(String propValue, Properties variableProps) {
    final Matcher m = REGEX_PROP_VARIABLE.matcher(propValue);

    // Extract variable names from property
    final Set<String> varNames = new HashSet<>();
    while (m.find())
      if (m.groupCount() > 0)
        varNames.add(m.group(1));

    // Replace variables with values in property
    String expandedPropValue = propValue;
    for (String varName : varNames) {
      final String varValue = variableProps.getProperty(varName);
      if (varValue != null) {
        expandedPropValue = replaceAllNoRegex(expandedPropValue, varName, varValue);
      }
    }

    return expandedPropValue;
  }

  /**
   * Replaces all occurrences of {@code search} in {@code input} with
   * {@code replacement}. In contrast to
   * {@link String#replaceAll(String, String)} this method does not use regex
   * matching, which can have undesired effects if your replacement string
   * contains some special regex chars/sequences like "<code>${</code>".
   *
   * @param input The string to perform the replacement on.
   * @param search The string to replace.
   * @param replacement The string to replace {@code search} with.
   * @return The given {@code input} with the replacements applied.
   */
  private String replaceAllNoRegex(String input, String search, String replacement) {
    String beforeReplace;
    do {
      beforeReplace = input;
      input = input.replace("${" + search + "}", replacement);
    } while (!beforeReplace.equals(input));

    return input;
  }
}
