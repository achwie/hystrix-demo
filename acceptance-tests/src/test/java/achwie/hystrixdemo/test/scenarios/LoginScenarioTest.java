package achwie.hystrixdemo.test.scenarios;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

import achwie.hystrixdemo.test.stages.GivenStage;
import achwie.hystrixdemo.test.stages.ThenStage;
import achwie.hystrixdemo.test.stages.WhenStage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class LoginScenarioTest extends ScenarioTest<GivenStage, WhenStage, ThenStage> {
  @Test
  public void userNotLoggedInByDefault() {
    given().user_is_on_catalog_page();
    when().user_does_nothing();
    then().user_should_not_be_logged_in();
  }

  @Test
  public void userLoggedInAfterLogin() {
    given().user_is_on_login_page();
    when().user_logs_in_with_credentials("john", "doe");
    then().user_should_be_logged_in_on_catalog_page_as("john");
  }

  @Test
  public void userRedirectedToPreviousPage() {
    given().user_is_on_catalog_page().and().user_goes_to_login_page();
    when().user_logs_in_with_credentials("john", "doe");
    then().user_should_be_on_catalog_page();
  }

  @Test
  public void userRedirectedToCatalogPageIfNoPreviousPage() {
    given().user_is_on_login_page();
    when().user_logs_in_with_credentials("john", "doe");
    then().user_should_be_on_catalog_page();
  }
}
