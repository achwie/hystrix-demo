package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.annotation.ScenarioStage;

import achwie.hystrixdemo.test.pages.CatalogPage;
import achwie.hystrixdemo.test.pages.LoginPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class GivenStage extends Stage<GivenStage> {
  @ScenarioRule
  private final PageFactoryResource pageFactory = new PageFactoryResource();
  @ScenarioStage
  private GivenOnCatalogPageStage givenOnCatalogPageStage;
  @ProvidedScenarioState
  private CatalogPage catalogPage;
  @ProvidedScenarioState
  private LoginPage loginPage;

  @BeforeScenario
  public void setUp() {
    catalogPage = pageFactory.createCatalogPage();
    loginPage = pageFactory.createLoginPage();
  }

  public GivenOnCatalogPageStage user_is_on_catalog_page() {
    catalogPage.openPage();
    return givenOnCatalogPageStage;
  }

  public void user_is_on_login_page() {
    loginPage.openPage();
  }
}