package achwie.hystrixdemo.test.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class CartPage {
  private final String baseUrl;
  private final WebDriver driver;

  public CartPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public void openPage() {
    driver.get(pageUrl());
  }

  private String pageUrl() {
    return baseUrl + "/view-cart";
  }

  public void clickShippingAddressLink() {
    final WebElement shippingAddressLink = driver.findElement(By.id("shipping-address-link"));
    shippingAddressLink.click();
  }

  public Object getCartItemsCount() {
    final List<WebElement> cartItemRows = driver.findElements(By.tagName("tr"));
    return !cartItemRows.isEmpty() ? cartItemRows.size() - 1 : 0; // -1 for the
                                                                  // headline
  }
}
