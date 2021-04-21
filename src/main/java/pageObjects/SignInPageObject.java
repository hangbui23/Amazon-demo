package pageObjects;

import org.openqa.selenium.WebDriver;

import common.BasePage;
import pageUIs.SignInPageUI;

public class SignInPageObject extends BasePage {
	WebDriver driver;
	public SignInPageObject(WebDriver driver) {
		this.driver = driver;
	}
	
	public void inputValueToEmailTextbox(String value) {
		waitForElementVisible(driver, SignInPageUI.TXT_USERNAME);
		sendKeyToElement(driver, SignInPageUI.TXT_USERNAME, value);
	}
	
	public void inputValueToPasswordTextbox(String value) {
		waitForElementVisible(driver, SignInPageUI.TXT_PASSWORD);
		sendKeyToElement(driver, SignInPageUI.TXT_PASSWORD, value);
	}
	
	public void clickOnContinueButton() {
		waitForElementVisible(driver, SignInPageUI.BTN_CONTINUE);
		clickToElement(driver, SignInPageUI.BTN_CONTINUE);
	}
	
	public void clickOnSubmitButton() {
		waitForElementVisible(driver, SignInPageUI.BTN_SUBMIT);
		clickToElement(driver, SignInPageUI.BTN_SUBMIT);
	}

	public String getErrorMessage() {
		waitForElementVisible(driver, SignInPageUI.LBL_ERROR_MESSAGE);
		return getAttributeElement(driver, SignInPageUI.LBL_ERROR_MESSAGE, "innerText");
	}

	
}
