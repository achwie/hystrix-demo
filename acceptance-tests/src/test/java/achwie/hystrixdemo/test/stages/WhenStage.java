package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CartPage;
import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class WhenStage extends Stage<WhenStage> {
  @ScenarioState
  private LoginPage loginPage;
  @ScenarioState
  private CatalogPage catalogPage;
  @ScenarioState
  private CartPage cartPage;
  @ScenarioStage
  private WhenOnCartPageStage cartPageStage;

  public WhenStage user_does_nothing() {
    return self();
  }

  public WhenStage user_logs_in_with_credentials(String username, String password) {
    if (catalogPage.isUserOnPage())
      catalogPage.clickLogIn();

    loginPage.loginWithCredentials(username, password);

    return self();
  }

  public WhenStage user_adds_item_to_cart(String productId, int quantity) {
    for (int i = 0; i < quantity; i++)
      catalogPage.addProductToCart(productId);

    return self();
  }

  public WhenOnCartPageStage user_goes_to_cart_page() {
    catalogPage.clickCartLink();

    return cartPageStage;
  }

  public void user_goes_to_order_history_page() {
    catalogPage.clickMyOrdersLink();
  }

  public WhenStage user_logs_out() {
    catalogPage.clickLogOutIfAvailable();

    return self();
  }
}