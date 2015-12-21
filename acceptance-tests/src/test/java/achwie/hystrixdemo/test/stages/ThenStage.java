package achwie.hystrixdemo.test.stages;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import achwie.hystrixdemo.test.pages.CatalogPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class ThenStage extends Stage<ThenStage> {
  @ExpectedScenarioState
  private CatalogPage catalogPage;

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
}