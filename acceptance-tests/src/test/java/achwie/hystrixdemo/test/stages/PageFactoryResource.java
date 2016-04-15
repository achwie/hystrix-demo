package achwie.hystrixdemo.test.stages;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import achwie.hystrixdemo.test.pages.CartPage;
import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;
import achwie.hystrixdemo.test.pages.OrderAddressPage;
import achwie.hystrixdemo.test.pages.OrderHistoryPage;
import achwie.hystrixdemo.test.pages.OrderPlacedPage;

/**
 * Creates a new page factory with an initialized driver. This implies that all
 * pages created with the same page factory share the same browser window (and
 * session). For more info see the
 * <a href="http://jgiven.org/docs/lifecycle/">JGiven docs</a>.
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class PageFactoryResource extends ExternalResource {
  // TODO: Don't hardcode this!
  public static final String BASE_URL = "http://localhost:8080";
  public WebDriver driver;

  public CatalogPage createCatalogPage() {
    return new CatalogPage(BASE_URL, driver);
  }

  public LoginPage createLoginPage() {
    return new LoginPage(BASE_URL, driver);
  }

  public CartPage createCartPage() {
    return new CartPage(BASE_URL, driver);
  }

  public OrderAddressPage createOrderAddressPage() {
    return new OrderAddressPage(BASE_URL, driver);
  }

  public OrderPlacedPage createOrderPlacedPage() {
    return new OrderPlacedPage(BASE_URL, driver);
  }

  public OrderHistoryPage createOrderHistoryPage() {
    return new OrderHistoryPage(BASE_URL, driver);
  }

  protected void before() {
    driver = new HtmlUnitDriver(true);
  }

  protected void after() {
    driver.close();
  }

}
