package achwie.hystrixdemo.loadgen.agent;

import org.apache.http.client.HttpClient;

import achwie.hystrixdemo.loadgen.command.GetCatalogCommand;
import achwie.hystrixdemo.loadgen.command.LoginCommand;
import achwie.hystrixdemo.loadgen.command.LogoutCommand;
import achwie.hystrixdemo.loadgen.command.ViewOrdersCommand;
import achwie.hystrixdemo.loadgen.entities.Catalog;
import achwie.hystrixdemo.loadgen.entities.LoginCredentials;

/**
 * 
 * @author 22.02.2016, Achim Wiedemann
 */
public class BrowsingUser implements Agent {
  private final String frontendBaseUrl;
  private final LoginCredentials userCreds;

  public BrowsingUser(String frontendBaseUrl, LoginCredentials userCreds) {
    this.frontendBaseUrl = frontendBaseUrl;
    this.userCreds = userCreds;
  }

  @Override
  public void run(CallContext context) throws Exception {
    final HttpClient httpClient = context.getHttpClient();

    viewCatalog(httpClient);
    viewMyOrders(httpClient);
  }

  private Catalog viewCatalog(HttpClient httpClient) throws Exception {
    return new GetCatalogCommand(frontendBaseUrl).run(httpClient);
  }

  private void viewMyOrders(HttpClient httpClient) throws Exception {
    new LoginCommand(frontendBaseUrl, userCreds).run(httpClient);
    new ViewOrdersCommand(frontendBaseUrl).run(httpClient);
    new LogoutCommand(frontendBaseUrl).run(httpClient);
  }
}