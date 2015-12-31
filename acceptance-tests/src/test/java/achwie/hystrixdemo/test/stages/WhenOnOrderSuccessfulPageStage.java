package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.OrderHistoryPage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class WhenOnOrderSuccessfulPageStage extends Stage<WhenOnOrderSuccessfulPageStage> {
  @ScenarioState
  private OrderHistoryPage orderHistoryPage;
  @ScenarioStage
  private WhenStage whenStage;

  public WhenStage user_goes_to_order_history_page() {
    orderHistoryPage.openPage();

    return whenStage;
  }
}
