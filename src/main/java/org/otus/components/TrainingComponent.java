package org.otus.components;

import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.ComponentAnnotation.Component;


@Component("xpath://nav/div[3]/div")
public class TrainingComponent extends AbsBaseComponent{

   public TrainingComponent(WebDriver driver){
      super(driver);
   }

   @FindBy(xpath = "//nav//div[3]//div//div//div[1]//div//div//a")
   public List<WebElement> courcesList;

   private static final Random RANDOM = new Random();

   public String clickOnRandomCourseAndReturnName() {
      int index = RANDOM.nextInt(courcesList.size());
      WebElement course = courcesList.get(index);
      String name = course.getText();
      course.click();
      return name;
   }

}
