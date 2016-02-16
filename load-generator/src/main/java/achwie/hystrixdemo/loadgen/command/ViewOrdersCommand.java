package achwie.hystrixdemo.loadgen.command;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class ViewOrdersCommand implements HttpClientCommand<String> {
  private final String viewOrdersUrl;

  public ViewOrdersCommand(String frontendBaseUrl) {
    this.viewOrdersUrl = frontendBaseUrl + "/my-orders";
  }

  @Override
  public String run(HttpClient httpClient) throws Exception {
    final HttpGet httpGet = new HttpGet(viewOrdersUrl);

    final HttpResponse resp = httpClient.execute(httpGet);
    final HttpEntity responseEntity = resp.getEntity();

    final byte[] content = HttpClientUtils.getContent(responseEntity);
    final Charset charset = HttpClientUtils.getCharset(responseEntity, HttpClientUtils.DEFAULT_CHARSET);

    // TODO: Parse result into objects
    return new String(content, charset);
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String frontendBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);
      new LoginCommand(frontendBaseUrl, LoginCredentials.USER_TEST).run(httpClient);
      new ViewOrdersCommand(frontendBaseUrl).run(httpClient);
    }
  }
}
