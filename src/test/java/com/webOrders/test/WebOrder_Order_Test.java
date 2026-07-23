package com.webOrders.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.webOrders.common.WebOrders_BasePage;
import com.webOrders.common.WebOrders_BaseSetup;
import com.webOrders.home.WebOrders_HomePage;
import com.webOrders.home.WebOrders_OrdersPage;
import com.webOrders.home.WebOrders_SignInPage;
import com.webOrders.home.WebOrders_ViewAllOrdersPage;
import com.webOrders.utils.WebOrders_TestData;

public class WebOrder_Order_Test extends WebOrders_BaseSetup {
	WebOrders_SignInPage signIn;
	WebOrders_HomePage homePage;
	WebOrders_BasePage basePage;
	WebOrders_OrdersPage orderPage;
	WebOrders_ViewAllOrdersPage allOrderPage;
	private WebDriver driver;


	@BeforeClass
	public void setUp() {
		driver = getDriver();
		basePage = new WebOrders_BasePage(driver);
	}

	@Test(description = "Validate reset button", dataProvider = "Reset Form", dataProviderClass = WebOrders_TestData.class, priority = 1, enabled = false)
	public void verifyCheckAll(String product, String qty, String custName, String street, String city, String state,
			String zip, String card, String cardNr, String expiry, String expectedResult) throws Exception {
		test = extent.createTest("Test Case 1", "Verify if all fields are cleared after clicking reset button");
		signIn = basePage.GoToHomePageAndSignIn("Tester", "test");
		signIn.verifySignInPageText();
		homePage = new WebOrders_HomePage(driver);
		orderPage = homePage.clickOnOrdersTab();
		orderPage.resetForm(product, qty, custName, street, city, state, zip, card, cardNr, expiry, expectedResult);
		basePage.logout();

	}
	
	@Test(description = "create and verify new Orders", dataProvider = "OrdersExcelData", dataProviderClass = WebOrders_TestData.class, priority = 2, enabled = true)
	public void createAndVerifyOrder(String product, String qty, String custName, String street, String city, String state,
			String zip, String card, String cardNr, String expiry, String expectedResult) throws Exception {
		test = extent.createTest("Test Case 2", "Create and Verify if order is created successfully");
		signIn = basePage.GoToHomePageAndSignIn("Tester", "test");
		signIn.verifySignInPageText();
		homePage = new WebOrders_HomePage(driver);
		orderPage = homePage.clickOnOrdersTab();
		orderPage.createOrder(product, qty, custName, street, city, state, zip, card, cardNr, expiry, expectedResult);
		
		if(expectedResult.equalsIgnoreCase("success")) 
		{
		allOrderPage = homePage.clickOnAllOrdersTab();
		allOrderPage.verifyUpdatedOrder(product, qty, custName, street, city, state, zip, card, cardNr, expiry);
		}
		basePage.logout();

		
	}
	
	
	/*
	 * @Test(description = "Validate uncheck all button", priority = 2, enabled =
	 * true) public void verifyUnCheckAll() throws Exception { test =
	 * extent.createTest("Test Case 2",
	 * "Verify if all orders are unselected if UnCheck All button is clicked");
	 * 
	 * viewAllOrderPage.clickUncheckAll(); Assert.assertEquals(true,
	 * viewAllOrderPage.verifyAllOrdersUnselected(), "Records are still selected.");
	 * }
	 * 
	 * @Test(description = "Validate user can delete an order", priority = 3,
	 * enabled = true) public void verifyDeleteOrder() throws Exception { test =
	 * extent.createTest("Test Case 3", "Verify if user can delete an order");
	 * 
	 * viewAllOrderPage.deleteOrder(name, product); Assert.assertEquals(true,
	 * viewAllOrderPage.verifyDeletedRecord(name), "Records are still Exists."); }
	 * 
	 * @Test(description = "Validate user can edit an order", dataProvider =
	 * "Update Form", dataProviderClass = WebOrders_TestData.class, priority = 4,
	 * enabled = true)
	 * 
	 * public void verifyEditOrder(String product, String qty, String custName,
	 * String street, String city, String state, String zip, String card, String
	 * cardNr, String expiry, String expectedResult) throws Exception {
	 * 
	 * editOrdersPage = viewAllOrderPage.clickEdit();
	 * 
	 * editOrdersPage.updateFieldSuccess(product, qty, custName, street, city,
	 * state, zip, card, cardNr, expiry); viewAllOrderPage = new
	 * WebOrders_ViewAllOrdersPage(driver);
	 * 
	 * viewAllOrderPage.verifyUpdatedOrder(product, qty, custName, street, city,
	 * state, zip, card, cardNr, expiry);
	 * 
	 * }
	 * 
	 * @Test(description =
	 * "Validate error is displayed while editing an order with invalid Data",
	 * dataProvider = "Update Order Form Error", dataProviderClass =
	 * WebOrders_TestData.class, priority = 5, enabled = true)
	 * 
	 * public void verifyError(String product, String qty, String custName, String
	 * street, String city, String state, String zip, String card, String cardNr,
	 * String expiry, String expectedResult) throws Exception {
	 * 
	 * editOrdersPage = viewAllOrderPage.clickEdit();
	 * 
	 * editOrdersPage.validateError(product, qty, custName, street, city, state,
	 * zip, card, cardNr, expiry, expectedResult); homePage = new
	 * WebOrders_HomePage(driver); homePage.clickOnAllOrdersTab(); viewAllOrderPage
	 * = new WebOrders_ViewAllOrdersPage(driver);
	 * 
	 * }
	 */
}
