package org.otus.utils;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.otus.support.GuiceScoped;

public class WaitUtils {

   private GuiceScoped guiceScoped;
   private WebDriverWait wait;

   public WaitUtils(GuiceScoped guiceScoped) {
      this.guiceScoped = guiceScoped;
   }

   public WaitUtils getWaitReference() {
      wait = new WebDriverWait(guiceScoped.getDriver(), Duration.ofSeconds(15));
      return this;
   }

   public void waitUnTillElementBeClickable(WebElement webElement) {
      wait.until(ExpectedConditions.elementToBeClickable(webElement));
   }

   public void waitUnTillElementVisible(WebElement webElement) {
      wait.until(ExpectedConditions.visibilityOf(webElement));
   }

   public void waitUnTillElementsVisible(List<WebElement> webElement) {
      wait.until(ExpectedConditions.visibilityOfAllElements(webElement));
   }


}
