package org.otus.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.PathAnnotation.Path;

@Path("/catalog/courses")
public class CourseCatalogPage extends AbsBasePage<CourseCatalogPage> {

   @FindBy(xpath = "//*[@id='__next']/div[1]/main/div/section[2]/div[1]/div/div/input")
   private WebElement searchField;
   @FindBy(xpath = "//*[@id='__next']/div[1]/main/div/section[2]/div[1]/div/div")
   private WebElement searchBtn;
   @FindBy(xpath = "//main//section[2]//div[2]//a//h6/div")
   private List<WebElement> coursesNames;
   @FindBy(xpath = "//section[2]//a/div[2]/div/div")
   private List<WebElement> coursesDates;
   @FindBy(xpath = "//*[@id='__next']/div[1]/main/div/section[1]/div/div/div/div/div")
   private List<WebElement> courses;

   public CourseCatalogPage(WebDriver driver) {
      super(driver);
   }

   public CourseCatalogPage clickOnCourse(String courseName) {

      WebElement webElement = driver.findElements(By.xpath("//main//section[2]//a")).stream()
          .filter(WebElement::isDisplayed)
          .filter(course -> getText(course)
              .replaceAll("\\s+", " ")
              .trim()
              .toLowerCase()
              .contains(courseName.toLowerCase()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));

      scrollAndClickOnElement(webElement);
      return this;
   }

   public CourseCatalogPage searchCourse(String courseName) {
      clickOnElement(searchBtn);
      sendText(searchField, courseName);
      return this;
   }

   public String getCourseName() {
      waitUtils.waitUnTillElementsVisible(coursesNames);
      int index = new Random().nextInt(0, coursesNames.size());
      return getText(coursesNames.get(index));
   }

   public List<WebElement> getCoursesByDate(CourseDateType type) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));

      List<LocalDate> dates = coursesDates.stream()
          .map(course -> {
             String dateText = course.getText().trim().split(" · ")[0].trim();
             return LocalDate.parse(dateText, formatter);
          })
          .toList();

      if (dates.isEmpty()) {
         return List.of();
      }

      LocalDate targetDate = (type == CourseDateType.EARLIEST)
          ? dates.stream().reduce((d1, d2) -> d1.isBefore(d2) ? d1 : d2).get()
          : dates.stream().reduce((d1, d2) -> d1.isAfter(d2) ? d1 : d2).get();

      return coursesDates.stream()
          .filter(course -> {
             String dateText = course.getText().trim().split(" · ")[0].trim();
             return LocalDate.parse(dateText, formatter).equals(targetDate);
          })
          .toList();
   }

   public boolean isCourseModelInPage(WebElement courseDate) {
      Document doc = Jsoup.parse(Objects.requireNonNull(driver.getPageSource()));
      String courseDateText = courseDate.getText().trim();
      String query = String.format("div:contains(%s)", courseDateText);
      Element element = doc.select(query).first();
      return element != null;
   }

   public WebElement getOpenedCourseByName(String courseName) {
      waitUtils.waitUnTillElementsVisible(courses);
      return courses.stream()
          .filter(course -> getText(course)
              .replaceAll("\\s+", " ")
              .trim()
              .equalsIgnoreCase(courseName.replaceAll("\\s+", " ").trim()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Course not found: " + courseName));
   }

   public boolean isCourseSelected(WebElement webElement) {
      return getElementAttribute(webElement, "value").equals("true");
   }

   public enum CourseDateType {
      EARLIEST,
      LATEST
   }

}
