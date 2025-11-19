package otus.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import org.otus.support.GuiceScoped;

public class Hooks {

   @Inject
   private GuiceScoped guiceScope;

   @After
   public void afterScenario() {
      if (guiceScope.getDriver() != null) {
         guiceScope.getDriver().quit();
      }
   }

}
