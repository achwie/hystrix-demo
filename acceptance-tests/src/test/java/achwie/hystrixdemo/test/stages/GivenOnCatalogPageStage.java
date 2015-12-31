package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CatalogPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class GivenOnCatalogPageStage extends Stage<GivenOnCatalogPageStage> {
  @ScenarioState
  private CatalogPage catalogPage;

  public GivenOnCatalogPageStage user_goes_to_login_page() {
    catalogPage.clickLogIn();

    return self();
  }

  public GivenOnCatalogPageStage user_is_not_logged_in() {
    catalogPage.clickLogOutIfAvailable();

    return self();
  }
}