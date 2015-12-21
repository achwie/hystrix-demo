package achwie.hystrixdemo.test.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import achwie.hystrixdemo.test.pages.LoginPage;

/**
 * 
 * @author 12.12.2015, Achim Wiedemann
 */
public class WhenStage extends Stage<WhenStage> {
  @ExpectedScenarioState
  private LoginPage loginPage;

  public WhenStage user_does_nothing() {
    return self();
  }

  public WhenStage user_logs_in_with_credentials(String username, String password) {
    loginPage.loginWithCredentials(username, password);
    return self();
  }
}