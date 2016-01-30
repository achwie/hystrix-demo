package achwie.hystrixdemo.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import achwie.hystrixdemo.cart.CartService;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Controller
public class AuthController {
  private final AuthService authService;
  private final SessionService sessionService;
  private final CartService cartService;

  @Autowired
  public AuthController(AuthService authService, SessionService sessionService, CartService cartService) {
    this.authService = authService;
    this.sessionService = sessionService;
    this.cartService = cartService;
  }

  @RequestMapping(value = "login", method = RequestMethod.GET)
  public String showLogin(Model model, HttpServletRequest req) {
    final String referrer = req.getHeader("Referer");

    model.addAttribute("referrer", referrer);

    return "login";
  }

  @RequestMapping(value = "login", method = RequestMethod.POST)
  public String performLogin(@ModelAttribute LoginCredentials loginCreds, HttpServletRequest req) {
    final String username = loginCreds.getUsername();
    final String password = loginCreds.getPassword();
    final String referrer = loginCreds.getReferrer();

    final User user = authService.login(username, password);

    if (user.isLoggedIn()) {
      sessionService.setSessionUser(user);
      return StringUtils.hasText(referrer) ? ("redirect:" + referrer) : "redirect:catalog";
    } else {
      // TODO: User feedback
      return "redirect:login";
    }
  }

  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public String performLogout(HttpServletRequest req) {
    final String sessionId = sessionService.getSessionId();

    cartService.clearCart(sessionId);
    sessionService.removeSessionUser();
    authService.logout();

    return "redirect:catalog";
  }
}
