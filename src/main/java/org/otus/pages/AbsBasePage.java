package org.otus.pages;

import org.openqa.selenium.WebDriver;
import org.otus.annotations.PathAnnotation.Path;
import org.otus.common.AbsCommon;
import org.otus.utils.AnnotationUtils;
import org.otus.utils.JsUtils;

public class AbsBasePage<T extends AbsCommon<T>> extends AbsCommon<T> {

   private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

   public AbsBasePage(WebDriver driver) {
      super(driver);
   }

   @SuppressWarnings("unchecked")
   public T openPage() {
      driver.get(BASE_URL + getPath());
      new JsUtils(driver).closePopUp();
      return (T) this;
   }

   private String getPath() {
      return new AnnotationUtils().getAnnotationInstance(this.getClass(), Path.class).value();
   }

}
