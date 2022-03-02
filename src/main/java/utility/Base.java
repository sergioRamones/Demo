package utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;




public class Base {
	
	private WebDriver driver;
	private String path = System.getProperty("user.dir");
	private String osName =  System.getProperty("os.name");
	private String chromeDriver;
	private String geckoDriver;
	private String msedgeDriver;
	
	public Base(WebDriver driver) {
		this.driver=driver;
		 PropFileHelper obj = new PropFileHelper();
		 obj.getSystemProp();
				
	}
	
	
	public Base() {
		// TODO Auto-generated constructor stub
	}


	/**
	 * @Description start webdriver according to browser 
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return String
	 * @exception 
	 * **/
	public WebDriver openBrowser() {
		String browser = System.getProperty("BROWSER");
		
		switch(browser) {
		case"chrome": 
			driver = chromeDriverConnection();
			break;
		case"firefox":
			driver = firefoxDriverConnection();
			break;
		case"edge":
			driver = edgeDriverConnection();
			break;
			default:
				reporter("Driver can't be initialited, ensure that you have selected the proper browser: " + browser);
				
		}
		return driver;
	}
	
	/**
	 * @Description get Operating system name 
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return String
	 * @exception 
	 * **/
	public String getOSname() {
		if (osName.contains("Mac")) {
			osName = "Mac";
		} else if (osName.contains("Windows")) {
			osName = "Windows";
		} else if (osName.contains("Linux")) {
			osName = "Linux";
		}
		return osName;
	}
	
	
	/**
	 * @Description set the path for driver according to Operating system
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @exception 
	 * **/
	public void setDriverPaths() {
		osName = getOSname();		
		switch (osName) {
		case "Mac":
		case "Linux":
			chromeDriver = path+"/chromedriver/chromedriver";
			geckoDriver = path+"/geckodriver/geckodriver";
			msedgeDriver = path+"/msedgedriver/msedgedriver";
			break;
		case "Windows":
		case "Windows 10":
			chromeDriver = path+"\\chromedriver\\chromedriver.exe";
			geckoDriver = path+"\\geckodriver\\geckodriver.exe";
			msedgeDriver = path+"\\msedgedriver\\msedgedriver.exe";
			break;

		}
	}
	
