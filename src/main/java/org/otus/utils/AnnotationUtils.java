package org.otus.utils;

import java.lang.annotation.Annotation;
import org.otus.exeptions.PathIsMissingException;

public class AnnotationUtils {

   public <T extends Annotation> T getAnnotationInstance(Class<?> targetClass,
       Class<T> annotationClass) {
      if (targetClass.isAnnotationPresent(annotationClass)) {
         return targetClass.getDeclaredAnnotation(annotationClass);
      }
      throw new PathIsMissingException(
          "Annotation " + annotationClass.getSimpleName() + " is missing on "
              + targetClass.getCanonicalName());
   }

}
