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
public class CartScenarioTest extends ScenarioTest<GivenStage, WhenStage, ThenStage> {
  private static final String PRODUCT_ID_BOOK = "1";

  @Test
  public void itemThatWasAddedBeforeLoginIsStillInCartAfterLogin() {
    given().user_is_on_catalog_page().and().user_is_not_logged_in();
    when().user_adds_item_to_cart(PRODUCT_ID_BOOK, 1).and().user_logs_in_with_credentials(USER_TEST.username, USER_TEST.password).and().user_goes_to_cart_page();
    then().user_should_see_cart_item_count_of(1);
  }

  @Test
  public void cartIsClearedAfterLogout() {
    given().user_is_logged_in_as(USER_TEST.username, USER_TEST.password);
    when().user_adds_item_to_cart(PRODUCT_ID_BOOK, 1).and().user_logs_out().and().user_goes_to_cart_page();
    then().user_should_see_cart_item_count_of(0);
  }

  @Test
  public void addedItemAppearsInCart() {
    given().user_is_logged_in_as(USER_TEST.username, USER_TEST.password);
    when().user_adds_item_to_cart(PRODUCT_ID_BOOK, 1).and().user_goes_to_cart_page();
    then().user_should_see_cart_item_count_of(1);
  }
}
