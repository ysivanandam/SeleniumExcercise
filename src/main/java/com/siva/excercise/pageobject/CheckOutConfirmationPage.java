package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class CheckOutConfirmationPage extends PageObject {
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.CheckOutConfirmationPage.class);
	
	public CheckOutConfirmationPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.TEXT_PREFIX + "Login", By.id("log"));
		element(ObjectNotation.TEXT_PREFIX + "Password", By.id("pwd"));
		element(ObjectNotation.BUTTON_PREFIX + "Login", By.xpath("//input[@type='submit' and @id='login']"));
	}
	
	public boolean enterUserName(String userName) {
		boolean methodStatus = true;
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("log")));
		}catch(Exception e) {
			methodStatus = false;
			logger.fatal(e.getMessage());
		}
		if(methodStatus) {
			List<WebElement> txtObj = element(ObjectNotation.TEXT_PREFIX + "Login");
			if(txtObj.size() == 0) {
				methodStatus = false;
				logger.error("Username text box not found");
			}else if(txtObj.size() > 1) {
				methodStatus = false;
				logger.error("Found multiple instances of username text box");
			}else {
				txtObj.get(0).clear();
				txtObj.get(0).sendKeys(userName);
				logger.info("Entered \"" + userName + "\"");
			}
		}
		return methodStatus;
	}
	
	public boolean enterUserPassword(String userPassword) {
		boolean methodStatus = true;
		List<WebElement> txtObj = element(ObjectNotation.TEXT_PREFIX + "Password");
		if(txtObj.size() == 0) {
			methodStatus = false;
			logger.error("Password text box not found");
		}else if(txtObj.size() > 1) {
			methodStatus = false;
			logger.error("Found multiple instances of password text box");
		}else {
			txtObj.get(0).clear();
			txtObj.get(0).sendKeys(userPassword);
			logger.info("Entered Password");
		}
		return methodStatus;
	}
	
	public boolean clickLoginButton() {
		boolean methodStatus = true;
		List<WebElement> buttonObj = element(ObjectNotation.BUTTON_PREFIX + "Login");
		if(buttonObj.size() == 0) {
			methodStatus = false;
			logger.error("Login button not found");
		}else if(buttonObj.size() > 1) {
			methodStatus = false;
			logger.error("Found multiple instances of Login button");
		}else {
			buttonObj.get(0).click();
			logger.info("Clicked Login button");
		}
		return methodStatus;
	}
	

}
