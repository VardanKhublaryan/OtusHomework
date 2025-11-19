package org.otus.pages;

import com.google.inject.Inject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.otus.annotations.PathAnnotation.Path;
import org.otus.support.GuiceScoped;
import org.otus.utils.JsUtils;

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
   @FindBy(xpath = "//*[@id='__next']/div[1]/main/div/section[1]/div[3]/div[2]/div/div/div[4]/div")
   private WebElement onboardingCourses;
   @FindBy(xpath = "//main//section[2]//a")
   List<WebElement> courseLinks;

   private static final Random RANDOM = new Random();

   @Inject
   public CourseCatalogPage(GuiceScoped guiceScoped) {
      super(guiceScoped);
   }

   public CourseCatalogPage clickOnCourse(String courseName) {

      WebElement webElement = guiceScoped.getDriver()
          .findElements(By.xpath("//main//section[2]//a")).stream()
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

   public void clickInOnboardingCourses() {
      new JsUtils(guiceScoped.getDriver()).scrollIntoView(onboardingCourses);
      clickOnElement(onboardingCourses);
   }

   public String getCourseName() {
      waitUtils.waitUnTillElementsVisible(coursesNames);
      int index = RANDOM.nextInt(0, coursesNames.size());
      return getText(coursesNames.get(index));
   }

   public List<WebElement> getCoursesByDate(CourseDateType type) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));

      List<LocalDate> dates = new ArrayList<>();
      List<WebElement> validCourses = new ArrayList<>();

      for (WebElement course : coursesDates) {
         String dateText = course.getText().trim().split(" · ")[0].trim();

         if (dateText.equals("О дате старта будет объявлено позже")) {
            continue;
         }
         LocalDate date = LocalDate.parse(dateText, formatter);
         dates.add(date);
         validCourses.add(course);
      }

      if (dates.isEmpty()) {
         return List.of();
      }

      LocalDate targetDate = (type == CourseDateType.EARLIEST)
          ? dates.stream().min(LocalDate::compareTo).get()
          : dates.stream().max(LocalDate::compareTo).get();

      List<WebElement> result = new ArrayList<>();
      for (int i = 0; i < dates.size(); i++) {
         if (dates.get(i).equals(targetDate)) {
            result.add(validCourses.get(i));
         }
      }

      return result;
   }

   public Map<String, String> getAllCoursesPricesAndNames() {

      try {
         Thread.sleep(4000);
      }catch (InterruptedException ignored){
         System.err.println("Thread interrupted");
      }
      Map<String, String> courses = new HashMap<>();
      List<String> courseHrefs = this.courseLinks.stream()
          .map(link -> link.getAttribute("href"))
          .filter(Objects::nonNull)
          .filter(href -> !href.isEmpty())
          .filter(href -> href.startsWith("http"))
          .collect(Collectors.toList());

      for (String href : courseHrefs) {
         try {
            Document doc = Jsoup.connect(href).get();

            String courseTitle = doc.select("main h3").first().text();
            String coursePrice = doc.select(".sc-153sikp-11.gztHyx:matchesOwn(\\d+\\s*₽)").first().text();

            courses.put(courseTitle, coursePrice);

         } catch (Exception e) {
            System.err.println("Failed to load: " + href);
            e.printStackTrace();
         }
      }
      return courses;
   }

   public boolean isCourseModelInPage(WebElement courseDate) {
      Document doc = Jsoup.parse(Objects.requireNonNull(guiceScoped.getDriver().getPageSource()));
      String courseDateText = courseDate.getText().trim();
      String query = String.format("div:contains(%s)", courseDateText);
      Element element = doc.select(query).first();
      return element != null;
   }

   public WebElement getOpenedCourseByName(String courseName) {
      waitUtils.waitUnTillElementsVisible(courses);

      String cleanCourseName = courseName.replaceAll("\\s*\\(\\d+\\)$", "").trim();

      return courses.stream()
          .filter(course -> {
             String text = getText(course)
                 .replaceAll("\\s+", " ")
                 .replaceAll("\\s*\\(\\d+\\)$", "")
                 .trim();
             return text.equalsIgnoreCase(cleanCourseName);
          })
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
