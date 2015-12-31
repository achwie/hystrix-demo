package achwie.hystrixdemo.test.scenarios;

import static achwie.hystrixdemo.test.scenarios.TestUser.USER_TEST;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

import achwie.hystrixdemo.test.stages.GivenStage;
import achwie.hystrixdemo.test.stages.ThenStage;
import achwie.hystrixdemo.test.stages.WhenStage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class OrderScenarioTest extends ScenarioTest<GivenStage, WhenStage, ThenStage> {
  private static final String PRODUCT_ID_BOOK = "1";

  @Test
  public void loggedInCustomerShouldBeAbleToPlaceAnOrder() {
    given().user_is_logged_in_as(USER_TEST.username, USER_TEST.password).and().user_is_on_catalog_page();
    when().user_adds_item_to_cart(PRODUCT_ID_BOOK, 1).and().user_goes_to_cart_page().and().user_goes_to_shipping_address_page().and()
        .user_enters_some_shipping_address().and().user_places_order();
    then().user_should_be_on_order_success_page();
  }

  @Test
  public void placedOrdersAreShownInOrderHistory() {
    given().user_is_logged_in_as(USER_TEST.username, USER_TEST.password).and().user_is_on_catalog_page();
    when().user_adds_item_to_cart(PRODUCT_ID_BOOK, 1).and().user_goes_to_cart_page().and().user_goes_to_shipping_address_page().and()
        .user_enters_some_shipping_address().and().user_places_order().and().user_goes_to_order_history_page();
    then().user_should_see_order_count_of_at_least(1);
  }
}
