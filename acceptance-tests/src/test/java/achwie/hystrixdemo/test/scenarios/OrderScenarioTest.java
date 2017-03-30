package achwie.hystrixdemo.test.scenarios;

import static achwie.hystrixdemo.test.scenarios.TestUser.USER_TEST;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

import achwie.hystrixdemo.test.stages.GivenStage;
import achwie.hystrixdemo.test.stages.ThenStage;
import achwie.hystrixdemo.test.stages.WhenOnCatalogPageStage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class OrderScenarioTest extends ScenarioTest<GivenStage, WhenOnCatalogPageStage, ThenStage> {
  private static final String PRODUCT_ID_BOOK = "1";

  @Test
  public void logged_in_customer_should_be_able_to_place_an_order() {
    given()
        .user_is_logged_in_as(USER_TEST)
        .and().user_is_on_catalog_page();
    when()
        .user_adds_item_$_to_cart_of_quantity_$(PRODUCT_ID_BOOK, 1)
        .and().user_opens_cart_page()
        .and().user_follows_shipping_address_link()
        .and().user_enters_some_shipping_address()
        .and().user_places_order();
    then()
        .user_should_be_on_order_success_page();
  }

  @Test
  public void placed_orders_are_shown_in_order_history() {
    given()
        .user_is_logged_in_as(USER_TEST)
        .and().user_is_on_catalog_page();
    when()
        .user_adds_item_$_to_cart_of_quantity_$(PRODUCT_ID_BOOK, 1)
        .and().user_opens_cart_page()
        .and().user_follows_shipping_address_link()
        .and().user_enters_some_shipping_address()
        .and().user_places_order()
        .and().user_opnes_order_history_page();
    then()
        .user_should_see_order_count_of_at_least(1);
  }
}
