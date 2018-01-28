package com.automation.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegisterPage  {

	public static By LOGIN;
	public static By EMAIL;
	public static By RENTER_EMAIL;
	public static By PASSWORD;
	public static By REGISTER;
	public static By LOGINHERE;
	
	@FindBy(xpath=".//*[@id='id_email']")
	public WebElement EMAILNEW;

	static
	{
		LOGIN = By.xpath(".//*[@id='content']/div/nav/div/div[2]/ul[2]/li/a");
		
		EMAIL = By.xpath(".//*[@id='id_email']");
		RENTER_EMAIL = By.xpath(".//*[@id='id_email2']");
		PASSWORD = By.xpath(".//*[@id='id_password']");
		REGISTER = By.xpath(".//*[@id='top']/div[1]/div/form/fieldset/div[4]/button");
		LOGINHERE=By.xpath(".//a[text()='Login here']");
	}

}