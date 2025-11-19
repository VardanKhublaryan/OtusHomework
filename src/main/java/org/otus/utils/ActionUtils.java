package org.otus.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.otus.support.GuiceScoped;

public class ActionUtils {
   private final Actions actions;

   public ActionUtils(GuiceScoped guiceScoped) {
      this.actions = new Actions(guiceScoped.getDriver());}

   public void hoverOnElement(WebElement element) {
      actions.moveToElement(element).build().perform();}
}