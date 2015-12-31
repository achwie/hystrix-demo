package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CartPage;
import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;
import achwie.hystrixdemo.test.pages.OrderAddressPage;
import achwie.hystrixdemo.test.pages.OrderHistoryPage;
import achwie.hystrixdemo.test.pages.OrderPlacedPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class GivenStage extends Stage<GivenStage> {
  @ScenarioRule
  private final PageFactoryResource pageFactory = new PageFactoryResource();
  @ScenarioStage
  private GivenOnCatalogPageStage catalogStage;
  @ScenarioState
  private CatalogPage catalogPage;
  @ScenarioState
  private LoginPage loginPage;
  @ScenarioState
  private CartPage cartPage;
  @ScenarioState
  private OrderAddressPage orderAddressPage;
  @ScenarioState
  private OrderPlacedPage orderPlacedPage;
  @ScenarioState
  private OrderHistoryPage orderHistoryPage;

  @BeforeScenario
  public void setUp() {
    catalogPage = pageFactory.createCatalogPage();
    loginPage = pageFactory.createLoginPage();
    cartPage = pageFactory.createCartPage();
    orderAddressPage = pageFactory.createOrderAddressPage();
    orderPlacedPage = pageFactory.createOrderPlacedPage();
    orderHistoryPage = pageFactory.createOrderHistoryPage();
  }

  public GivenOnCatalogPageStage user_is_on_catalog_page() {
    catalogPage.openPage();
    return catalogStage;
  }

  public GivenStage user_is_on_login_page() {
    loginPage.openPage();
    return self();
  }

  public GivenStage user_is_logged_in_as(String username, String password) {
    loginPage.openPage();
    loginPage.loginWithCredentials(username, password);
    return self();
  }
}