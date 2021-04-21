package pageObjects;
import org.openqa.selenium.WebDriver;
import common.BasePage;
import pageUIs.HomePageUI;

public class HomePageObject extends BasePage{
	WebDriver driver;
	SignInPageObject signInPage;
	SearchPageObject searchPage;
	
	public HomePageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public SignInPageObject clickOnSignInButton() {
		clickToDynamicElementByJS(driver, HomePageUI.DYNAMIC_LNK_BTN, "Sign in");
		signInPage = PageGeneratorManager.getSignInPage(driver);
		return signInPage;
	}
	
	public String getHeaderText() {
		waitForElementVisible(driver, HomePageUI.LNK_ACCOUNT_LIST);
		return getAttributeElement(driver, HomePageUI.LNK_ACCOUNT_LIST, "textContent");
	}
	
	public void SearchBasedOnDepartment(String value) {
		selectItemInDropDown(driver, HomePageUI.CBB_DEPARMENT_SEARCH,value);
	}
	
	public void inputValueToSearchTextbox(String value) {
		waitForElementVisible(driver,HomePageUI.TXT_SEARCH);
		sendKeyToElement(driver, HomePageUI.TXT_SEARCH, value);
	}
	
	public SearchPageObject clickOnSearchButton() {
		waitForElementVisible(driver,HomePageUI.BTN_SEARCH);
		clickToElement(driver, HomePageUI.BTN_SEARCH);
		searchPage = PageGeneratorManager.getSearchPage(driver);
		return searchPage;
	}
}
