package org.otus.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.PathAnnotation.Path;

@Path("/lessons")
public class CoursePage extends AbsBasePage<CoursePage>{

   @FindBy(css = "[class='sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa']")
   private WebElement courseTitle;

   public CoursePage(WebDriver driver) {
      super(driver);
   }

   public String getCourseTitle() {
      waitUtils.waitUnTillElementVisible(courseTitle);
      return getText(courseTitle);
   }

}
