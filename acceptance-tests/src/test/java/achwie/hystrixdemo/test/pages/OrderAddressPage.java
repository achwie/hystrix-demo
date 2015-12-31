package achwie.hystrixdemo.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class OrderAddressPage {
  private final String baseUrl;
  private final WebDriver driver;

  public OrderAddressPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public void openPage() {
    driver.get(pageUrl());
  }

  public void enterShippingAddress(String name, String address, String city, String zip, String country) {
    final WebElement nameField = driver.findElement(By.name("name"));
    final WebElement addressField = driver.findElement(By.name("address"));
    final WebElement cityField = driver.findElement(By.name("city"));
    final WebElement zipField = driver.findElement(By.name("zip"));
    final WebElement countryField = driver.findElement(By.name("country"));

    nameField.sendKeys(name);
    addressField.sendKeys(address);
    cityField.sendKeys(city);
    zipField.sendKeys(zip);
    countryField.sendKeys(country);
  }

  private String pageUrl() {
    return baseUrl + "/order-address";
  }

  public void clickPlaceOrder() {
    final WebElement placeOrderBtn = driver.findElement(By.id("place-order-btn"));
    placeOrderBtn.click();
  }
}
