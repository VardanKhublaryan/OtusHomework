package org.otus.exeptions;

import static com.google.inject.internal.Errors.format;

public class BrowserNotSupportedException extends RuntimeException {

   public BrowserNotSupportedException(String browser) {
      super(format("Browser not supported", "%s", browser));
   }

}
