package achwie.hystrixdemo.auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestOperations;

import achwie.hystrixdemo.CommandGroup;
import achwie.hystrixdemo.HystrixRestCommand;

/**
 * 
 * @author 30.01.2016, Achim Wiedemann
 */
class LoginCommand extends HystrixRestCommand<User> {
  private static final String DIGEST_ALGO = "SHA-1";
  private final String url;
  private final String username;
  private final String password;
  private MessageDigest sha1Digest;

  public LoginCommand(RestOperations restOps, String authServiceBaseUrl, String sessionId, String username, String password) {
    super(CommandGroup.AUTH_SERVICE, restOps);
    this.url = authServiceBaseUrl + "/" + sessionId;
    this.username = username;
    this.password = password;

    try {
      sha1Digest = MessageDigest.getInstance(DIGEST_ALGO);
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Could not create message digest for hashing login cache key! Caching login data will be disabled.", e);
    }
  }

  @Override
  protected User run() throws Exception {
    try {
      return restOps.postForObject(url, new LoginRequest(username, password), User.class);
    } catch (HttpStatusCodeException e) {
      // Returns 406 for wrong credentials - otherwise we have an error
      if (e.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
        return getFallback(); // Don't trip circuit breaker
      } else {
        LOG.error("Unexpected response while trying to authenticate user {} at {} (status: {}, response body: '{}')", username, url, e.getStatusCode(),
            e.getResponseBodyAsString());
        throw e;
      }
    }
  }

  @Override
  protected User getFallback() {
    return User.ANONYMOUS;
  }

  @Override
  protected String getCacheKey() {
    // If we can't hash, don't cache!
    if (sha1Digest == null)
      return null;

    final String key = url + "_" + username + "_" + password;
    final byte[] keyHashedBytes = sha1Digest.digest(key.getBytes());
    return new String(keyHashedBytes);
  }

  // ---------------------------------------------------------------------------
  private static final class LoginRequest {
    @SuppressWarnings("unused") // Field gets serialized
    public final String username;
    @SuppressWarnings("unused") // Field gets serialized
    public final String password;

    public LoginRequest(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }
}
