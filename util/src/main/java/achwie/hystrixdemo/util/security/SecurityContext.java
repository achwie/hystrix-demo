package achwie.hystrixdemo.util.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * Holds security related information about the <em>current request</em>.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> Although this object is immutable and
 * technically safe to pass on to other threads, note the security information
 * it stores relates only to the current request thread and is short lived.
 * </p>
 * 
 * @author 16.06.2017, Achim Wiedemann
 */
public class SecurityContext {
  private final String user;
  private final Set<String> scopes = new HashSet<>();

  public SecurityContext(String user, Collection<String> scopes) {
    this.user = user;
    if (scopes != null)
      this.scopes.addAll(scopes);
  }

  /**
   * Returns the scopes that the current user has.
   * 
   * @return The scopes that the current user has - will return an empty
   *         {@link Set} if n/a.
   */
  public Set<String> getScopes() {
    return Collections.unmodifiableSet(scopes);
  }

  /**
   * Returns the user that made the current request.
   * 
   * @return The user that made the current request - may be {@code null}.
   */
  public String getUser() {
    return user;
  }
}