package org.otus.components;

import com.google.inject.Inject;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.ComponentAnnotation.Component;
import org.otus.support.GuiceScoped;


@Component("xpath://nav/div[3]/div")
public class TrainingComponent extends AbsBaseComponent{

   @Inject
   public TrainingComponent(GuiceScoped guiceScoped){
      super(guiceScoped);
   }

   @FindBy(xpath = "//nav//div[3]//div//div//div[1]//div//div//a")
   public List<WebElement> courcesList;

   private static final Random RANDOM = new Random();

   public String clickOnRandomCourseAndReturnName() {
      int index = RANDOM.nextInt(courcesList.size()-1);
      WebElement course = courcesList.get(index);
      String name = course.getText();
      course.click();
      return name;
   }

}
