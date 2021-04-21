package pageUIs;

public class SearchPageUI {
	public static final String LST_ITEM = "//div[contains(@data-component-type,'s-search-result')]";
	public static final String MAXIMUM_PAGE = "(//li[@aria-disabled='true'])[last()]";
	public static final String BTN_NEXT = "//*[text()='Next']";
	public static final String ICON_HIDDEN_LOADING = "//*[contains(@class,'row aok-hidden')]";
	public static final String CHK_LANGUAGE = "//*[text()='%s']/preceding-sibling::div";
	public static final String CHK_LANGUAGE_CHECKED = "//*[text()='%s']/preceding-sibling::div//input";
	public static final String CBB_SORTBY_PARENT = "//span[@aria-label='Sort by:']";
	public static final String CBB_SORTBY_CHILD = "//div[contains(@class,'vertical-scroll')]//a[text()='%s']";
	public static final String LBL_PUBLISHDATE= "//div[@class='a-row']/child::span[contains(@class,'text-normal')]";
}
