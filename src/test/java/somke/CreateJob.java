package somke;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;

import dashboard.Dashboard;
import jobs.AddJob;
import login.Login;
import utility.Base;
import utility.PropFileHelper;
import utility.VideoRecorder_utlity;

@Listeners({utility.ScreenshotOfFailedTest.class})
public class CreateJob {

	private WebDriver driver;
	private String osName = System.getProperty("os.name");
	private String path = System.getProperty("user.dir");
	Base base = new Base();
	Login login;
	Dashboard dash;
	AddJob addJobs;
	String url;
	String user;
	String password;
	String jsonPath;
	JsonNode nodeTree;
	PropFileHelper obj = new PropFileHelper();
	String dashTitle, street, city, state, itemName, qty, unitPrice, startDays, endDays, privateNotes;

	@BeforeTest
	public void initDataInit(ITestContext context) {
		setupData();
		// init webDriver
		login = new Login(driver);
		driver = login.openBrowser();
		context.setAttribute("WebDriver", driver);
		login.openUrl(url);

	}

	@Test
	public void createJob() throws Exception {
		VideoRecorder_utlity.startRecord("Create a Job");
		login.login(user, password);
		dash = new Dashboard(driver);
		dash.selectCreateNewJob(dashTitle);
		addJobs = new AddJob(driver);
		addJobs.addNewCustomer(street, city, state);
		addJobs.verifyIfCustomerWasCreated();
		addJobs.schedule(Integer.parseInt(startDays), Integer.parseInt(endDays));
		addJobs.lineItems(itemName, qty, unitPrice);
		addJobs.addPrivateNotes(privateNotes);
		addJobs.saveJob();
		VideoRecorder_utlity.stopRecord();

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
	
	public void setupData() {

		if (osName.contains("Windows")) {
			jsonPath = path + "\\datafiles\\data.json";

		} else {
			jsonPath = path + "/datafiles/data.json";

		}

		nodeTree = base.readJsonFileByNode(jsonPath, "houseCallPro");
		dashTitle = nodeTree.path("dashTitle").asText();
		street = nodeTree.path("street").asText();
		city = nodeTree.path("city").asText();
		state = nodeTree.path("state").asText();
		itemName = nodeTree.path("itemName").asText();
		qty = nodeTree.path("qty").asText();
		unitPrice = nodeTree.path("unitPrice").asText();
		startDays = nodeTree.path("startDays").asText();
		endDays = nodeTree.path("endDays").asText();
		privateNotes = nodeTree.path("privateNotes").asText();
		
		obj.getSystemProp();
		user = System.getProperty("USERNAME");
		password = System.getProperty("PASSWORD");
		url = System.getProperty("URL");
	}

}
