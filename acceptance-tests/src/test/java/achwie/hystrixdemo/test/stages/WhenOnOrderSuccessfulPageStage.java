package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.OrderPlacedPage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class WhenOnOrderSuccessfulPageStage extends WhenBaseStage<WhenOnOrderSuccessfulPageStage> {
  @ScenarioState
  private OrderPlacedPage orderPlacedPage;

  public WhenOnCatalogPageStage user_follows_catalog_link() {
    orderPlacedPage.clickBackToCatalog();

    return catalogStage;
  }
}
