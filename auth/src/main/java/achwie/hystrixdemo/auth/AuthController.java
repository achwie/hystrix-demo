package achwie.hystrixdemo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RequestMapping(value = "/{sessionId}", method = RequestMethod.GET)
  public User getUser(@PathVariable String sessionId) {
    return authService.getUserForSession(sessionId);
  }

  @RequestMapping(value = "/{sessionId}", method = RequestMethod.POST)
  public ResponseEntity<User> performLogin(@PathVariable String sessionId, @RequestBody LoginRequest loginRequest) {
    final String username = loginRequest.username;
    final String password = loginRequest.password;

    final User user = authService.createSession(sessionId, username, password);

    final HttpStatus status = user.isLoggedIn() ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE;
    return new ResponseEntity<>(user, status);
  }

  @RequestMapping(value = "/{sessionId}", method = RequestMethod.DELETE)
  public ResponseEntity<String> performLogout(@PathVariable String sessionId) {
    authService.destroySession(sessionId);

    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------
  private static final class LoginRequest {
    public final String username;
    public final String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
      this.username = username;
      this.password = password;
    }
  }
}
