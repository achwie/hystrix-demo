package achwie.hystrixdemo.test.stages;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class WhenOnCatalogPageStage extends WhenBaseStage<WhenOnCatalogPageStage> {

  public WhenOnCatalogPageStage user_adds_item_$_to_cart_of_quantity_$(String productId, int quantity) {
    for (int i = 0; i < quantity; i++)
      catalogPage.addProductToCart(productId);

    return this;
  }
}