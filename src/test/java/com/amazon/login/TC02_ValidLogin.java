package com.amazon.login;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import common.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.SignInPageObject;

public class TC02_ValidLogin extends BaseTest {
	WebDriver driver;
	HomePageObject homePage;
	SignInPageObject signInPage;
	String validEmail,validPassword;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		validEmail = "bhang230491@gmail.com";
		validPassword = "123456";
		
		log.info("Open Amazon page");
		driver = getBrowserName(browserName);
		homePage = PageGeneratorManager.getHomePage(driver);
		
		log.info("Click on Sign In button");
		signInPage = homePage.clickOnSignInButton();
		}

	@Test
	public void TC01_ValidEmailPassword() {
		log.info("Input value to Email textbox");
		signInPage.inputValueToEmailTextbox(validEmail);
		
		log.info("Click on Continue button");
		signInPage.clickOnContinueButton();
		
		log.info("Input value to Password textbox");
		signInPage.inputValueToPasswordTextbox(validPassword);
		
		log.info("Click on Submit button");
		signInPage.clickOnSubmitButton();
		
		log.info("Verify log in succuessfully.");
		homePage.getHeaderText().contains("Hello, HangBui");
	}
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}
}