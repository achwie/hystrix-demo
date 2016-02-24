package achwie.hystrixdemo;

import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Groups for Hystrix commands.
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
public enum CommandGroup implements HystrixCommandGroupKey {
  CATALOG_SERVICE, CART_SERVICE, AUTH_SERVICE, ORDER_SERVICE, STOCK_SERVICE;
}
