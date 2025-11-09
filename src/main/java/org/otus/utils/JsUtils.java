package org.otus.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsUtils {

   private WebDriver driver;

   public JsUtils(WebDriver driver) {
      this.driver = driver;
   }

   public void scrollIntoView(WebElement element) {
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
          element);
   }

   public void closePopUp() {
      JavascriptExecutor js = (JavascriptExecutor) driver;
      WebElement acceptButton = (WebElement) js.executeScript(
          "return Array.from(document.querySelectorAll('button, a, div')).find(e => "
              + "e.innerText && e.innerText.trim().toLowerCase().includes('ok'));"
      );

      if (acceptButton != null) {
         js.executeScript("arguments[0].click();", acceptButton);
      } else {
         js.executeScript(
             "document.querySelectorAll('[id*=\"cookie\"],[class*=\"cookie\"],[id*=\"consent\"],[class*=\"consent\"]').forEach(e => e.remove());"
         );
      }
   }

}
