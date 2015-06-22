package com.siva.excercise.pageobject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.siva.excercise.automation.model.PageObject;
import com.siva.excercise.variables.ObjectNotation;
import com.siva.excercise.wrapper.WrappedWebDriver;
import com.siva.excercise.util.Utilities;

public class TransactionResultPage extends PageObject {
	HashMap<String, String> tblHeader = new HashMap<String, String>();
	Logger logger = Logger.getLogger(com.siva.excercise.pageobject.TransactionResultPage.class);
	
	public TransactionResultPage(WrappedWebDriver driver) {
		super(driver);
		element(ObjectNotation.TABLE_PREFIX + "TransactionLog", By.xpath("//table[@class='wpsc-purchase-log-transaction-results']"));
	}

	public void getTableHeaders() {
		int headerInd = 0;
		List<WebElement> tHeaderRows = element(ObjectNotation.TABLE_PREFIX + "TransactionLog").get(0).findElements(By.xpath("./thead/tr/th"));
		for(WebElement thObj : tHeaderRows) {
			String colHeader = thObj.getText().trim();
			if(!colHeader.equals("")) {
				tblHeader.put(colHeader, Integer.toString(headerInd));
			}
			headerInd++;
		}
		logger.info("ColumnHeaderPostions: " + Utilities.returnHashMapAsString(tblHeader));
	}
	
	public boolean verifyTransactionSummary(LinkedHashMap<String, String> verifyResults) {
		boolean verifyStatus = true;
		Iterator<String> hItr = null;
		List<WebElement> tBodyRows = element(ObjectNotation.TABLE_PREFIX + "TransactionLog").get(0).findElements(By.xpath("./tbody/tr"));
		logger.info("Total Transaction Rows: " + tBodyRows.size());
		
		for(WebElement trObj : tBodyRows) {
			verifyStatus = true;
			hItr = verifyResults.keySet().iterator();
			List<WebElement> tdObjs = trObj.findElements(By.xpath("./td"));
			while(hItr.hasNext() && verifyStatus) {
				String colNameToVerify = hItr.next(); 
				System.out.println("colNameToVerify: " + colNameToVerify);
				if(tblHeader.containsKey(colNameToVerify)) {
					int colIndexToVerify = Integer.parseInt(tblHeader.get(colNameToVerify));
					System.out.println("colIndexToVerify: " + colIndexToVerify);
					System.out.println(tdObjs.size());
					if(tdObjs.size() > 0 && colIndexToVerify <= tdObjs.size()) {
						String expColValue = verifyResults.get(colNameToVerify).trim().replaceAll("\\$", "").replaceAll("", "");
						String actColValue = tdObjs.get(colIndexToVerify).getText().trim().replaceAll("\\$", "").replaceAll("", "");
						if(expColValue.equalsIgnoreCase(actColValue)) {
							logger.info("Valid Transaction Log for " + colNameToVerify + " : " + verifyResults.get(colNameToVerify) + " = " + tdObjs.get(colIndexToVerify).getText());
						}else{
							logger.info("Invalid Transaction Log for " + colNameToVerify + " : " + verifyResults.get(colNameToVerify) + " != " + tdObjs.get(colIndexToVerify).getText());
							verifyStatus = false;							
						}						
					}else {						
						logger.fatal("Invalid Column Index/Position : " + colNameToVerify);
						verifyStatus = false;
					}
				}else {					
					logger.fatal("Invalid Column Name: " + colNameToVerify);
					verifyStatus = false;
				}				
			}
		}
		return verifyStatus;
	}
}
