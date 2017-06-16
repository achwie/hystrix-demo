package achwie.hystrixdemo.util.security;

/**
 * <p>
 * Holds security contexts on a per-thread basis (using a {@link ThreadLocal}).
 * Can be used to conveniently access the security context of a request thread.
 * Usually used in conjunction with a {@link UserFilter servlet filter}.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> Since this class employs a
 * {@link ThreadLocal} it will only return return security contexts set on the
 * same thread!
 * </p>
 * 
 * @author 16.06.2017, Achim Wiedemann
 * @see UserFilter A servlet filter that sets the security context for a request
 *      thread.
 */
public class SecurityContextProvider {
  private ThreadLocal<SecurityContext> securityContexts = new ThreadLocal<>();

  /**
   * Returns the {@link SecurityContext} for the current thread.
   * 
   * @return The {@link SecurityContext} for the current thread - may be
   *         {@code null}.
   */
  public SecurityContext getCurrentContext() {
    return securityContexts.get();
  }

  void setCurrentContext(SecurityContext securityContext) {
    securityContexts.set(securityContext);
  }
}
