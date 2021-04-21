package common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	public void openUrl(WebDriver driver, String url) {
		driver.get(url);
	}
	
	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}
	
	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public void waitForAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}
	
	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}
	
	public void getTextAlert(WebDriver driver) {
		driver.switchTo().alert().getText();
	}

	public void senKeyToAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}
	
	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}
	
	public By ByXpath(String locator) {
		return By.xpath(locator);
	}
	
	public By ByXpath(String locator,String...values) {
		return By.xpath(getDynamicLocator(locator,values));
	}
	
	public WebElement findWebElement(WebDriver driver, String locator) {
		return driver.findElement(ByXpath(locator));
	}
	
	public WebElement findWebElement(WebDriver driver, String locator,String...values) {
		return driver.findElement(ByXpath(getDynamicLocator(locator,values)));
	}

	public String getDynamicLocator(String locator, String... values) {
		locator =  String.format(locator, (Object[])values);
		return locator;
				
	}
	
	public List<WebElement> findListWebElement(WebDriver driver, String locator) {
		return driver.findElements(ByXpath(locator));
	}
	
	public List<WebElement> findListWebElement(WebDriver driver, String locator, String...values) {
		System.out.println(ByXpath(getDynamicLocator(locator,values)));
		return driver.findElements(ByXpath(getDynamicLocator(locator,values)));
	}
	
	public void clickToElement(WebDriver driver, String locator) {
		if(driver.toString().contains("Edge")) {
			sleepInMiliSecond(500);
		}
		findWebElement(driver,locator).click();
	}
	
	public void clickToElement(WebDriver driver, String locator, String...values) {
		if(driver.toString().contains("Edge")) {
			sleepInMiliSecond(500);
		}
		findWebElement(driver,getDynamicLocator(locator,values)).click();
	}
	
	public void clickToElement(WebElement element) {
		element.click();
	}
	
	public void sendKeyToElement(WebDriver driver, String locator, String value) {
		findWebElement(driver,locator).clear();
		findWebElement(driver,locator).sendKeys(value);
	}
	
	public void sendKeyToElement(WebDriver driver, String locator, String value, String... values) {
		findWebElement(driver,getDynamicLocator(locator,values)).sendKeys(value);
	}
	
	public void selectItemInDropDown(WebDriver driver, String locator, String value) {
		Select element = new Select(findWebElement(driver,locator));
		element.selectByVisibleText(value);
	}
	
	public void selectItemInDropDown(WebDriver driver, String locator, String value, String...values) {
		Select element = new Select(findWebElement(driver,getDynamicLocator(locator,values)));
		element.selectByVisibleText(value);
	}
	
	public String getSelectedItemInDropDown(WebDriver driver, String locator) {
		Select element = new Select(findWebElement(driver,locator));
		return element.getFirstSelectedOption().getText();
	}
	
	public boolean isDropDownMultiple(WebDriver driver, String locator) {
		Select element = new Select(findWebElement(driver,locator));
		return element.isMultiple();
	}
	
	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator, String expectedItem) {
		clickToElement(driver,parentLocator);
		sleepInSecond(1);
		WebDriverWait explicitWait = new WebDriverWait(driver, 20);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(ByXpath(childItemLocator)));
		List<WebElement> allItems = findListWebElement(driver, childItemLocator);
		for (WebElement item : allItems) {
			if (item.getText().equals(expectedItem)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				clickToElement(item);
				sleepInSecond(1);
				break;
			}
		}
	}
	
	public void sleepInSecond(long timeout) {
		try {
			Thread.sleep(timeout * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getAttributeElement(WebDriver driver, String locator, String attribute) {
		return findWebElement(driver, locator).getAttribute(attribute);
	}
	
	public String getAttributeElement(WebDriver driver, String locator, String attribute, String...values) {
		return findWebElement(driver, getDynamicLocator(locator,values)).getAttribute(attribute);
	}
	
	public String getTextElement(WebDriver driver, String locator) {
		return findWebElement(driver, locator).getText();
	}
	
	public String getTextElement(WebDriver driver, String locator, String...values) {
		return findWebElement(driver, getDynamicLocator(locator,values)).getText();
	}
	
	public int getElementSize(WebDriver driver, String locator) {
		return findListWebElement(driver, locator).size();
	}
	
	public void checkTheRadioOrCheckbox(WebDriver driver, String locator) {
		WebElement element = findWebElement(driver, locator);
		if(!element.isSelected()) {
			element.click();
		};
	}
	
	public void checkTheRadioOrCheckbox(WebDriver driver, String locator, String... values) {
		WebElement element = findWebElement(driver,getDynamicLocator(locator,values));
		if(!element.isSelected()) {
			element.click();
		};
	}
	
	public void uncheckTheCheckbox(WebDriver driver, String locator) {
		WebElement element = findWebElement(driver, locator);
		if(element.isSelected()) {
			element.click();
		};
	}
	
	public boolean isElementDisplay(WebDriver driver, String locator) {
		return findWebElement(driver, locator).isDisplayed();
	}
	
	public boolean isElementDisplay(WebDriver driver, String locator, String...values) {
		return findWebElement(driver, getDynamicLocator(locator,values)).isDisplayed();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator, String...values) {
		return findWebElement(driver, getDynamicLocator(locator,values)).isSelected();
	}
	
	public boolean isElementSelected(WebDriver driver, WebElement ele) {
		return ele.isSelected();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator, String...values) {
		return findWebElement(driver, getDynamicLocator(locator,values)).isEnabled();
	}
	
	public void switchToIFrame(WebDriver driver, String locator) {
		driver.switchTo().frame(findWebElement(driver, locator));
	}
	
	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public void doubleClickElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.doubleClick().perform();
	}
	
	public void hoverMouseToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.moveToElement(findWebElement(driver, locator)).perform();
	}
	
	public void rightClickToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.contextClick(findWebElement(driver, locator)).perform();
	}
	
	public void dragDrop(WebDriver driver, String locatorSource, String locatorTarget) {
		Actions action = new Actions(driver);
		action.dragAndDrop(findWebElement(driver, locatorSource), findWebElement(driver, locatorTarget)).perform();
	}
	
	public void sendKeyBoardToElement(WebDriver driver, String locator, Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(findWebElement(driver, locator), key).perform();
	}
	
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}
	
	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void clickToDynamicElementByJS(WebDriver driver, String locator, String...values) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, getDynamicLocator(locator,values));
		jsExecutor.executeScript("arguments[0].click();", element);
	}
	
	public void scrollToElement(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public void scrollToElement(WebDriver driver, String locator, String...values) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, getDynamicLocator(locator,values));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public String getElementValidationMessage(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = findWebElement(driver, locator);
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", element);
	}
	
	public boolean isImageLoaded(WebDriver driver, String locator) {
		WebElement element = findWebElement(driver, locator);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete&&typeof arguments[0].naturalWidth!=\"undefined\"&& arguments[0].naturalWidth>0", element);
		if(status) {
			return true;
		}else {
			return false;
		}
		
	} 
	
	public void waitForElementVisible(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(ByXpath(locator)));
	}
	
	public void waitForElementPresent(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(ByXpath(locator)));
	}
	
	public void waitForElementVisible(WebDriver driver, String locator, String...values) {
	WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
	explicitWait.until(ExpectedConditions.visibilityOfElementLocated(ByXpath(getDynamicLocator(locator,values))));
	}
	
	public void waitForListElementVisible(WebDriver driver, String locator, String...values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ByXpath(getDynamicLocator(locator,values))));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(ByXpath(locator)));
	}
	
	public void waitForElementClickable(WebDriver driver, String locator, String...values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(ByXpath(getDynamicLocator(locator,values))));
	}
	
	public void waitForElementInvisible(WebDriver driver, String locator, String...values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalContants.SHORT_TIMEOUT);
		overRideImplicitWait(driver,GlobalContants.SHORT_TIMEOUT);
		System.out.println("Start Date " + new Date().toString());
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(ByXpath(getDynamicLocator(locator,values))));
		System.out.println("End Date " + new Date().toString());
		overRideImplicitWait(driver,GlobalContants.LONG_TIMEOUT);
	}
	
	public void sleepInSecond(int second) {
		try {
			Thread.sleep(second*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sleepInMiliSecond(int milisecond) {
		try {
			Thread.sleep(milisecond);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isDataTextSortASC(WebDriver driver,String locator) {
		List<String> arrList = new ArrayList<String>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(webElement.getText());
		}
		
		//Create another list to sort
		List<String> isSorted = new ArrayList<String>();
		for(String text : arrList) {
			isSorted.add(text);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		return arrList.equals(isSorted);
	}
	
	public boolean isDataFloatSortASC(WebDriver driver,String locator) {
		List<Float> arrList = new ArrayList<Float>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(Float.parseFloat(webElement.getText().replace("$", "").trim()));
		}
		
		System.out.println("======Du lieu Sort ASC trên UI======");
		for(Float name: arrList) {
			System.out.println(name);
		}
		
		//Create another list to sort
		List<Float> isSorted = new ArrayList<Float>();
		for(Float number : arrList) {
			isSorted.add(number);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		
		System.out.println("======Du lieu Sort ASC sau sort======");
		for(Float name: isSorted) {
			System.out.println(name);
		}
		
		return arrList.equals(isSorted);
	}
	
	public boolean isDataDateSortASC(WebDriver driver,String locator) {
		List<Date> arrList = new ArrayList<Date>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(convertStringToDate(webElement.getText()));
		}
		
		//Create another list to sort
		List<Date> isSorted = new ArrayList<Date>();
		for(Date date : arrList) {
			isSorted.add(date);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		return arrList.equals(isSorted);
	}
	
	public Date convertStringToDate(String date) {
		date = date.replace(",", "");
		Date formatDate=null;
		try {
			formatDate = new SimpleDateFormat("MMM dd yyyy").parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate;
	}
	public boolean isDataTextSortDESC(WebDriver driver,String locator) {
		List<String> arrList = new ArrayList<String>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(webElement.getText());
		}
		
		//Create another list to sort
		List<String> isSorted = new ArrayList<String>();
		for(String text : arrList) {
			isSorted.add(text);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		
		//Sort DESC
		Collections.reverse(isSorted);
		
		return arrList.equals(isSorted);
	}
	
	public boolean isDataFloatSortDESC(WebDriver driver,String locator) {
		List<Float> arrList = new ArrayList<Float>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(Float.parseFloat(webElement.getText().replace("$", "").trim()));
		}
		
		System.out.println("======Du lieu Sort DESC trên UI======");
		for(Float price: arrList) {
			System.out.println(price);
		}
		
		//Create another list to sort
		List<Float> isSorted = new ArrayList<Float>();
		for(Float number : arrList) {
			isSorted.add(number);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		
		//Sort DESC
		Collections.reverse(isSorted);
		
		
		System.out.println("======Du lieu Sort DESC sau sort======");
		for(Float name: isSorted) {
			System.out.println(name);
		}
		
		return arrList.equals(isSorted);
	}
	
	public boolean isDataDateSortDESC(WebDriver driver,String locator) {
		List<Date> arrList = new ArrayList<Date>();
		List<WebElement> lst_ele = findListWebElement(driver,locator);
		//Add text to list
		for (WebElement webElement : lst_ele) {
			arrList.add(convertStringToDate(webElement.getText()));
		}
		
		//Create another list to sort
		List<Date> isSorted = new ArrayList<Date>();
		for(Date date : arrList) {
			isSorted.add(date);
		}
		
		//Sort ASC
		Collections.sort(isSorted);
		
		//Sort DESC
		Collections.reverse(isSorted);
		
		return arrList.equals(isSorted);
	}
	
	
	public boolean isElementUndisplay(WebDriver driver, String locator) {
		List<WebElement> elements = findListWebElement(driver, locator);
		if(elements.size()==0) {
			return true;
		}
		else if(elements.size()>0 && !elements.get(0).isDisplayed()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isElementUndisplay(WebDriver driver, String locator, String...values) {
		overRideImplicitWait(driver, GlobalContants.SHORT_TIMEOUT);
		System.out.println("Start Date " + new Date().toString());
		List<WebElement> elements = findListWebElement(driver, locator,values);
		overRideImplicitWait(driver, GlobalContants.LONG_TIMEOUT);
		if(elements.size()==0) {
			System.out.println("Element not in DOM and UI");
			System.out.println("End Date " + new Date().toString());
			return true;
		}
		else if(elements.size()>0 && !elements.get(0).isDisplayed()) {
			System.out.println("Element in DOM and not in UI");
			System.out.println("End Date " + new Date().toString());
			return true;
		}
		else {
			System.out.println("End Date " + new Date().toString());
			return false;
		}
	}
	
	public void overRideImplicitWait(WebDriver driver, long timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}
}
