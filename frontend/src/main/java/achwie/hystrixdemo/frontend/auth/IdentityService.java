package achwie.hystrixdemo.frontend.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 19.11.2015, Achim Wiedemann
 */
@Component
public class IdentityService {
  public String getUserId(HttpServletRequest req) {
    // TODO: Use user ID instead of session ID
    return req.getSession().getId();
  }
}
