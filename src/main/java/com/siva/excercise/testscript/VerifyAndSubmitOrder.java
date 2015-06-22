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

	public void searchOrderAndReview(String searchString) {
		homeScreenPage.clickHomeMenuLink();
		homeScreenPage.searchProduct(searchString);
	}
	
}
