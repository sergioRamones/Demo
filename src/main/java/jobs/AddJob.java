package jobs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utility.Base;

public class AddJob extends Base{

	By text_title = By.tagName("h5");
	By btn_newCustomer = By.xpath("//span[contains(text(),'New customer')]/parent::*");
	By iframeNewJob = By.xpath("//iframe[@name='__privateStripeMetricsController3990']");
	By btn_saveJob = By.xpath("//span[contains(text(),'Save job')]/parent::*");
	By btn_edit = By.xpath("//span[@title='Edit']");
	//Add new customer
	By text_titleAddNewCustomer = By.xpath("//h2[contains(text(),'Add')]");
	By txt_fristName = By.xpath("//input[@id='customer-dialog-first-name']");
	By txt_lastName = By.xpath("//input[@name='last_name']");
	By txt_mobilePhone = By.xpath("//input[@name='mobile_number']");
	By txt_company = By.xpath("//input[@name='company']");
	By txt_displayName = By.xpath("//input[@name='display_name']");
	By txt_homePhone = By.xpath("//input[@name='home_number']");
	By txt_workNumber = By.xpath("//input[@name='work_number']");
	By txt_jobTitle= By.xpath("//input[@name='job_title']");
	By txt_email = By.xpath("//input[@name='email']");
	By txt_street = By.xpath("//input[@id='service_addresses[0].streetAutoComplete']");
	By txt_unit = By.xpath("//input[@name='service_addresses[0].street_line_2']");
	By txt_city = By.xpath("//input[contains(@name,'city')]");
	By btn_state = By.xpath("//div[contains(@id,'state')]");
	By list_state = By.xpath("//ul[contains(@role,'listbox')]/li");
	By txt_zip = By.xpath("//input[contains(@name,'zip')]");
	By txt_addressNotes = By.xpath("//textarea[contains(@name,'.notes')]");
	By txt_customerNotes = By.xpath("//textarea[@name='notes']");
	By txt_customerTags = By.xpath("//div[contains(@class,' MuiGrid-grid-sm-8-696')]//input[@class='MuiInputBase-input-879 MuiFilledInput-input-862']");
	By txt_thisCustomerBillsTo = By.xpath("//input[contains(@placeholder,'Search customers')]");
	By check_receiveNotifications = By.xpath("//span[contains(text(),'Receive notifications')]/parent::*//input[contains(@class,'jss969')]");
	By btn_createCustomer = By.xpath("//span[contains(text(),'create')]/parent::*");
	//Schedule
	By text_scheduleTitle = By.xpath("//h6/span[contains(text(),'Schedule')]");
	By txt_dateFrom = By.xpath("//*[@data-test-id='schedule-date-from']//input[@id='input-date']");
	By txt_dateTo = By.xpath("//*[@data-test-id='schedule-date-to']//input[@id='input-date']");
	//line Items
	By text_lineItemstitle = By.xpath("//h2[contains(text(),'Line items')]");
	By txt_itemName = By.xpath("//input[@id='item-name']");
	By txt_qty = By.xpath("//input[@id='qty']");
	By txt_unitPrice = By.xpath("//input[@id='unit-price']");
	//private notes
	By btn_privateNotes = By.xpath("//*[contains(text(),'Private notes')]/parent::*/parent::*/parent::*/parent::*/parent::*/parent::*/parent::*");
	By txt_privateNotes = By.xpath("//div[@class='jss14']//div[@class='MuiCardContent-root jss325']/div/div/textarea[1]");
	//schedule
	By text_jobTitle = By.xpath("//span[contains(text(),'Job')]");
	
	public AddJob(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @Description Add new customer
	 * @author sramones
	 * @Date 01/03/2022
	 * @param String (18)  
	 * @return N/A
	 * @exception 
	 * **/
	public void addNewCustomer(String stret, String city, String state) {
		verifyElementIsPresent(text_title);
		validateExpectedText(getText(text_title),"New job");
		highlighElement(btn_newCustomer);
		click(btn_newCustomer);
		verifyElementIsPresent(text_titleAddNewCustomer);
		type(getRandomName(false),txt_fristName);
		type(getRandomLastName(false),txt_lastName);
		type("8448805550",txt_mobilePhone);
		type("Test automation" ,txt_company);
		type(getRandomName(false),txt_displayName);
		type("8448805550",txt_homePhone);
		type("Automation" ,txt_jobTitle);
		type(getRandomName(true)+"@mailinator.com",txt_email);
		type("8448805550",txt_workNumber);
		takeScreenShot();
		type(stret,txt_street);
		type(getRandomNumber(2),txt_unit);
		type(city,txt_city);
		click(btn_state);
		verifyElementByValueAndClick(list_state,state);
		type(getRandomNumber(5),txt_zip);
		type("Automation Notes address" ,txt_addressNotes);
		takeScreenShot();
		type("Automation Notes customer" ,txt_customerNotes);
		verifyElementIsPresent(txt_customerTags);
		type("Automation tag customer" ,txt_customerTags);
		type("Automation Bill" ,txt_thisCustomerBillsTo);
		takeScreenShot();
		click(btn_createCustomer);
		verifyElementIsVisible(text_scheduleTitle);

	}
	
	/**
	 * @Description Schedule a job
	 * @author sramones
	 * @Date 01/03/2022
	 * @param int, int  MMDDYYY
	 * @return N/A
	 * @exception 
	 * **/
	public void schedule(int current, int to) {
		verifyElementIsPresent(text_scheduleTitle);
		type(getDate(0) ,txt_dateFrom);
		type(getDate(365) ,txt_dateTo);
		takeScreenShot();

	}
	
	/**
	 * @Description Add item 
	 * @author sramones
	 * @Date 01/03/2022
	 * @param String, String, String
	 * @return N/A
	 * @exception 
	 * **/
	public void lineItems(String itemName, String qty, String unitPrice) {
		verifyElementIsPresent(text_lineItemstitle);
		type(itemName,txt_itemName);
		type(qty,txt_qty);
		type(unitPrice,txt_unitPrice);
		takeScreenShot();
	}
	
	/**
	 * @Description save Job 
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return N/A
	 * @exception 
	 * **/
	public void saveJob() {
		verifyElementIsPresent(btn_saveJob);
		click(btn_saveJob);
		verifyElementIsPresent(text_jobTitle);
		takeScreenShot();
		
	}
	
	public void verifyIfCustomerWasCreated() {
		verifyElementIsPresent(btn_edit);
		if(isDisplayed(btn_edit)==false) {
			takeScreenShot();
			Assert.fail("Customer was not created properly");
		}else {
			reporter("Customer was created successfully");
		}
	}
}
