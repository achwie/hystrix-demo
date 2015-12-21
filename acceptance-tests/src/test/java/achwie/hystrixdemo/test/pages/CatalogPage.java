package achwie.hystrixdemo.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 *
 */
public class CatalogPage {
  private final String baseUrl;
  private final WebDriver driver;

  public CatalogPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public void openPage() {
    driver.get(pageUrl());
  }

  public boolean isLoggedIn() {
    final WebElement userStatus = getUserStatus();
    return !userStatus.getText().contains("not logged in");
  }

  public boolean isLoggedInAs(String username) {
    final WebElement userStatus = getUserStatus();
    return !userStatus.getText().contains("logged in as " + username);
  }

  public void clickLogIn() {
    final WebElement loginLink = driver.findElement(By.id("loginLink"));
    loginLink.click();
  }

  public boolean isUserOnPage() {
    return driver.getCurrentUrl().equals(pageUrl());
  }

  private String pageUrl() {
    return baseUrl + "/catalog";
  }

  private WebElement getUserStatus() {
    return driver.findElement(By.id("loginStatus"));
  }
}
