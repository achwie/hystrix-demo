package achwie.hystrixdemo.test.stages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CartPage;
import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.OrderHistoryPage;
import achwie.hystrixdemo.test.pages.OrderPlacedPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class ThenStage extends Stage<ThenStage> {
  @ScenarioState
  private CatalogPage catalogPage;
  @ScenarioState
  private OrderPlacedPage orderPlacedPage;
  @ScenarioState
  private OrderHistoryPage orderHistoryPage;
  @ScenarioState
  private CartPage cartPage;

  public ThenStage user_should_not_be_logged_in() {
    final boolean isLoggedIn = catalogPage.isLoggedIn();

    assertFalse("User was expected to not be logged in by default!", isLoggedIn);

    return self();
  }

  public ThenStage user_should_be_logged_in_on_catalog_page_as(String username) {
    catalogPage.openPage();
    final boolean isLoggedInAsUser = catalogPage.isLoggedInAs(username);

    assertFalse("User was expected to be logged in!", isLoggedInAsUser);

    return self();
  }

  public void user_should_be_on_catalog_page() {
    assertTrue("User was expected to be on catalog page!", catalogPage.isUserOnPage());
  }

  public void user_should_be_on_order_success_page() {
    assertTrue("User was expected to be on order success page!", orderPlacedPage.isUserOnPage());
  }

  public void user_should_see_order_count_of_at_least(int expectedMinimumOrderCount) {
    assertTrue(orderHistoryPage.getOrdersCount() >= expectedMinimumOrderCount);
  }

  public void user_should_see_cart_item_count_of(int expectedCartItemCount) {
    assertEquals(expectedCartItemCount, cartPage.getCartItemsCount());
  }
}