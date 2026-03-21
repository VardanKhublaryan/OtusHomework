package org.otus.factory;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.otus.exeptions.BrowserNotSupportedException;
import org.otus.factory.settings.BrowserSettings;
import org.otus.listeners.MouseListeners;

public class DriverFactory {

   private final String browser =
       System.getProperty("browser", "chrome");

   private final String runMode =
       System.getProperty("run.mode", "local");

   private final String remoteUrl =
       System.getProperty("remote.url", "http://188.130.251.59:4444/wd/hub");

   public WebDriver create() {
      try {
         WebDriver driver = runMode.equalsIgnoreCase("remote")
             ? createRemoteDriver()
             : createLocalDriver();

         return new EventFiringDecorator<>(new MouseListeners())
             .decorate(driver);

      } catch (MalformedURLException e) {
         throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
      }
   }

   private WebDriver createRemoteDriver() throws MalformedURLException {
      return switch (browser.toLowerCase()) {
         case "chrome" ->
             new RemoteWebDriver(new URL(remoteUrl),
                 new BrowserSettings().chromeSettings());
         case "firefox" ->
             new RemoteWebDriver(new URL(remoteUrl),
                 new BrowserSettings().firefoxSettings());
         default ->
             throw new BrowserNotSupportedException(browser);
      };
   }

   private WebDriver createLocalDriver() {
      return switch (browser.toLowerCase()) {
         case "chrome" ->
             new ChromeDriver(new BrowserSettings().chromeSettings());
         case "firefox" ->
             new FirefoxDriver(new BrowserSettings().firefoxSettings());
         default ->
             throw new BrowserNotSupportedException(browser);
      };
   }
}
