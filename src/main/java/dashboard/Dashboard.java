package dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.Base;

public class Dashboard extends Base{
	

	By btn_new = By.xpath("//button[@data-testid='add-new-button']");
	By btn_job = By.xpath("//li[@data-testid='add-new-job']");
	
	public Dashboard(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @Description Keyword click on new and select job
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return N/A
	 * @exception 
	 * **/
	public void selectCreateNewJob(String title) {
		verifyElementIsVisible(btn_new);
		validateExpectedText(getTitle(),title);
		highlighElement(btn_new);
		click(btn_new);
		highlighElement(btn_job);
		click(btn_job);
	}

}
