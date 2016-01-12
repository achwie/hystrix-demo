package achwie.hystrixdemo.loadgen.command;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class HttpClientFactory {
  public static final CloseableHttpClient createHttpClient() {
    final CloseableHttpClient httpClient = HttpClients.createDefault();

    return httpClient;
  }
}
