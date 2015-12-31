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
public class OrderHistoryPage {
  private final String baseUrl;
  private final WebDriver driver;

  public OrderHistoryPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public void openPage() {
    driver.get(pageUrl());
  }

  private String pageUrl() {
    return baseUrl + "/my-orders";
  }

  public int getOrdersCount() {
    final List<WebElement> orderSeparators = driver.findElements(By.tagName("hr"));

    return (orderSeparators != null) ? orderSeparators.size() : 0;
  }

}
