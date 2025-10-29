package org.otus.exeptions;

import static com.google.inject.internal.Errors.format;

public class PathIsMissingException extends RuntimeException{

   public PathIsMissingException(String className) {
      super(format("Path on class %s is absent", className));
   }

}
