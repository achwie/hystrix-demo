package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.CartPage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class WhenOnCartPageStage extends Stage<WhenOnCartPageStage> {
  @ScenarioState
  private CartPage cartPage;
  @ScenarioStage
  private WhenOnOrderAddressPageStage orderAddressStage;

  public WhenOnOrderAddressPageStage user_goes_to_shipping_address_page() {
    cartPage.clickShippingAddressLink();

    return orderAddressStage;
  }
}
