package achwie.hystrixdemo.frontend.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author 23.11.2015, Achim Wiedemann
 */
// Intentionally didn't use Spring Security to keep it even more simple
@Component("securityFilterProxy")
public class SecurityFilterProxy implements Filter {
  private final IdentityService idService;

  @Autowired
  public SecurityFilterProxy(IdentityService idService) {
    this.idService = idService;
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    try {
      if (req instanceof HttpServletRequest) {
        idService.setServletRequestForCurrentThread((HttpServletRequest) req);
      }
      chain.doFilter(req, res);
    } finally {
      idService.removeServletRequestForCurrentThread();
    }
  }

  @Override
  public void destroy() {
  }
}
