package achwie.hystrixdemo.loadgen.agent;

import org.apache.http.client.HttpClient;

import achwie.hystrixdemo.loadgen.entities.LoginCredentials;

/**
 * 
 * @author 22.02.2016, Achim Wiedemann
 */
public class CallContext {
  private final HttpClient httpClient;
  private final LoginCredentials loginCreds;

  public CallContext(HttpClient httpClient, LoginCredentials loginCreds) {
    this.httpClient = httpClient;
    this.loginCreds = loginCreds;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public LoginCredentials getLoginCreds() {
    return loginCreds;
  }
}