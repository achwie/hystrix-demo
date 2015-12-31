package achwie.hystrixdemo.test.stages;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class WhenOnCatalogPageStage extends WhenBaseStage<WhenOnCatalogPageStage> {

  public WhenOnCatalogPageStage user_adds_item_to_cart(String productId, int quantity) {
    for (int i = 0; i < quantity; i++)
      catalogPage.addProductToCart(productId);

    return self();
  }
}