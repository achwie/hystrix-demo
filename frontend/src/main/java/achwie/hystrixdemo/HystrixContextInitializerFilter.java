package achwie.hystrixdemo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * Initializes the {@link HystrixRequestContext} to enable request-scoped
 * caching (only for caching results if the same call is made multiple times in
 * a request).
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
public class HystrixContextInitializerFilter implements Filter {

  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    final HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
    try {
      chain.doFilter(req, res);
    } finally {
      hystrixRequestContext.shutdown();
    }
  }

  @Override
  public void destroy() {
  }
}
