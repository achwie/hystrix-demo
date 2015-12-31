package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.annotation.ScenarioStage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class WhenOnCartPageStage extends WhenBaseStage<WhenOnCartPageStage> {
  @ScenarioStage
  private WhenOnOrderAddressPageStage orderAddressStage;

  public WhenOnOrderAddressPageStage user_follows_shipping_address_link() {
    cartPage.clickShippingAddressLink();

    return orderAddressStage;
  }
}
