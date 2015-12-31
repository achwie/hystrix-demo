package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.annotation.ScenarioState;

import achwie.hystrixdemo.test.pages.OrderAddressPage;

/**
 * 
 * @author 30.12.2015, Achim Wiedemann
 *
 */
public class WhenOnOrderAddressPageStage extends WhenBaseStage<WhenOnOrderAddressPageStage> {
  public static final String SOME_ADDRESS_NAME = "John Doe";
  public static final String SOME_ADDRESS_ADDRESS = "102-3456 Dough St";
  public static final String SOME_ADDRESS_CITY = "St. Johns  NL";
  public static final String SOME_ADDRESS_ZIP = "A1B 2C3";
  public static final String SOME_ADDRESS_COUNTRY = "Canada";
  @ScenarioState
  private OrderAddressPage orderAdressPage;
  @ScenarioStage
  private WhenOnOrderSuccessfulPageStage orderSuccessfulStage;

  public WhenOnOrderAddressPageStage user_enters_some_shipping_address() {
    orderAdressPage.enterShippingAddress(SOME_ADDRESS_NAME, SOME_ADDRESS_ADDRESS, SOME_ADDRESS_CITY, SOME_ADDRESS_ZIP, SOME_ADDRESS_COUNTRY);

    return self();
  }

  public WhenOnOrderSuccessfulPageStage user_places_order() {
    orderAdressPage.clickPlaceOrder();

    return orderSuccessfulStage;
  }
}
