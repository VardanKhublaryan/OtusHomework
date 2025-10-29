package org.otus.moduls;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.otus.components.HeaderComponent;
import org.otus.components.TrainingComponent;

public class GuiceComponentModule extends AbstractModule {
   private WebDriver driver;

   public GuiceComponentModule(WebDriver driver) {
      this.driver = driver;
   }

   @Provides
   @Singleton
   public HeaderComponent getHeaderComponent() {
      return new HeaderComponent(driver);
   }

   @Provides
   @Singleton
   public TrainingComponent getTrainingComponent() {
      return new TrainingComponent(driver);
   }
}
