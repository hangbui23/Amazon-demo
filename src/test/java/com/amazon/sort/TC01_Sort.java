package com.amazon.sort;
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

public class TC01_Sort extends BaseTest {
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
	public void TC01_SortByPublishDate() {
		log.info("Open Books department");
		homePage.SearchBasedOnDepartment("Books");
		
		log.info("Input apple value to search text box");
		homePage.inputValueToSearchTextbox("apple");
		
		log.info("Click on Search button");
		searchPage = homePage.clickOnSearchButton();
		
		log.info("Click on English Language and verify English checkbox checked");
		Assert.assertEquals(searchPage.clickAndVerifyLanguageCheckboxChecked("English"),"true");
		
		log.info("Sort by Publication Date");
		searchPage.SortItemsBy("Publication Date");
		
		log.info("Verify Publication Date sort");
		verifyTrue(searchPage.isDateSortDesc());
	}
	
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}
}
