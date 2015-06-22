package com.siva.excercise.testscript;

import com.siva.excercise.automation.model.TestScript;
import com.siva.excercise.pageobject.HomeScreen;
import com.siva.excercise.wrapper.WrappedWebDriver;

public class VerifyAndSubmitOrder extends TestScript {
	HomeScreen homeScreenPage = null;
	
	public VerifyAndSubmitOrder(WrappedWebDriver driver) {
		super(driver);
		homeScreenPage = new HomeScreen(driver);
	}

	public boolean searchOrderAndReview(String searchString) {
		boolean returnStatus = true;
		returnStatus = homeScreenPage.clickHomeMenuLink();
		if(returnStatus)
			returnStatus = homeScreenPage.searchProduct(searchString);
		return returnStatus;
	}
	
}
