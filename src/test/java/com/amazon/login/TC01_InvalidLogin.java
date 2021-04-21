package com.amazon.login;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import common.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.SignInPageObject;


public class TC01_InvalidLogin extends BaseTest {
	WebDriver driver;
	HomePageObject homePage;
	SignInPageObject signInPage;
	String invalidEmail,invalidPassword,validEmail;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		invalidEmail="123.com";
		invalidPassword="1234567";
		validEmail = "bhang230491@gmail.com";
		
		log.info("Open Amazon page");
		driver = getBrowserName(browserName);
		homePage = PageGeneratorManager.getHomePage(driver);
		
		log.info("Click on Sign In button");
		signInPage = homePage.clickOnSignInButton();
		}

	@Test
	public void TC01_InvalidEmail() {
		log.info("Input value to Email textbox");
		signInPage.inputValueToEmailTextbox(invalidEmail);
		
		log.info("Click on Continue button");
		signInPage.clickOnContinueButton();
		
		log.info("Verify the message display");
		Assert.assertEquals("We cannot find an account with that email address", signInPage.getErrorMessage());
		
	}
	
	@Test
	public void TC02_InvalidPassword() {
		log.info("Input value to Email textbox");
		signInPage.inputValueToEmailTextbox(validEmail);
		
		log.info("Click on Continue button");
		signInPage.clickOnContinueButton();
		
		log.info("Input value to Password textbox");
		signInPage.inputValueToPasswordTextbox(invalidPassword);
		
		log.info("Click on Submit button");
		signInPage.clickOnSubmitButton();
		
		log.info("Verify the message display");
		Assert.assertEquals("Your password is incorrect", signInPage.getErrorMessage());
	}
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}
}
