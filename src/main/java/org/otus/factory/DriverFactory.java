package org.otus.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.otus.exeptions.BrowserNotSupportedException;
import org.otus.factory.settings.BrowserSettings;
import org.otus.listeners.MouseListeners;

public class DriverFactory {

   private final String browserName = System.getProperty("browser", "chrome").toLowerCase();

   public WebDriver create() {
      WebDriver driver = switch (browserName){
         case "chrome" -> new ChromeDriver(new BrowserSettings().chromeSettings());
         case "firefox" -> new FirefoxDriver(new BrowserSettings().firefoxSettings());
         default -> throw new BrowserNotSupportedException(browserName);
      };
      return new EventFiringDecorator<>(new MouseListeners()).decorate(driver);
   }
}
