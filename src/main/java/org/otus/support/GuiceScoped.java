package org.otus.support;

import io.cucumber.guice.ScenarioScoped;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.otus.factory.DriverFactory;

@ScenarioScoped
public class GuiceScoped {

   private WebDriver driver = new DriverFactory().create();
   private Map<String, Object> storeObj = new HashMap<>();

   public WebDriver getDriver() {
      return driver;
   }

   public <T> void set(T obj, String name) {
      storeObj.put(name, obj);
   }

   public <T> T get(String name) {
      return (T) storeObj.get(name);
   }
}
