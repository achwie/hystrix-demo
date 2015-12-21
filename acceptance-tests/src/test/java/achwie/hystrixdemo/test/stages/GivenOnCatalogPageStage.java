package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import achwie.hystrixdemo.test.pages.CatalogPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class GivenOnCatalogPageStage extends Stage<GivenOnCatalogPageStage> {
  @ExpectedScenarioState
  private CatalogPage catalogPage;

  public void user_goes_to_login_page() {
    catalogPage.clickLogIn();
  }
}