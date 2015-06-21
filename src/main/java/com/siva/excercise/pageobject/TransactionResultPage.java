package com.siva.excercise.pageobject;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class TransactionResultPage extends PageObject {
	LinkedHashMap<String, String> tblHeader = new LinkedHashMap<String, String>();
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.TransactionResultPage.class);
	
	public TransactionResultPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.TABLE_PREFIX + "TransactionLog", By.xpath("//table[@class='wpsc-purchase-log-transaction-results']"));
	}

	public void getTableHeaders() {
		int headerInd = 0;
		List<WebElement> tBodyRows = element(ObjectNotation.TABLE_PREFIX + "TransactionLog").get(0).findElements(By.xpath("./thead/tr/th"));
		logger.info("Total Items Purchased: " + tBodyRows.size());
		for(WebElement thObj : tBodyRows) {
			logger.info("Header " + (headerInd + 1) + " : " + thObj.getText());
			tblHeader.put("header" + headerInd, thObj.getText());
			headerInd++;
		}
	}
}
