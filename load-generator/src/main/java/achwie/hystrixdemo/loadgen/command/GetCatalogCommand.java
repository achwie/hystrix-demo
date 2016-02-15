package achwie.hystrixdemo.loadgen.command;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.Catalog;
import achwie.hystrixdemo.loadgen.entities.CatalogItem;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class GetCatalogCommand implements Callable<Catalog> {
  private final CloseableHttpClient httpClient;
  private final String getCatalogUrl;

  public GetCatalogCommand(CloseableHttpClient httpClient, String frontendBaseUrl) {
    this.httpClient = httpClient;
    this.getCatalogUrl = frontendBaseUrl + "/catalog";
  }

  public Catalog call() throws Exception {
    final HttpGet httpGet = new HttpGet(getCatalogUrl);

    final CloseableHttpResponse resp = httpClient.execute(httpGet);
    final HttpEntity responseEntity = resp.getEntity();

    final byte[] content = HttpClientUtils.getContent(responseEntity);
    final Charset charset = HttpClientUtils.getCharset(responseEntity, HttpClientUtils.DEFAULT_CHARSET);

    final String catalogStr = new String(content, charset);

    return parseCatalog(catalogStr);
  }

  private Catalog parseCatalog(String catalogHtml) {
    final Catalog catalog = new Catalog();

    final Pattern p = Pattern.compile(".*?<tr[^>]*>(.*?)</tr>.*?", Pattern.DOTALL | Pattern.MULTILINE);
    final Matcher m = p.matcher(catalogHtml);
    while (m.find() && m.groupCount() == 1) {
      final String productHtml = m.group(1).trim();
      final CatalogItem item = parseCatalogItem(productHtml);
      if (item != null)
        catalog.add(item);
    }

    return catalog;
  }

  private CatalogItem parseCatalogItem(String productHtml) {
    final Pattern p = Pattern.compile("^.*?<td>([^<]*)</td>.*name=\"productId\"\\s+value=\"([^\"]+)\".*", Pattern.DOTALL | Pattern.MULTILINE);
    final Matcher m = p.matcher(productHtml);
    if (m.find() && m.groupCount() == 2) {
      final String name = m.group(1);
      final String id = m.group(2);
      return new CatalogItem(id, name);
    }

    return null;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String catalogServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);
      final Catalog catalog = new GetCatalogCommand(httpClient, catalogServiceBaseUrl).call();

      System.out.println("Catalog items:");
      for (int i = 0; i < catalog.size(); i++)
        System.out.println(" " + catalog.getItem(i));
    }
  }
}
