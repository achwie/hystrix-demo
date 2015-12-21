package achwie.hystrixdemo.test.stages;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;

/**
 * Creates a new page factory with an initialized driver. This implies that all
 * pages created with the same page factory share the same browser window (and
 * session).
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class PageFactoryResource extends ExternalResource {
  // TODO: Don't hardcode this!
  public static final String BASE_URL = "http://localhost:8080/hystrix-demo-frontend";
  public WebDriver driver;

  public CatalogPage createCatalogPage() {
    return new CatalogPage(BASE_URL, driver);
  }

  public LoginPage createLoginPage() {
    return new LoginPage(BASE_URL, driver);
  }

  protected void before() {
    driver = new HtmlUnitDriver(true);
  }

  protected void after() {
    driver.close();
  }
}
