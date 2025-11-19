package org.otus.components;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.ComponentAnnotation.Component;
import org.otus.support.GuiceScoped;

@Component("css:span[title='Обучение']")
public class HeaderComponent extends AbsBaseComponent{

   @FindBy(css = "span[title='Обучение']")
   private WebElement trainingField;

   @Inject
   public HeaderComponent(GuiceScoped guiceScoped) {
      super(guiceScoped);
   }

   public void hoverOnTrainingField() {
      verifyComponentLoaded();
      actionUtils.hoverOnElement(trainingField);
   }

}
