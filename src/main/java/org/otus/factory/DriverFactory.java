package org.otus.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.otus.exeptions.BrowserNotSupportedException;
import org.otus.factory.settings.BrowserSettings;
import org.otus.listeners.MouseListeners;

public class DriverFactory {

   private final String browserName = System.getProperty("browser", "chrome").toLowerCase();

   public WebDriver create() {
//      WebDriverManager.chromedriver().setup();
      WebDriver driver;

      try {
         driver = switch (browserName.toLowerCase()) {
            case "chrome" -> new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new BrowserSettings().chromeSettings());
            case "firefox" -> new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new BrowserSettings().firefoxSettings());
            default -> throw new BrowserNotSupportedException(browserName);
         };
      } catch (MalformedURLException e) {
         throw new RuntimeException("Invalid remote URL: " + "http://localhost:4444/wd/hub", e);
      }

      return new EventFiringDecorator<>(new MouseListeners()).decorate(driver);
   }
}
