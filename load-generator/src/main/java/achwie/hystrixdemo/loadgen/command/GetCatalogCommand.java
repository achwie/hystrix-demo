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
  private final CloseableHttpClient httpClient;
  private final String getCatalogUrl;

  public GetCatalogCommand(CloseableHttpClient httpClient, String catalogServiceBaseUrl) {
    this.httpClient = httpClient;
    this.getCatalogUrl = catalogServiceBaseUrl;
  }

  public Catalog call() throws Exception {
    final HttpGet httpGet = new HttpGet(getCatalogUrl);

    final CloseableHttpResponse resp = httpClient.execute(httpGet);
    final HttpEntity responseEntity = resp.getEntity();

    final byte[] content = HttpClientUtils.getContent(responseEntity);
    final Charset charset = HttpClientUtils.getCharset(responseEntity, HttpClientUtils.DEFAULT_CHARSET);

    final String catalogJsonStr = new String(content, charset);

    return Catalog.fromJson(catalogJsonStr);
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String catalogServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_CATALOG_BASEURL);
      final Catalog catalog = new GetCatalogCommand(httpClient, catalogServiceBaseUrl).call();

      System.out.println("Catalog items:");
      for (int i = 0; i < catalog.size(); i++)
        System.out.println(" " + catalog.getItem(i));
    }
  }
}
