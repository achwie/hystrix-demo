package achwie.hystrixdemo.loadgen;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.command.AddToCartCommand;
import achwie.hystrixdemo.loadgen.command.GetCatalogCommand;
import achwie.hystrixdemo.loadgen.command.HttpClientFactory;
import achwie.hystrixdemo.loadgen.command.LoginCommand;
import achwie.hystrixdemo.loadgen.command.LogoutCommand;
import achwie.hystrixdemo.loadgen.command.PlaceOrderCommand;
import achwie.hystrixdemo.loadgen.command.ViewOrdersCommand;
import achwie.hystrixdemo.loadgen.entities.Catalog;
import achwie.hystrixdemo.loadgen.entities.CatalogItem;
import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 13.02.2016, Achim Wiedemann
 */
public final class LoadGenerator {

  public static void main(String[] args) throws Exception {
    // We only have 10 test users in the auth-service...
    int numConcurrentUsers = 10;

    if (args.length > 0) {
      try {
        numConcurrentUsers = Integer.parseInt(args[0]);
      } catch (NumberFormatException e) {
        System.err.println("ERROR: Could not parse arg <concurrent-users> - going with default value of " + numConcurrentUsers);
      }
    }

    final ServicesConfig servicesConfig = new ServicesConfig(ServicesConfig.FILENAME_SERVICES_PROPERTIES);
    final String frontendBaseUrl = servicesConfig.getProperty(ServicesConfig.PROP_FRONTEND_BASEURL);

    final ExecutorService es = Executors.newFixedThreadPool(numConcurrentUsers);

    for (int i = 0; i < numConcurrentUsers; i++) {
      final CloseableHttpClient httpClient = HttpClientFactory.createHttpClient();
      final LoginCredentials loginCreds = createLoginCreds(i);
      final CallContext context = new CallContext(httpClient, loginCreds);
      final Agent agent;
      // One buyer, the rest just browsing
      if (i == 0) {
        agent = new BuyingUser(frontendBaseUrl, loginCreds);
      } else {
        agent = new BrowsingUser(frontendBaseUrl, loginCreds);
      }

      es.execute(new AgentRunner(agent, context));
    }
  }

  private static LoginCredentials createLoginCreds(int i) {
    return new LoginCredentials("test" + i, "test" + i);
  }
}

class AgentRunner implements Runnable {
  private final Agent agent;
  private final CallContext context;

  public AgentRunner(Agent agent, CallContext context) {
    this.agent = agent;
    this.context = context;
  }

  @Override
  public void run() {
    while (true) {
      try {
        agent.run(context);
      } catch (Exception e) {
        e.printStackTrace();
        try {
          Thread.sleep(5000); // Whoops! Give time to recover...
        } catch (InterruptedException ie) {
          Thread.interrupted(); // Reset interrupted flag
        }
      }
    }
  }
}

interface Agent {
  public void run(CallContext context) throws Exception;
}

class CallContext {
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

class BrowsingUser implements Agent {
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

class BuyingUser implements Agent {
  private final int maxItemsToBuy = 3;
  private final String frontendBaseUrl;
  private final LoginCredentials userCreds;

  public BuyingUser(String frontendBaseUrl, LoginCredentials userCreds) {
    this.frontendBaseUrl = frontendBaseUrl;
    this.userCreds = userCreds;
  }

  @Override
  public void run(CallContext context) throws Exception {
    final HttpClient httpClient = context.getHttpClient();

    new LoginCommand(frontendBaseUrl, userCreds).run(httpClient);

    final Catalog catalog = new GetCatalogCommand(frontendBaseUrl).run(httpClient);
    putRandomItemsIntoCart(catalog, httpClient);

    new PlaceOrderCommand(frontendBaseUrl).run(httpClient);

    new ViewOrdersCommand(frontendBaseUrl).run(httpClient);
    new LogoutCommand(frontendBaseUrl).run(httpClient);
  }

  private void putRandomItemsIntoCart(Catalog catalog, HttpClient httpClient) throws Exception {
    final List<CatalogItem> catalogItems = catalog.getItems();
    final Random rand = new Random();
    for (int i = 0; i < rand.nextInt(maxItemsToBuy); i++) {
      final CatalogItem item = catalogItems.get(rand.nextInt(catalogItems.size()));
      new AddToCartCommand(frontendBaseUrl, item.getId(), 1).run(httpClient);
    }
  }
}
