package org.otus.components;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.otus.annotations.ComponentAnnotation.Component;
import org.otus.common.AbsCommon;
import org.otus.utils.AnnotationUtils;


public abstract class AbsBaseComponent extends AbsCommon<AbsBaseComponent> {

   public AbsBaseComponent(WebDriver driver) {
      super(driver);
   }

   public void verifyComponentLoaded() {
      waitUtils.waitUnTillElementVisible(driver.findElement(getComponentSelector()));
   }

   public By getComponentSelector() {
      String[] selector = new AnnotationUtils().getAnnotationInstance(this.getClass(), Component.class).value().split(":");
      return switch (selector[0].trim()) {
         case "css" -> By.cssSelector(selector[1].trim());
         case "xpath" -> By.xpath(selector[1].trim());
         default -> null;
      };
   }
}