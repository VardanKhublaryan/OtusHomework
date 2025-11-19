package org.otus.pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.PathAnnotation.Path;
import org.otus.support.GuiceScoped;

@Path("/lessons")
public class CoursePage extends AbsBasePage<CoursePage> {

   @FindBy(css = "[class='sc-1x9oq14-0 sc-s2pydo-1 kswXpy diGrSa']")
   private WebElement courseTitle;

   @Inject
   public CoursePage(GuiceScoped guiceScoped) {
      super(guiceScoped);
   }

   public String getCourseTitle() {
      waitUtils.waitUnTillElementVisible(courseTitle);
      return getText(courseTitle);
   }

}
