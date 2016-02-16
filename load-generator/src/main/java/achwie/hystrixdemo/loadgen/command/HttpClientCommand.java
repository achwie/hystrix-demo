package achwie.hystrixdemo.loadgen.command;

import org.apache.http.client.HttpClient;

/**
 * 
 * @author 16.02.2016, Achim Wiedemann
 *
 */
public interface HttpClientCommand<T> {
  public T run(HttpClient httpClient) throws Exception;
}