	/**
	 * @Description start google Chrome session
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return WebDriver
	 * @exception
	 **/
	public WebDriver chromeDriverConnection() {
		setDriverPaths();
		System.setProperty("webdriver.chrome.driver", chromeDriver);
		ChromeOptions option = new ChromeOptions();
//		option.addArguments("--incognito");
		option.addArguments("--start-maximized");
		driver = new ChromeDriver(option);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(600));
		return driver;
	}

	/**
	 * @Description start Firefox session
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return WebDriver
	 * @exception
	 **/
	public WebDriver firefoxDriverConnection() {
		setDriverPaths();
		FirefoxOptions option = new FirefoxOptions();
		System.setProperty("webdriver.gecko.driver", geckoDriver);
		option.addArguments("--incognito");
		option.addArguments("--start-maximized");
		driver = new FirefoxDriver(option);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
		return driver;
	}

	/**
	 * @Description start Edge session
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return WebDriver
	 * @exception
	 **/
	public WebDriver edgeDriverConnection() {
		setDriverPaths();
		EdgeOptions option = new EdgeOptions();
		System.setProperty("webdriver.edge.driver", msedgeDriver);
		option.addArguments("--incognito");
		option.addArguments("--start-maximized");
		driver = new EdgeDriver(option);
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(200));
		return driver;
	}
	
	/**
	 * @Description Return WebDriver 
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return WebDriver
	 * @exception 
	 * **/
	public WebDriver getDriver() {
		return driver;
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * @Description get tile from a page
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return String
	 * @exception 
	 * **/
	public String getTitle() {
		String title = driver.getTitle();
		reporter("The page title is: ", title);
		return title;
	}
	
	
	
	/**
	 * @Description Find Element by locator
	 * @author sramones
	 * @Date 01/03/2022
	 * @param By
	 * @return WebElement
	 * @exception 
	 * **/
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}
	
	/**
	 * @Description Find Elements by locator
	 * @author sramones
	 * @Date 01/03/2022
	 * @param By
	 * @return List<WebElement> 
	 * @exception 
	 * **/
	public List<WebElement> findElements(By locator){
		return driver.findElements(locator);
	}
	
	/**
	 * @Description Find Elements by locator and getText
	 * @author sramones
	 * @Date 01/03/2022
	 * @param By
	 * @return String
	 * @exception 
	 * **/
	public String getText(By locator) {
		return findElement(locator).getText();
	}
	
	
	/**
	 * @Description getText from WebElement
	 * @author sramones
	 * @Date 01/03/2022
	 * @param WebElement
	 * @return String
	 * @exception 
	 * **/
	public String getText(WebElement element) {
		return element.getText();
	}
	
	/**
	 * @Description Type text by locator
	 * @author sramones
	 * @Date 01/03/2022
	 * @param locator
	 * @return N/A
	 * @exception 
	 * **/
	public void type(String inputText, By locator) {
		findElement(locator).clear();
		findElement(locator).sendKeys(inputText);
		reporter("Text inserted is", inputText);
		reporterLocator("was inserted", findElement(locator));
	}
	
	/**
	 * @Description Type text by javaScript
	 * @author sramones
	 * @Date 01/03/2022
	 * @param locator
	 * @return N/A
	 * @exception
	 **/
	public void typeJS(String inputText, By locator) {
		WebElement element = findElement(locator); 
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;  
		jsExecutor.executeScript("arguments[0].value='"+inputText+"'", element); 
		reporter("Text inserted is", inputText);
		reporterLocator("was inserted", element);
	}
	/**
	 * @Description selectAll text in a input box
	 * @author sramones
	 * @Date 01/03/2022
	 * @param locator
	 * @return N/A
	 * @exception 
	 * **/
	public void selectAll(By locator) {
		WebElement element = findElement(locator);
	    String selectAll = Keys.chord(Keys.CONTROL, "a");
	    element.sendKeys(selectAll);
	}
	
	/**
	 * @Description click by locator
	 * @author sramones
	 * @Date 01/03/2022
	 * @param locator
	 * @return N/A
	 * @exception 
	 * **/
	public void click(By locator) {
		WebElement button = findElement(locator);
		button.click();
		reporterLocator("was clicked", button);
	}
	
	/**
	 * @Description verify if element exist by locator
	 * @author sramones
	 * @Date 01/03/2022
	 * @param locator
	 * @return boolean
	 * @exception 
	 * **/
	public boolean isDisplayed(By locator) {
		try {
			WebElement element = driver.findElement(locator);
			reporterLocator("is Displayed",element);
			return element.isDisplayed();
		}catch(NoSuchElementException e) {
			reporter("Web element is not displayed");
			return false;
		}
	}
	
	public void openUrl(String url) {
		driver.get(url);
		reporter("The URL was opened: " + url);
	}
	
	
	/**
	 * @Description get Original tab / window
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return String
	 * @exception 
	 * **/
	public String getCurrentTabOrWindow(){
		return driver.getWindowHandle();
	}
	
	
	/**
	 * @Description Switch to tab / window
	 * @author sramones
	 * @Date 01/03/2022
	 * @param String
	 * @return N/A
	 * @exception 
	 * **/
	public void switchToDefaultTabWindow(String originalWindow ) {
		
		for (String windowHandle : driver.getWindowHandles()) {
			if (originalWindow.contentEquals(windowHandle)) {
				driver.switchTo().window(originalWindow);
				reporter("switch to", originalWindow);
				return;
			}
		}
		Assert.fail("Window or Tab do not exist");
	}
	
	/**
	 * @Description Open new tab
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return N/A
	 * @exception 
	 * **/
	public void openNewTab() {
		driver.switchTo().newWindow(WindowType.TAB);
	}

	/**
	 * @Description Open new tab
	 * @author sramones
	 * @Date 01/03/2022
	 * @param N/A
	 * @return N/A
	 * @exception 
	 * **/
	public void openNewWindow() {
		driver.switchTo().newWindow(WindowType.WINDOW);
	}
	
	/**
	 * @Description switch to a frame 
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By
	 * @return N/A
	 * @throws InterruptedException 
	 */
	public void switchToFrame(By locator) {
		driver.switchTo().frame(findElement(locator));
	}
	
	/**
	 * @Description switch to a Default frame 
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By
	 * @return N/A
	 * @throws InterruptedException 
	 */
	public void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}
	
	/**
	 * @Description verify element is present wait till timeout
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By
	 * @return N/A
	 * @throws InterruptedException 
	 */
	public void verifyElementIsPresent(By locator) {
		try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		reporterLocator("is visible", findElement(locator));
		highlighElement(locator);
		}catch(TimeoutException e) {
			Assert.fail("Element is not present");
		}
	}
	
	/**
	 * @Description verify element is visible wait till timeout
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By
	 * @return N/A
	 * @throws InterruptedException 
	 */
	public void verifyElementIsVisible(By locator) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		reporterLocator("is visible", findElement(locator));
		highlighElement(locator);
	}
	
	
	/**
	 * @Description highligh element
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By
	 * @return N/A
	 * @throws InterruptedException 
	 */
	public void highlighElement(By locator) {
			WebElement element = findElement(locator);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border:  solid red');",element);
			js.executeScript("arguments[0].setAttribute('style', 'border: solid 2px white');", element);
			reporterLocator("was highlighted", element);
	}
	
	
	
	/**
	 * @Description report in the log
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String, String
	 * @return N/A
	 **/
	public void reporter(String message, String value) {
		Reporter.log(message + "<b> [ " + value + " ] </b>", true);

	}
	
	/**
	 * @Description report in the log
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String, String
	 * @return N/A
	 **/
	public void reporter(String message) {
		Reporter.log( "<b> [ " + message + " ] </b>", true);

	}

	/**
	 * @Description report in the log the locator used
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter WebElement
	 * @return N/A
	 **/
	public void reporterLocator(String text, WebElement element) {
		if (element.toString().contains("By.") == true) {
			
			Reporter.log("WebElement "+text+ "by locatior ---> <b> " + element.toString().split("By.")[1] + "</b> ",
					true);
		} else if (element.toString().contains("->") == true) {
			Reporter.log("WebEement "+text+ " by locatior ---> <b> " + element.toString().split("->")[1] + "</b> ",
					true);
		}
	}

	/**
	 * @Description select drop down by index
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By, int
	 * @return N/A
	 * @throws StaleElementReferenceException 
	 */
	public void selectDropDownByIndex(By locator, int index) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		int attempts = 0;

		while (attempts < 2) {
			try {

				action.selectByIndex(index);
				reporter("Element was selected by index", String.valueOf(index));
				break;

			} catch (StaleElementReferenceException e) {
				Assert.fail("Cannot select element: "+ dropdown.toString());
			}
			 catch (NoSuchElementException e) {
					Assert.fail("Cannot select element: "+ dropdown.toString());
				}
			attempts++;
		}

	}
	
	/**
	 * @Description select drop down by value
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By, String
	 * @return N/A
	 * @throws StaleElementReferenceException 
	 */
	public void selectDropDownByValue(By locator, String value) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		int attempts = 0;

		while (attempts < 2) {
			try {

				action.selectByValue(value);
				reporter("Element was selected by Value", value);
				break;

			} catch (StaleElementReferenceException e) {
				Assert.fail("Cannot select element: "+ dropdown.toString());
			}
			 catch (NoSuchElementException e) {
					Assert.fail("Cannot select element: "+ dropdown.toString());
				}
			attempts++;
		}

	}
	
	/**
	 * @Description select drop down by visible text
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter By, String
	 * @return N/A
	 * @throws StaleElementReferenceException 
	 */
	public void selectDropDownByVisibleText(By locator, String text) {
		WebElement dropdown = findElement(locator);
		Select action = new Select(dropdown);

		int attempts = 0;

		while (attempts < 2) {
			try {

				action.selectByVisibleText(text);
				reporter("Element was selected by Value", text);
				break;

			} catch (StaleElementReferenceException e) {
				Assert.fail("Cannot select element: "+ dropdown.toString());
			}
			 catch (NoSuchElementException e) {
					Assert.fail("Cannot select element: "+ dropdown.toString());
				}
			attempts++;
		}

	}
	
	/**
	 * @throws Exception
	 * @Description Read JSON file
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String, String
	 * @return JsonNode
	 * @implNote nodeTree.path("fieldName").asText()
	 */
	 public JsonNode readJsonFileByNode(String jsonpath, String nodeName) {
		 JsonNode nodeTree = null;
		 try {
		 ObjectMapper mapper = new ObjectMapper();
		 JsonNode root = mapper.readTree(new File(jsonpath));
		 // Get Name
		 nodeTree = root.path(nodeName);
		 } catch (JsonGenerationException e) {
	            e.printStackTrace();
	        } catch (JsonMappingException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		 return nodeTree;
	 }
	 
	 /**
		 * @throws Exception
		 * @Description Read JSON file
		 * @author sramones
		 * @Date 01/03/2022 
		 * @Parameter String, String
		 * @return JsonNode
		 * @implNote nodeTree.path("fieldName").asText()
		 */
		 public JsonNode readJsonFile(String jsonpath) {
			 JsonNode root = null;
			 try {
			 ObjectMapper mapper = new ObjectMapper();
			  root = mapper.readTree(new File(jsonpath));
			 // Get Name
			 } catch (JsonGenerationException e) {
		            e.printStackTrace();
		        } catch (JsonMappingException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			 return root;
		 }
		 
		 
			/**
			 * @throws Exception
			 * @Description verify expected text
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter String, String
			 * @return N/A
			 * 
			 */
			public void validateExpectedText(String expected, String actual) {
				try {

					Assert.assertEquals(expected, actual);
					reporter(expected + " ] IS EQUAL TO [" + actual);
				} catch (AssertionError e) {
					Assert.fail("text are not matching <b> expeected:  [ " + expected + " ] and actaual: [ " + actual
							+ " ] <b>");
				}
			}
			
			/**
			 * @throws N/A
			 * @Description take and screen shoot of specific part during the execution.
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter N/A 
			 * @return N/A
			 */
			public void takeScreenShot() {
				osName = getOSname();
				switch (osName) {
				case "Mac":
				case "Linux":
					path = "/execution_results/screenshots/";
					break;
				case "Windows":
					path = ".\\execution_results\\screenshots\\";
					break;
				
				}
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
				File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//the below method will save the screen shot in the path that we are passing 
				try {	
						String fullpath =  path + "screen"+"_"+formater.format(calendar.getTime())+".png";
						
						FileUtils.copyFile(srcFile, new File(fullpath));
						fullpath = "."+fullpath;
						Reporter.log("******Placed screen shot in: "+ fullpath+" ******",true);
						Reporter.log("<br> <img src='"+ fullpath+"' height='400' with='400'/></b>",true);
					
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
	
			/**
			 * @throws Exception
			 * @Description generate random name flag true unique name, flag false name in the list
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter Boolean
			 * @return String
			 */
			public String getRandomName(Boolean flag) {
				Date date = new Date();
				String[] people = new String[] { "Sergio", "Ivan", "John", "Marcus", "Henry", "Ismael", "Nishant", "Rakesh",
						"Carlos", "Felix", "Miriam", "Diana", "Adriana", "Alejandro", "Gaby", "Caro", "Melisa", "Aimee",
						"Nataly", "Fernando", "Thomas", "Fidel", "Javier", "Ricardo", "Monica", "Nidia", "Eddy", "Evert", "Ben",
						"Anu", "Rosa","Azucena" };
				List<String> names = Arrays.asList(people);
				Collections.shuffle(names);
				int index = new Random().nextInt(names.size());
				String randomName = names.get(index);
				if (flag == true) {
					DateFormat hourdateFormat = new SimpleDateFormat("HHmmssddMMyyyyssss");
					randomName = randomName + hourdateFormat.format(date);
				}
				
				return randomName;

			}
			
			/**
			 * @throws Exception
			 * @Description generate random last name flag true unique name, flag false name in the list
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter Boolean
			 * @return String
			 */
			public String getRandomLastName(Boolean flag) {
				Date date = new Date();
				String[] people = new String[] { "Ramones", "Velez", "Flores", "Williams", "Hetfield", "Abbot", "Anderson", "Avalos",
						"Ortiz", "Serrato", "Hernandez", "Pushkarna", "Kim", "Reddy", "Paramasivam", "Molina", "Soria", "Heredia",
						"Torres", "Melchor", "Alladi", "Velazquez", "Kumar", "Montesano", "Marcelino","Cruz" };
				List<String> lastNames = Arrays.asList(people);
				Collections.shuffle(lastNames);
				int index = new Random().nextInt(lastNames.size());
				String randomName = lastNames.get(index);
				if (flag == true) {
					DateFormat hourdateFormat = new SimpleDateFormat("HHmmssddMMyyyy");
					randomName = randomName + hourdateFormat.format(date);
				}

				return randomName;

			}
			
			/**
			 * @throws Exception
			 * @Description generate random number length according to the parameter
			 * @author sramones
			 * @Date 01/03/2022 
			 * @Parameter int
			 * @return String
			 */
			public String getRandomNumber(int length) {
				Random numeroRandom = new Random();
				StringBuilder Str = new StringBuilder ();
				
				for (int i=0; i<length;i++) {
					Str.append(numeroRandom.nextInt(length));
				}
				
				return Str.toString();
			}
			
			/**
			 * @throws Exception
			 * @Description click in the webElement
			 * @author sramones
			 * @Date 01/03/2022 
			 * @Parameter By, text
			 * @return N/A
			 */
			public void verifyElementByValueAndClick(By locator, String text) {
				try {
					
					List<WebElement> element = findElements(locator);
					boolean flag = false;
					
					for(WebElement el : element) {
						if(el.getText().equals(text)) {
							scrollJScript(el);
							el.click();
							flag=true;
							Reporter.log("The Element in the list: <b> " + text+ "</b>", true);
							break;
						}
						
					}
					
					if(flag==false) {
						Assert.fail("The text is not in the list: <b> " + text+ "</b>");
					}
					

				} catch (StaleElementReferenceException e) {
					reporter("stale element reference: element is not attached to the page document");
				}
				
				
			}
			
			
			/**
			 * @throws Exception
			 * @Description scroll in to view webElement
			 * @author sramones
			 * @Date 01/03/2022 
			 * @Parameter WebElement
			 * @return N/A
			 */
			public void scrollJScript(WebElement element) {
				try {
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
					
					if (element.toString().contains("By.") == true) {
						Reporter.log("Web element was clicked by locatior ---> <b> " + element.toString().split("By.")[1] + "</b> ",true);
					} else if (element.toString().contains("->") == true) {
						Reporter.log("Web element was clicked by locatior ---> <b> " + element.toString().split("->")[1] + "</b> ",true);
					}
				} catch (Exception e) {
					Reporter.log("Its not posible to Scroll to the Web element by locatior ---> <b>"
							+ element.toString().split("By.")[1] + "</b>", true);
				}
			}// end scroll
			
			
			 
			/**
			 * @throws N/A
			 * @Description This method is take today date plus the amount of days that you are give by parameter and returned
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter int 
			 * @return String
			 * @implNote N/A
			 */
			public String getDate(int amountDays){
				 
				 	Date myDate = new Date();
					DateFormat df=new SimpleDateFormat("MM/DD/YYYY");//("YYYY-MM-dd");
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(myDate);
					cal.add(Calendar.DATE, amountDays); 
					
					myDate = cal.getTime();
					
					String date=df.format(myDate);
								
					return date;
				 
			 }
			
			/**
			 * @throws N/A
			 * @Description Click webElement with JScript.
			 * @author sramones
			 * @Date 01/03/2022
			 * @Parameter WebElement 
			 * @return N/A
			 */
			public void clickJScript(By locator) {
				try {
					WebElement element = findElement(locator);
					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript("arguments[0].click();", element);

					if (element.toString().contains("By.") == true) {
						Reporter.log("Web element was clicked by locatior ---> <b> " + element.toString().split("By.")[1]
								+ "</b> ", true);
					}
					else if (element.toString().contains("->") == true) {
						Reporter.log("Web element was clicked by locatior ---> <b> " + element.toString().split("->")[1]
								+ "</b> ", true);
					}

				} catch (ArrayIndexOutOfBoundsException e) {
					Reporter.log("ArrayIndexOutOfBoundsException: ",true);
				}

				
			}
}//end class
