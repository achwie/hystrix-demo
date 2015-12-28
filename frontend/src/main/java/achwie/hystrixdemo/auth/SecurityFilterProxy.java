package achwie.hystrixdemo.auth;

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
  private final SessionService sessionService;

  @Autowired
  public SecurityFilterProxy(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    try {
      if (req instanceof HttpServletRequest) {
        sessionService.setServletRequestForCurrentThread((HttpServletRequest) req);
      }
      chain.doFilter(req, res);
    } finally {
      sessionService.removeServletRequestForCurrentThread();
    }
  }

  @Override
  public void destroy() {
  }
}
