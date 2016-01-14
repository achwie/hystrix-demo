package achwie.hystrixdemo.loadgen.command;

import java.nio.charset.Charset;
import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.loadgen.entities.User;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class LoginCommand implements Callable<User> {
  private final String loginUrl;
  private final LoginCredentials loginCreds;

  public LoginCommand(String authServiceBaseUrl, String sessionId, LoginCredentials loginCreds) {
    this.loginUrl = authServiceBaseUrl + "/" + sessionId;
    this.loginCreds = loginCreds;
  }

  @Override
  public User call() throws Exception {
    try (CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {

      final StringEntity credsEntity = new StringEntity(loginCreds.toJsonString());
      credsEntity.setContentType(ContentType.APPLICATION_JSON.getMimeType());

      final HttpPost httpPost = new HttpPost(loginUrl);
      httpPost.setEntity(credsEntity);

      final CloseableHttpResponse response = httpClient.execute(httpPost);
      final HttpEntity responseEntity = response.getEntity();

      final byte[] content = HttpClientUtils.getContent(responseEntity);
      final Charset charset = HttpClientUtils.getCharset(responseEntity, HttpClientUtils.DEFAULT_CHARSET);

      final String userJsonStr = new String(content, charset);

      return User.fromJson(userJsonStr);
    }
  }

  public static void main(String[] args) throws Exception {
    final String sessionId = "1";
    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String authServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_AUTH_BASEURL);
    final User user = new LoginCommand(authServiceBaseUrl, sessionId, new LoginCredentials("test", "test")).call();

    System.out.println(String.format("User for session %s: %s ", sessionId, user));
  }
}
