package otus.steps;

import com.google.inject.Inject;
import io.cucumber.java.en.Given;
import org.otus.support.GuiceScoped;

public class CommonSteps {

   @Inject
   private GuiceScoped guiceScoped;

   private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

   @Given("open browser")
   public void openBrowser() {
      guiceScoped.getDriver().get(BASE_URL);
   }

}
