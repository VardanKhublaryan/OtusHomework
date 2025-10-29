package org.otus.moduls;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import org.otus.pages.CourseCatalogPage;
import org.otus.pages.CoursePage;

public class GuicePageModule extends AbstractModule {
   public WebDriver driver;

   public GuicePageModule(WebDriver driver){
      this.driver = driver;
   }

   @Provides
   public WebDriver getDriver() {
      return driver;
   }

   @Provides
   @Singleton
   public CourseCatalogPage getCourseCatalogPage() {
      return new CourseCatalogPage(driver);
   }

   @Provides
   @Singleton
   public CoursePage getCoursePage() {
      return new CoursePage(driver);
   }

}
