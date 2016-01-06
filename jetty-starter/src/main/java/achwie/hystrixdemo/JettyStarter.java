package achwie.hystrixdemo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
public class JettyStarter {
  private final int listenPort;

  public JettyStarter(int listenPort) {
    this.listenPort = listenPort;
  }

  public void start() throws Exception {
    final WebAppContext webappContext = new WebAppContext();
    configureContext(webappContext);

    final Server server = new Server(listenPort);
    server.setHandler(webappContext);
    server.start();

    // The use of server.join() the will make the current thread join and
    // wait until the server is done executing.
    // See http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
    server.join();
  }

  protected void configureContext(WebAppContext context) {
    context.setContextPath("/");
    context.setResourceBase("src/main/webapp");
  }
}
