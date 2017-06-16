package achwie.hystrixdemo.loadgen.command;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class LoginCommand implements HttpClientCommand<Boolean> {
  private final String loginUrl;
  private final LoginCredentials loginCreds;

  public LoginCommand(String frontendBaseUrl, LoginCredentials loginCreds) {
    this.loginUrl = frontendBaseUrl + "/login";
    this.loginCreds = loginCreds;
  }

  @Override
  public Boolean run(HttpClient httpClient) throws Exception {
    final List<NameValuePair> params = new ArrayList<>();
    params.add(new BasicNameValuePair("username", loginCreds.getUsername()));
    params.add(new BasicNameValuePair("password", loginCreds.getPassword()));

    final HttpPost httpPost = new HttpPost(loginUrl);
    httpPost.setEntity(new UrlEncodedFormEntity(params));

    final HttpResponse response = httpClient.execute(httpPost);

    // Even if we don't care for the content: make sure resources are released!
    // We consume the HTTP entity in contrast to simply closing the response,
    // because this way HttpClient will try to re-use the connection, whereas
    // when closing the response the connection will be discarded. When doing
    // lots of fast subsequent requests this prevents the OS from using up
    // all it's client-sockets, which will manifest itself in a
    // SocketException. This is subtle, but it's mentioned in the docs at
    // https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html
    EntityUtils.consume(response.getEntity());

    final int status = response.getStatusLine().getStatusCode();
    if (status == HttpStatus.SC_MOVED_TEMPORARILY) {
      final Header locationHeader = response.getFirstHeader("Location");
      if (locationHeader != null)
        return !locationHeader.getValue().matches(".*/login;.*$");
    }

    return false;
  }

  public static void main(String[] args) throws Exception {
    try (final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient()) {
      final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
      final String authServiceBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);
      final boolean success = new LoginCommand(authServiceBaseUrl, LoginCredentials.USER_TEST).run(httpClient);

      System.out.println(String.format("Login for user test successful: %s ", success));
    }
  }
}
