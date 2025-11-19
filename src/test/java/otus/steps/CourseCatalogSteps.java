package otus.steps;

import static org.otus.pages.CourseCatalogPage.CourseDateType.EARLIEST;
import static org.otus.pages.CourseCatalogPage.CourseDateType.LATEST;

import com.google.inject.Inject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebElement;
import org.otus.components.HeaderComponent;
import org.otus.components.TrainingComponent;
import org.otus.pages.CourseCatalogPage;
import org.otus.pages.CoursePage;
import org.otus.support.GuiceScoped;

public class CourseCatalogSteps {

   @Inject
   private GuiceScoped guiceScoped;

   @Inject
   private CourseCatalogPage courseCatalogPage;

   @Inject
   private CoursePage coursePage;

   @Inject
   private HeaderComponent headerComponent;

   @Inject
   private TrainingComponent trainingComponent;

   private String courseName;

   @When("open course catalog")
   public void openCourseCatalog() {
      courseCatalogPage.openPage();
   }

   @When("search course")
   public void searchAndClickOnCourse() {
      courseName = courseCatalogPage.getCourseName();
      courseCatalogPage.searchCourse(courseName)
          .clickOnCourse(courseName);
      guiceScoped.set(courseName, "courseName");
   }

   @Then("Course title mast be equal to course name")
   public void checkCourseTitle() {
      Assertions.assertThat(coursePage.getCourseTitle()).isEqualTo(guiceScoped.get("courseName"));
   }

   @When("get Earliest Courses")
   public void verifyEarliestCourses() {
      List<WebElement> earliestCourses = courseCatalogPage.getCoursesByDate(EARLIEST);
      guiceScoped.set(earliestCourses, "earliestCourses");
   }

   @When("get Latest Courses")
   public void verifyLatestCourses() {
      List<WebElement> latestCourses = courseCatalogPage.getCoursesByDate(LATEST);
      guiceScoped.set(latestCourses, "latestCourses");
   }

   @Then("verify Earliest And Latest Courses")
   public void verifyEarliestAndLatestCourses(){
      SoftAssertions softly = new SoftAssertions();
      List<WebElement> earliestCourses = guiceScoped.get("earliestCourses");
      List<WebElement> latestCourses = guiceScoped.get("latestCourses");
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

   @And("hover On Training Field")
   public void hoverOnTrainingField(){
      headerComponent.hoverOnTrainingField();
   }

   @Then("verify Selected Course")
   public void verifySelectedCourse(){
      String courseName = trainingComponent.clickOnRandomCourseAndReturnName();
      Assertions.assertThat(courseCatalogPage.isCourseSelected(courseCatalogPage.getOpenedCourseByName(courseName)))
          .as("Course should be present in the page: " + courseName)
          .isTrue();
   }

   @When("click on Подготовительные курсы")
   public void selectOnboardingCourses() {
      courseCatalogPage.clickInOnboardingCourses();
   }

   @Then("find cheapest and expensive courses")
   public void getCheapestAndExpensiveCourse() {
      Map<String, String> allCoursesPricesAndNames = courseCatalogPage.getAllCoursesPricesAndNames();
      Map.Entry<String, String> cheapestCourse = allCoursesPricesAndNames.entrySet().stream()
          .min(Comparator.comparing(entry -> entry.getValue().split(" ")[0]))
          .orElseThrow(() -> new IllegalArgumentException("No courses found"));

      Map.Entry<String, String> expensiveCourse = allCoursesPricesAndNames.entrySet().stream()
          .max(Comparator.comparing(entry -> entry.getValue().split(" ")[0]))
          .orElseThrow(() -> new IllegalArgumentException("No courses found"));

      System.out.println("Cheapest Course: " + cheapestCourse.getKey() + " with price " + cheapestCourse.getValue());
      System.out.println("Expensive Course: " + expensiveCourse.getKey() + " with price " + expensiveCourse.getValue());
   }
}
