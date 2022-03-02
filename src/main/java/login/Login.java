package login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utility.Base;

public class Login extends Base{
	
	By txt_email = By.id("email");
	By txt_password = By.id("password");
	By btn_signIn = By.xpath("//span[text()='Sign in']/parent::*");
	By btn_new = By.xpath("//button[@data-testid='add-new-button']");
	

	public Login(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @Description Login
	 * @author sramones
	 * @Date 01/03/2022
	 * @param String, String
	 * @return N/A
	 * @exception 
	 * **/
	public void login(String user, String password) {
		verifyElementIsPresent(btn_signIn);
		type(user ,txt_email);
		type(password ,txt_password);
		click(btn_signIn);
		verifyElementIsPresent(btn_new);
		takeScreenShot();
		
	}
}
