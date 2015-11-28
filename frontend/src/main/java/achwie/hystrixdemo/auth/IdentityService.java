package achwie.hystrixdemo.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**
 * 
 * @author 19.11.2015, Achim Wiedemann
 */
@Component
public class IdentityService {
  private ThreadLocal<HttpServletRequest> servletRequests = new ThreadLocal<>();
  public static final String SESSION_KEY_IDENTITY = "IdentityService.IDENTITY";

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   * 
   * @return
   */
  public User getSessionUser() {
    final HttpServletRequest req = servletRequests.get();

    if (req != null) {
      final User user = (User) req.getSession().getAttribute(SESSION_KEY_IDENTITY);
      if (user != null)
        return user;
    }

    return User.ANONYMOUS;
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   * 
   * @return
   */
  public String getSessionId() {
    final HttpServletRequest req = servletRequests.get();
    if (req == null)
      return null;

    return req.getSession().getId();
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   */
  public void destroySessionIdentity() {
    final HttpServletRequest req = servletRequests.get();
    if (req == null)
      return;

    req.getSession().removeAttribute(SESSION_KEY_IDENTITY);
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   * 
   * @return
   */
  public static User ensureAuthenticatedUser(IdentityService idService, String operationName) {
    final User user = idService.getSessionUser();

    if (user.isLoggedIn())
      return user;

    throw new SecurityException(String.format("Can't perform operation '%s' for unauthenticated user!", operationName));
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   */
  void setSessionUser(User user) {
    final HttpServletRequest req = servletRequests.get();
    if (req == null)
      return;

    if (user.isLoggedIn()) {
      req.getSession().setAttribute(SESSION_KEY_IDENTITY, user);
    } else {
      // TODO: Log
      System.err.println("Can't set user for non-authenticated user!");
    }
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   */
  void setServletRequestForCurrentThread(HttpServletRequest req) {
    servletRequests.set(req);
  }

  /**
   * <p>
   * <strong>NOTE:</strong> This method must only be performed on the
   * request-thread!
   * </p>
   */
  void removeServletRequestForCurrentThread() {
    servletRequests.remove();
  }
}
