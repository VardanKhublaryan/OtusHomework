import static org.otus.pages.CourseCatalogPage.CourseDateType.EARLIEST;
import static org.otus.pages.CourseCatalogPage.CourseDateType.LATEST;

import com.google.inject.Inject;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.otus.components.HeaderComponent;
import org.otus.components.TrainingComponent;
import org.otus.extentions.UIExtension;
import org.otus.pages.CourseCatalogPage;
import org.otus.pages.CoursePage;

@ExtendWith(UIExtension.class)
public class CourseCatalogTest {

   @Inject
   private CourseCatalogPage courseCatalogPage;

   @Inject
   private CoursePage coursePage;

   @Inject
   private HeaderComponent headerComponent;

   @Inject
   private TrainingComponent trainingComponent;

   @Test
   public void searchAndClickOnCourse() {
      courseCatalogPage.openPage();
      String courseName = courseCatalogPage.getCourseName();
      courseCatalogPage.searchCourse(courseName)
          .clickOnCourse(courseName);
      Assertions.assertThat(coursePage.getCourseTitle()).isEqualTo(courseName);
   }

   @Test
   public void verifyEarliestAndLatestCourses() {
      SoftAssertions softly = new SoftAssertions();
      courseCatalogPage.openPage();
      List<WebElement> earliestCourses = courseCatalogPage.getCoursesByDate(EARLIEST);
      List<WebElement> latestCourses = courseCatalogPage.getCoursesByDate(LATEST);
      earliestCourses.forEach(course ->
          softly.assertThat(courseCatalogPage.isCourseModelInPage(course))
              .as("Earliest course should be present:")
              .isTrue());
      latestCourses.forEach(course ->
          softly.assertThat(courseCatalogPage.isCourseModelInPage(course))
              .as("Latest course should be present:")
              .isTrue());
      softly.assertAll();
   }

   @Test
   public void verifySelectedCourse() {
      courseCatalogPage.openPage();
      headerComponent.hoverOnTrainingField();
      String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
      Assertions.assertThat(courseCatalogPage.isCourseSelected(courseCatalogPage.getOpenedCourseByName(courseName)))
          .as("Course should be present in the page: " + courseName)
          .isTrue();
   }

}
