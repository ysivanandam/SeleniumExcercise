package com.siva.excercise.pageobject;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.util.Utilities;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class OrderConfirmationPage extends PageObject {
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.OrderConfirmationPage.class);
	double shippingCost, itemCost, itemTax, totalPrice;
	public OrderConfirmationPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.SPAN_PREFIX + "ReviewShipping", By.xpath("//div[@class='review group']/descendant::tr[@class='total_price total_shipping']/td[@class='wpsc_totals'][2]/span/span"));
		element(ObjectNotation.SPAN_PREFIX + "ReviewItemCost", By.xpath("//div[@class='review group']/descendant::tr[@class='total_price total_item']/td[@class='wpsc_totals'][2]/span/span"));
		element(ObjectNotation.SPAN_PREFIX + "ReviewTax", By.xpath("//div[@class='review group']/descendant::tr[@class='total_price total_tax']/td[@class='wpsc_totals'][2]/span/span"));
		element(ObjectNotation.SPAN_PREFIX + "TotalPrice", By.xpath("//div[@class='review group']/descendant::tr[@class='total_price']/td[@class='wpsc_totals'][2]/span/span"));
		element(ObjectNotation.BUTTON_PREFIX + "OrderSubmit", By.xpath("//input[@type='submit' and @value='Purchase']"));
	}

	public boolean waitForFormToLoad() {
		boolean returnStatus = true;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @placeholder='First Name']")));
		}catch(Exception e) {
			returnStatus = false;
			logger.fatal(e.getMessage());
		}
		return returnStatus;
	}
	
	public boolean waitForBillingFormToLoad() {
		boolean returnStatus = true;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='text' and @title='billingfirstname' and @value='first name']")));
		}catch(Exception e) {
			returnStatus = false;
			logger.fatal(e.getMessage());
		}
		return returnStatus;
	}
	
	public boolean displayChargeDetails(List<WebElement> listObj, String costType, boolean scrollToObject) {
		if(listObj.size() == 0) {
			logger.info("No " + costType + " found");
			return false;
		}else {
			if(scrollToObject) {
				Utilities.scrollToObject(driver.getDriver(), listObj.get(0));
			}
			logger.info(costType + " = " + listObj.get(0).getText());
			return true;
		}
		
	}
	
	public boolean reviewOrderSummary() {
		boolean methodStatus = true;
		methodStatus = displayChargeDetails(element(ObjectNotation.SPAN_PREFIX + "ReviewShipping"), "Shipping Cost", false) 
				&& displayChargeDetails(element(ObjectNotation.SPAN_PREFIX + "ReviewItemCost"), "Item Cost", false) 
				&& displayChargeDetails(element(ObjectNotation.SPAN_PREFIX + "ReviewTax"), "Tax", false) 
				&& displayChargeDetails(element(ObjectNotation.SPAN_PREFIX + "TotalPrice"), "Total Price", true) ;		
		return methodStatus;
	}
	
	public void submitOrder() {
		List<WebElement> purchaseButton = element(ObjectNotation.BUTTON_PREFIX + "OrderSubmit");
		if(purchaseButton.size() == 0) {
			logger.error("Purchase button not found");
		}else if(purchaseButton.size() > 0) {
			purchaseButton.get(0).click();
			logger.info("Clicked Purchase Button");
		}
	}
	
}
 