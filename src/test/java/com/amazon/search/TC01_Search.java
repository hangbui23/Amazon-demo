package com.amazon.search;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import common.BaseTest;
import pageObjects.HomePageObject;
import pageObjects.PageGeneratorManager;
import pageObjects.SearchPageObject;

public class TC01_Search extends BaseTest {
	WebDriver driver;
	HomePageObject homePage;
	SearchPageObject searchPage;
	
	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		log.info("Open Amazon page");
		driver = getBrowserName(browserName);
		homePage = PageGeneratorManager.getHomePage(driver);
		}

	@Test
	public void TC01_SearchBasedOnDepartment() {
		log.info("Open Books department");
		homePage.SearchBasedOnDepartment("Books");
		
		log.info("Input apple value to search text box");
		homePage.inputValueToSearchTextbox("apple");
		
		log.info("Click on Search button");
		searchPage = homePage.clickOnSearchButton();
		
		log.info("Click on English Language and verify Englist checkbox checked");
		Assert.assertEquals(searchPage.clickAndVerifyLanguageCheckboxChecked("English"),"true");
		
		log.info("The maximum page is " + searchPage.getMaximumNumberPage());
		int maximumNumberPage = searchPage.getMaximumNumberPage();
		
		int page=1;
		while(page<=maximumNumberPage) {
		log.info("The Result displays exactly 16 items on page "  + page);
		Assert.assertEquals(16, searchPage.getListItemSearch());
		
		if(page!=maximumNumberPage) {
		log.info("Click on Next button");
		searchPage.clickOnNextButton();
		}
		
		page++;
		}
	}
	
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}
}
