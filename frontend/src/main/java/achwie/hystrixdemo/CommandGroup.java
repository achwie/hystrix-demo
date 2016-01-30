package achwie.hystrixdemo;

import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Groups for Hystrix commands.
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
public enum CommandGroup implements HystrixCommandGroupKey {
  CATALOG_GET, CATALOG_GET_ITEM, CART_GET, CART_CLEAR, CART_ADD_ITEM, AUTH_LOGIN, AUTH_LOGOUT, ORDER_PLACE, ORDER_GET_FOR_USER, STOCK_GET_QUANTITY, STOCK_PLACE_HOLD;
}
