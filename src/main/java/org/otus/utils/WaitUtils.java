package org.otus.utils;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

   private WebDriver driver;
   private WebDriverWait wait;

   public WaitUtils(WebDriver driver) {
      this.driver = driver;
   }

   public WaitUtils getWaitReference() {
      wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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
