package achwie.hystrixdemo.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 *
 */
public class LoginPage {
  private String baseUrl;
  private WebDriver driver;

  public LoginPage(String baseUrl, WebDriver driver) {
    this.baseUrl = baseUrl;
    this.driver = driver;
  }

  public void openPage() {
    driver.get(pageUrl());
  }

  public boolean isUserOnPage() {
    return driver.getCurrentUrl().equals(pageUrl());
  }

  public void loginWithCredentials(String username, String password) {
    final WebElement usernameField = driver.findElement(By.name("username"));
    final WebElement passwordField = driver.findElement(By.name("password"));
    final WebElement loginForm = driver.findElement(By.tagName("form"));

    usernameField.sendKeys(username);
    passwordField.sendKeys(password);
    loginForm.submit();
  }

  private String pageUrl() {
    return baseUrl + "/login";
  }
}
