package com.siva.excercise.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class FancyNotificationDialog extends PageObject {
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.FancyNotificationDialog.class);
	
	public FancyNotificationDialog(WrappedWebDriver driver) {
		super(driver);
		//display: block; position: fixed; left: 50%; top: 50%; margin-left: -185px; margin-top: -75px;
		element(ObjectNotation.SPAN_PREFIX + "FancyNotification", By.xpath("//div[@id='fancy_notification' and contains(@style, 'display: block')]/descendant::div[@id='fancy_notification_content' and contains(@style, 'display: block')]/span"));
	}

	public boolean goToCheckOutPage() {
		boolean methodStatus = true;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			WebElement dialogObj = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='fancy_notification' and contains(@style, 'display: block')]/descendant::div[@id='fancy_notification_content' and contains(@style, 'display: block')]/span")));
			logger.info("Message in dialog box: " + dialogObj.getText());
			WebElement goToCheckOut = dialogObj.findElement(By.xpath("./following-sibling::a[@class='go_to_checkout']"));
			goToCheckOut.click();
			logger.info("Clicked \"Go to Checkout\" button");
		}catch(Exception e) {
			methodStatus = false;
			e.printStackTrace();
		}
//		List<WebElement> dialogObj = element(ObjectNotation.SPAN_PREFIX + "FancyNotification");
//		if(dialogObj.size() == 0) {
//			methodStatus = false;
//		}else {
//			logger.info(dialogObj.get(0).getAttribute("text"));
//		}
		return methodStatus;
	}
	
}
