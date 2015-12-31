package achwie.hystrixdemo.test.pages;

import org.openqa.selenium.WebDriver;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class OrderPlacedPage {
  private String baseUrl;
  private WebDriver driver;

  public OrderPlacedPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public boolean isUserOnPage() {
    return driver.getCurrentUrl().equals(pageUrl());
  }

  private String pageUrl() {
    return baseUrl + "/order-placed";
  }
}
