package achwie.hystrixdemo;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author 17.02.2016, Achim Wiedemann
 *
 */
@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate> {
  private static final int TIMEOUT_CONN_FROM_POOL_MILLIS = 3000;
  private static final int TIMEOUT_CONN_ESTABLISHED_MILLIS = 3000;
  private static final int TIMEOUT_CONN_SOCK_READ_MILLIS = 3000;
  private final CloseableHttpClient httpClient;

  public RestTemplateFactory() {
    final HttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
    this.httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
  }

  @Override
  public RestTemplate getObject() throws Exception {
    final HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    clientRequestFactory.setConnectionRequestTimeout(TIMEOUT_CONN_FROM_POOL_MILLIS);
    clientRequestFactory.setConnectTimeout(TIMEOUT_CONN_ESTABLISHED_MILLIS);
    clientRequestFactory.setReadTimeout(TIMEOUT_CONN_SOCK_READ_MILLIS);
    final RestTemplate restTemplate = new RestTemplate();
    restTemplate.setRequestFactory(clientRequestFactory);

    return restTemplate;
  }

  @Override
  public Class<?> getObjectType() {
    return RestTemplate.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }
}
