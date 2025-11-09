package org.otus.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.ComponentAnnotation.Component;

@Component("css:span[title='Обучение']")
public class HeaderComponent extends AbsBaseComponent{

   @FindBy(css = "span[title='Обучение']")
   private WebElement trainingField;

   public HeaderComponent(WebDriver driver) {
      super(driver);
   }

   public void hoverOnTrainingField() {
      verifyComponentLoaded();
      actionUtils.hoverOnElement(trainingField);
   }

}
