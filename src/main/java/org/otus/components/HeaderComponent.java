package org.otus.components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.ComponentAnnotation.Component;

@Component("xpath://*[@id='__next']/div[1]/div[3]/div")
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
