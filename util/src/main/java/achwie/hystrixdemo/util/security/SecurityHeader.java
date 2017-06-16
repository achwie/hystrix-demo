package achwie.hystrixdemo.util.security;

/**
 * Contains security related HTTP headers.
 * 
 * @author 16.06.2017, Achim Wiedemann
 */
public enum SecurityHeader {
  /**
   * The header to transport the user for the request.
   */
  USER("X-User"),
  /**
   * The header to transport the scopes of the user for the request.
   */
  SCOPES("X-Scopes");
  private final String headerName;

  SecurityHeader(String headerName) {
    this.headerName = headerName;
  }

  /**
   * Returns the name of the header to be used in an HTTP request.
   * 
   * @return The name of the header.
   */
  public String getHeaderName() {
    return headerName;
  }
}
