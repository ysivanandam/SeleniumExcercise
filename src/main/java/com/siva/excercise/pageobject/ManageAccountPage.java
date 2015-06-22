package com.siva.excercise.pageobject;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class ManageAccountPage extends PageObject {
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.ManageAccountPage.class);
	
	public ManageAccountPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.TEXT_PREFIX + "FirstName", By.xpath("//input[@type='text' and @data-wpsc-meta-key='billingfirstname']"));
		element(ObjectNotation.SELECT_PREFIX + "BillingCountry", By.xpath("//select[@data-wpsc-meta-key='billingcountry']"));
		element(ObjectNotation.BUTTON_PREFIX + "Submit", By.xpath("//input[@type='submit' and @value='Save Profile']"));
	}
	
	public String getSelectedCountry() {
		Select selectObj = new Select(element(ObjectNotation.SELECT_PREFIX + "BillingCountry").get(0));
		return selectObj.getFirstSelectedOption().getText();
	}
	
	public boolean setFirstName(String firstName) {
		WebElement txtFirstName = element(ObjectNotation.TEXT_PREFIX + "FirstName").get(0);
		txtFirstName.clear();
		txtFirstName.sendKeys("FN");
		return true;
	}
	
	public String getFirstName() {
		return element(ObjectNotation.TEXT_PREFIX + "FirstName").get(0).getAttribute("value");
	}
	
	public boolean selectCountry(String countryName) {
		boolean returnStatus = true;
		Select selectObj = new Select(element(ObjectNotation.SELECT_PREFIX + "BillingCountry").get(0));
		try {
			selectObj.selectByVisibleText(countryName);
			logger.info("Selected " + countryName);
		}catch(Exception e) {
			logger.error("Error while selecting " + countryName);
		}
		return returnStatus;
	}
	
	public void submitForm() {
		element(ObjectNotation.BUTTON_PREFIX + "Submit").get(0).click();
		logger.info("Form Submitted");
	}

}
