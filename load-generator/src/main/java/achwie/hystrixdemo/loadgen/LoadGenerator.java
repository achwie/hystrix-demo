package achwie.hystrixdemo.loadgen;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.CloseableHttpClient;

import achwie.hystrixdemo.loadgen.agent.Agent;
import achwie.hystrixdemo.loadgen.agent.BrowsingUser;
import achwie.hystrixdemo.loadgen.agent.BuyingUser;
import achwie.hystrixdemo.loadgen.agent.CallContext;
import achwie.hystrixdemo.loadgen.command.HttpClientFactory;
import achwie.hystrixdemo.loadgen.entities.LoginCredentials;
import achwie.hystrixdemo.util.ServicesConfig;

/**
 * A simple load generator capable of concurrently running some agents,
 * simulating user actions.
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
    // Just assuming that our (test) users have credentials like test1 / test1
    return new LoginCredentials("test" + i, "test" + i);
  }
}
