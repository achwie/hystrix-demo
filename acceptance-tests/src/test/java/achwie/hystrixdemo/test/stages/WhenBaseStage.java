package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CartPage;
import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;
import achwie.hystrixdemo.test.pages.OrderHistoryPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public abstract class WhenBaseStage<SELF extends WhenBaseStage<SELF>> extends Stage<SELF> {
  @ScenarioState
  protected LoginPage loginPage;
  @ScenarioState
  protected CatalogPage catalogPage;
  @ScenarioState
  protected CartPage cartPage;
  @ScenarioState
  protected OrderHistoryPage orderHistoryPage;
  @ScenarioStage
  protected WhenOnCartPageStage cartStage;
  @ScenarioStage
  protected WhenOnCatalogPageStage catalogStage;

  public WhenOnCatalogPageStage user_logs_in_with_credentials(String username, String password) {
    if (!loginPage.isUserOnPage())
      loginPage.openPage();

    loginPage.loginWithCredentials(username, password);

    return catalogStage;
  }

  public WhenOnCartPageStage user_opens_cart_page() {
    cartPage.openPage();

    return cartStage;
  }

  public void user_opnes_order_history_page() {
    orderHistoryPage.openPage();
  }

  public WhenOnCatalogPageStage user_logs_out() {
    if (!catalogPage.isUserOnPage())
      catalogPage.openPage();

    catalogPage.clickLogOutIfAvailable();

    return catalogStage;
  }
}