package achwie.hystrixdemo.test.scenarios;

import static achwie.hystrixdemo.test.scenarios.TestUser.USER_JOHN;

import org.junit.Test;

import com.tngtech.jgiven.junit.ScenarioTest;

import achwie.hystrixdemo.test.stages.GivenStage;
import achwie.hystrixdemo.test.stages.ThenStage;
import achwie.hystrixdemo.test.stages.WhenOnCatalogPageStage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class LoginScenarioTest extends ScenarioTest<GivenStage, WhenOnCatalogPageStage, ThenStage> {

  @Test
  public void user_not_logged_in_by_default() {
    given()
        .user_is_on_catalog_page()
        .and().user_is_not_logged_in();
    then()
        .user_should_not_be_logged_in();
  }

  @Test
  public void user_logged_in_after_login() {
    given()
        .user_is_on_login_page();
    when()
        .user_logs_in_as(USER_JOHN);
    then()
        .user_should_be_logged_in_on_catalog_page_as(USER_JOHN.username);
  }

  @Test
  public void user_redirected_to_previous_page() {
    given()
        .user_is_on_catalog_page()
        .and().user_is_not_logged_in()
        .and().user_follows_login_link();
    when()
        .user_logs_in_as(USER_JOHN);
    then()
        .user_should_be_on_catalog_page();
  }

  @Test
  public void user_redirected_to_catalog_page_if_no_previous_page() {
    given()
        .user_is_on_login_page();
    when()
        .user_logs_in_as(USER_JOHN);
    then()
        .user_should_be_on_catalog_page();
  }
}
