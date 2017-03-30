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
public class CartScenarioTest extends ScenarioTest<GivenStage, WhenOnCatalogPageStage, ThenStage> {
  private static final String PRODUCT_ID_BOOK = "1";

  @Test
  public void item_that_was_added_before_login_is_still_in_cart_after_login() {
    given().user_is_on_catalog_page().and().user_is_not_logged_in();
    when().user_adds_item_$_to_cart_of_quantity_$(PRODUCT_ID_BOOK, 1).and().user_logs_in_as(USER_TEST).and().user_opens_cart_page();
    then().user_should_see_cart_item_count_of(1);
  }

  @Test
  public void cart_is_cleared_after_logout() {
    given().user_is_logged_in_as(USER_TEST);
    when().user_adds_item_$_to_cart_of_quantity_$(PRODUCT_ID_BOOK, 1).and().user_logs_out().and().user_opens_cart_page();
    then().user_should_see_cart_item_count_of(0);
  }

  @Test
  public void added_item_appears_in_cart() {
    given().user_is_logged_in_as(USER_TEST);
    when().user_adds_item_$_to_cart_of_quantity_$(PRODUCT_ID_BOOK, 1).and().user_opens_cart_page();
    then().user_should_see_cart_item_count_of(1);
  }
}
