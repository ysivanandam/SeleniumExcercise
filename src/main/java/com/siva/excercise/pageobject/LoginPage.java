package com.siva.excercise.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class LoginPage extends PageObject {

	public LoginPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.TEXT_PREFIX + "UserName", By.id("user_login"));
		element(ObjectNotation.TEXT_PREFIX + "Password", By.id("user_pass"));
		element(ObjectNotation.BUTTON_PREFIX + "Login", By.id("wp-submit"));
	}
	
	public boolean doLogin(String userName, String userPassword) {
		boolean returnStatus = true;
		WebElement usrName = element(ObjectNotation.TEXT_PREFIX + "UserName").get(0);
		usrName.clear();
		usrName.sendKeys(userName);
		WebElement usrPwd = element(ObjectNotation.TEXT_PREFIX + "Password").get(0);
		usrPwd.clear();
		usrPwd.sendKeys(userPassword);
		element(ObjectNotation.BUTTON_PREFIX + "Login").get(0).click();
		return returnStatus;
	}

}
