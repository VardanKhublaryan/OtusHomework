package org.otus.factory.settings;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserSettings {

   public ChromeOptions chromeSettings() {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("start-maximized");
      Map<String, Object> selenoidOptions = new HashMap<>();
      selenoidOptions.put("enableVNC", true);
      options.setCapability("selenoid:options", selenoidOptions);
      return options;
   }

   public FirefoxOptions firefoxSettings() {
      FirefoxOptions options = new FirefoxOptions();
      options.addArguments("--width=1920");
      options.addArguments("--height=1080");
      Map<String, Object> selenoidOptions = new HashMap<>();
      selenoidOptions.put("enableVNC", true);
      options.setCapability("selenoid:options", selenoidOptions);
      return options;
   }
}
