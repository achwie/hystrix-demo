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
  // TODO: Set 'http.conn-manager.timeout' to not wait infinitely for a
  // connection from the pool
  private final CloseableHttpClient httpClient;

  public RestTemplateFactory() {
    final HttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
    this.httpClient = HttpClients.custom().setConnectionManager(connMgr).build();
  }

  @Override
  public RestTemplate getObject() throws Exception {
    final HttpComponentsClientHttpRequestFactory clientRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
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
