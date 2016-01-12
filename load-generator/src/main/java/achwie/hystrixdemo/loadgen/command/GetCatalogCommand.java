package achwie.hystrixdemo.loadgen.command;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.Catalog;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class GetCatalogCommand implements Callable<Catalog> {
  private final String getCatalogUrl;

  public GetCatalogCommand(String catalogServiceBaseUrl) {
    this.getCatalogUrl = catalogServiceBaseUrl;
  }

  public Catalog call() throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final HttpGet httpGet = new HttpGet(getCatalogUrl);

      final CloseableHttpResponse resp = httpClient.execute(httpGet);
      final HttpEntity responseEntity = resp.getEntity();

      final byte[] content = HttpClientUtils.getContent(responseEntity);

      Charset charset = HttpClientUtils.getCharset(responseEntity);
      if (charset == null)
        charset = Charset.forName("UTF-8");

      final String catalogJsonStr = new String(content, charset);

      return Catalog.fromJson(catalogJsonStr);
    }
  }

  public static void main(String[] args) throws Exception {
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String catalogServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_CATALOG_BASEURL);
    final Catalog catalog = new GetCatalogCommand(catalogServiceBaseUrl).call();

    System.out.println("Catalog items:");
    for (int i = 0; i < catalog.getItemCount(); i++)
      System.out.println("  " + catalog.getItem(i));
  }
}
