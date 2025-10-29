package org.otus.extentions;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.otus.factory.DriverFactory;
import org.otus.moduls.GuiceComponentModule;
import org.otus.moduls.GuicePageModule;

public class UIExtension implements BeforeEachCallback, AfterEachCallback {

   private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

   @Override
   public void beforeEach(ExtensionContext context) {
      WebDriver webDriver = new DriverFactory().create();
      DRIVER.set(webDriver);
      Injector injector = Guice.createInjector(new GuicePageModule(webDriver), new GuiceComponentModule(webDriver));
      injector.injectMembers(context.getTestInstance().get());
   }

   @Override
   public void afterEach(ExtensionContext context) {
      WebDriver webDriver = DRIVER.get();
      if (webDriver != null) {
         webDriver.quit();
         DRIVER.remove();
      }
   }

}
