package com.webOrders.utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.webOrders.common.WebOrders_BasePage;

public class Listeners implements ITestListener {

	String report = "TestReport" + "_"+ System.currentTimeMillis() + ".html";
    private ExtentReports extent = ExtentReportBase.CreateExtentReport(report, "Edge");
//	private ExtentReports extent = ExtentReportBase.CreateExtentReport("TestReport.html", "Firefox");
	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	public WebDriver driver;
	WebOrders_BasePage basePage;

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	@Override
	public void onStart(ITestContext context) {
		// Initialization hooks if needed
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Initialize the report logging object for the current test
		ExtentTest localTest = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(localTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest currentTest = extentTest.get();
		if (currentTest != null) {
			currentTest.log(Status.PASS, "Test Successful");

			// Capture screenshot on test success
			try {
				driver = WebOrders_BasePage.getDriver();
				basePage = new WebOrders_BasePage(driver);
				String screenshotPath = ExtentReportBase.getScreenhotSuccess(driver, result.getMethod().getMethodName());
				currentTest.addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				currentTest.fail("Test passed, but unable to capture screenshot: " + e.getMessage());
			}
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest currentTest = extentTest.get();
		if (currentTest != null) {
			currentTest.fail(result.getThrowable());

			// Capture screenshot on test failure
			try {
				driver = WebOrders_BasePage.getDriver();
				basePage = new WebOrders_BasePage(driver);
				String screenshotPath = ExtentReportBase.getScreenhot(driver, result.getMethod().getMethodName());
				currentTest.addScreenCaptureFromPath(screenshotPath);
			} catch (Exception e) {
				currentTest.fail("Test failed, but unable to capture screenshot: " + e.getMessage());
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest currentTest = extentTest.get();
		
		// If the test skipped before onTestStart could run, create a temporary log block
		if (currentTest == null) {
			currentTest = extent.createTest(result.getMethod().getMethodName());
			extentTest.set(currentTest);
		}
		
		currentTest.log(Status.SKIP, "Test Skipped");
		if (result.getThrowable() != null) {
			currentTest.skip(result.getThrowable()); // Log the underlying exception causing the skip
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Optional implementation block
	}
}
