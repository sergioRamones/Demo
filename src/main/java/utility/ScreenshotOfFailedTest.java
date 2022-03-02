package utility;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class ScreenshotOfFailedTest  implements ITestListener{

//	private WebDriver driver;
	private Base obj = new Base();
	/**
	 * @throws N/A
	 * @Description get the test suite Name that its failing then take an screenshot if it failed.
	 * @Author Sergio Ramones
	 * @Date 04-JUN-2021 
	 * @Parameter ITestResult
	 * @return N/A
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		try {

			ITestContext context = result.getTestContext();
			obj.setDriver((WebDriver) context.getAttribute("WebDriver"));

			obj.takeScreenShot();
			Reporter.log("********* Error " + result.getStatus() + " test has failed **********", true);
			Reporter.log("********* Error " + result.getTestName() + " test has failed **********", true);
			Reporter.log("********* Error " + result.getMethod() + " test has failed **********", true);
			Reporter.log("********* Error " + result.getName() + " test has failed **********", true);

			try {
				VideoRecorder_utlity.stopRecord();
			} catch (Exception e) {
				Reporter.log("********* Video can't stop becaouse has not started. **********", true);
			}

		} catch (NoSuchSessionException | IllegalArgumentException | SecurityException e) {
			Reporter.log("********* Error " + result.getTestName()
					+ " test has can not be executed as Session ID is null **********", true);
		}
	}// end OntestFailure
	
	
}
