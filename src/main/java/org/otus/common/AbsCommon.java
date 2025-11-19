package org.otus.common;

import com.google.inject.Inject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.otus.support.GuiceScoped;
import org.otus.utils.ActionUtils;
import org.otus.utils.JsUtils;
import org.otus.utils.WaitUtils;

public abstract class AbsCommon<T extends AbsCommon<T>> {
   protected GuiceScoped guiceScoped;
   protected ActionUtils actionUtils;
   protected WaitUtils waitUtils;

   public AbsCommon(GuiceScoped guiceScoped) {
      this.guiceScoped = guiceScoped;
      this.waitUtils = new WaitUtils(guiceScoped).getWaitReference();
      this.actionUtils = new ActionUtils(guiceScoped);
      PageFactory.initElements(guiceScoped.getDriver(), this);
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
      new JsUtils(guiceScoped.getDriver()).scrollIntoView(element);
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