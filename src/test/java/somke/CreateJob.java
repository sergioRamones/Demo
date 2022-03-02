package somke;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import dashboard.Dashboard;
import jobs.AddJob;
import login.Login;

public class CreateJob{
	private WebDriver driver;
	Login login;
	Dashboard dash;
	AddJob addJobs;
	
	
	@BeforeTest
	public void initBrowser() {
		login = new Login(driver);
		driver = login.chromeDriverConnection();
		login.openUrl("https://pro.housecallpro-qa.com/pro/log_in");
		
	}
	
  @Test
  public void createJob() {
	  login.login("sergio.ivan.ramones@gmail.com","Srv44245$");
	  dash = new Dashboard(driver);
	  dash.selectCreateNewJob("Dashboard - HCP");
	  addJobs = new AddJob(driver);
	  addJobs.addNewCustomer("Singapur", "Monterrey", "CA");
	  addJobs.verifyIfCustomerWasCreated();
	  addJobs.schedule(0,365);
	  addJobs.lineItems("Computers", "10", "100");
	  addJobs.saveJob();
	  
	  
  }
  @AfterTest
  public void tearDown() {
	 driver.quit();
  }

}
