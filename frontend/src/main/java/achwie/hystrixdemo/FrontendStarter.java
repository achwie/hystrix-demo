package achwie.hystrixdemo;

import static achwie.hystrixdemo.util.ServicesConfig.FILENAME_SERVICES_PROPERTIES;
import static achwie.hystrixdemo.util.ServicesConfig.PROP_FRONTEND_BASEURL;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import achwie.hystrixdemo.util.ServicesConfig;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 */
// TODO: This doesn't work yet - there's still some issue with taglibs. So for
// now I'll just deploy this webapp in Tomcat
public class FrontendStarter {
  public static void main(String[] args) throws Exception {
    final URI webrootUri = new File("src/main/webapp").toURI();

    // Set JSP to use Standard JavaC always
    System.setProperty("org.apache.jasper.compiler.disablejsr199", "false");

    final File jspTempDir = createJspTempDir();
    final ServicesConfig servicesConfig = new ServicesConfig(FILENAME_SERVICES_PROPERTIES);
    final URI frontendServiceBaseUri = servicesConfig.getPropertyAsURI(PROP_FRONTEND_BASEURL);

    final JettyStarter starter = new JettyStarter(frontendServiceBaseUri.getPort()) {
      @Override
      protected void configureContext(WebAppContext context) {
        super.configureContext(context);

        context.setAttribute("javax.servlet.context.tempdir", jspTempDir);

        // Set Classloader of Context to be sane (needed for JSTL)
        // JSP requires a non-System classloader, this simply wraps the
        // embedded System classloader in a way that makes it suitable
        // for JSP to use
        ClassLoader jspClassLoader = new URLClassLoader(new URL[0], this.getClass().getClassLoader());
        context.setClassLoader(jspClassLoader);

        // Add JSP Servlet (must be named "jsp")
        ServletHolder holderJsp = new ServletHolder("jsp", JspServlet.class);
        holderJsp.setInitOrder(0);

        // Add Default Servlet (must be named "default")
        ServletHolder holderDefault = new ServletHolder("default", DefaultServlet.class);
        holderDefault.setInitParameter("resourceBase", webrootUri.toASCIIString());
        holderDefault.setInitParameter("dirAllowed", "true");

        context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
            ".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/.*taglibs.*\\.jar$");
        context.addServlet(holderDefault, "/");
      }
    };

    starter.start();
  }

  private static File createJspTempDir() throws IOException {
    File tempDir = new File(System.getProperty("java.io.tmpdir"));
    File jspTempDir = new File(tempDir, "embedded-jetty-jsp");

    if (!jspTempDir.exists()) {
      if (!jspTempDir.mkdirs()) {
        throw new IOException("Unable to create JSP temp directory: " + jspTempDir);
      }
    }

    jspTempDir.deleteOnExit();
    return jspTempDir;
  }
}
