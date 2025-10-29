package org.otus.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.otus.utils.ActionUtils;
import org.otus.utils.JsUtils;
import org.otus.utils.WaitUtils;

public abstract class AbsCommon<T extends AbsCommon<T>> {
   protected WebDriver driver;
   protected ActionUtils actionUtils;
   protected WaitUtils waitUtils;

   public AbsCommon(WebDriver driver) {
      this.driver = driver;
      this.waitUtils = new WaitUtils(driver).getWaitReference();
      this.actionUtils = new ActionUtils(driver);
      PageFactory.initElements(driver, this);
   }

   @SuppressWarnings("unchecked")
   protected T clickOnElement(WebElement element) {
      waitUtils.waitUnTillElementVisible(element);
      waitUtils.waitUnTillElementBeClickable(element);
      element.click();
      return (T) this;
   }

   @SuppressWarnings("unchecked")
   protected T scrollAndClickOnElement(WebElement element) {
      waitUtils.waitUnTillElementVisible(element);
      waitUtils.waitUnTillElementBeClickable(element);
      new JsUtils(driver).scrollIntoView(element);
      element.click();
      return (T) this;
   }

   protected String getText(WebElement element) {
      waitUtils.waitUnTillElementVisible(element);
      return element.getText();
   }

   protected void sendText(WebElement element, String text) {
      waitUtils.waitUnTillElementVisible(element);
      element.sendKeys(text);
   }

   protected String getElementAttribute(WebElement element, String text) {
      waitUtils.waitUnTillElementVisible(element);
      return element.getDomAttribute(text);
   }
}