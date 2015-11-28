package achwie.hystrixdemo.frontend.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author 21.11.2015, Achim Wiedemann
 */
@Controller
public class AuthController {
  private final AuthService authService;
  private final IdentityService idService;

  @Autowired
  public AuthController(AuthService authService, IdentityService idService) {
    this.authService = authService;
    this.idService = idService;
  }

  @RequestMapping(value = "login", method = RequestMethod.GET)
  public String showLogin(Model model, HttpServletRequest req) {
    final String referrer = req.getHeader("Referer");

    model.addAttribute("referrer", referrer);

    return "login";
  }

  @RequestMapping(value = "login", method = RequestMethod.POST)
  public String performLogin(@ModelAttribute ViewLoginCredentials loginCreds, HttpServletRequest req) {
    final String username = loginCreds.getUsername();
    final String password = loginCreds.getPassword();
    final String referrer = loginCreds.getReferrer();

    final User user = authService.findUser(username, password);

    if (user.isLoggedIn()) {
      idService.setSessionUser(user);
      return (referrer != null) ? ("redirect:" + referrer) : "redirect:catalog";
    } else {
      return "redirect:login";
    }
  }

  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public String performLogout(HttpServletRequest req) {
    idService.destroySessionIdentity();

    return "redirect:catalog";
  }
}
