package pageObjects;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import common.BasePage;
import pageUIs.HomePageUI;
import pageUIs.SearchPageUI;

public class SearchPageObject extends BasePage{
	WebDriver driver;
	
	public SearchPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public int getListItemSearch() {
		waitForElementVisible(driver, SearchPageUI.LST_ITEM);
		return getElementSize(driver, SearchPageUI.LST_ITEM);
	}
	
	public int getMaximumNumberPage() {
		waitForElementVisible(driver, SearchPageUI.MAXIMUM_PAGE);
		return Integer.parseInt(getAttributeElement(driver, SearchPageUI.MAXIMUM_PAGE,"innerText"));
	}
	
	public void clickOnNextButton() {
		waitForElementVisible(driver, SearchPageUI.BTN_NEXT);
		clickToElement(driver, SearchPageUI.BTN_NEXT);
		waitForElementPresent(driver,SearchPageUI.ICON_HIDDEN_LOADING);
	}
	
	public String clickAndVerifyLanguageCheckboxChecked(String language) {
		waitForElementVisible(driver, SearchPageUI.CHK_LANGUAGE, language);
		scrollToBottomPage(driver);
		checkTheRadioOrCheckbox(driver, SearchPageUI.CHK_LANGUAGE, language);
		return getAttributeElement(driver, SearchPageUI.CHK_LANGUAGE_CHECKED, "checked", language);
	}
	
	public void SortItemsBy(String value) {
		waitForElementVisible(driver, SearchPageUI.CBB_SORTBY_PARENT);
		selectItemInCustomDropdown(driver, SearchPageUI.CBB_SORTBY_PARENT, SearchPageUI.CBB_SORTBY_CHILD, value);
	}
	
	public boolean isDateSortDesc() {
	waitForElementPresent(driver,SearchPageUI.ICON_HIDDEN_LOADING);
	return isDataTextSortDESC(driver, SearchPageUI.LBL_PUBLISHDATE);
	}
}
