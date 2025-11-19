package org.otus.pages;

import org.otus.annotations.PathAnnotation.Path;
import org.otus.common.AbsCommon;
import org.otus.support.GuiceScoped;
import org.otus.utils.AnnotationUtils;
import org.otus.utils.JsUtils;

public class AbsBasePage<T extends AbsCommon<T>> extends AbsCommon<T> {

   private final static String BASE_URL = System.getProperty("baseUrl", "https://otus.ru");

   public AbsBasePage(GuiceScoped driver) {
      super(driver);
   }

   @SuppressWarnings("unchecked")
   public T openPage() {
      guiceScoped.getDriver().get(BASE_URL + getPath());
      new JsUtils(guiceScoped.getDriver()).closePopUp();
      return (T) this;
   }

   private String getPath() {
      return new AnnotationUtils().getAnnotationInstance(this.getClass(), Path.class).value();
   }

}
