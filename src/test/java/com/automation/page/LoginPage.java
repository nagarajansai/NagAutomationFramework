package com.automation.page;

import org.openqa.selenium.By;

import com.automation.webaccelerators.ActionEngine;

public class LoginPage extends ActionEngine {

	public static By LOGINBTN;
	public static By EMAIL_ERR_MSG;
	public static By PASSWORD_ERR_MSG;

	static
	{
		LOGINBTN = By.xpath(".//*[@id='top']/div[1]/div/form/fieldset/div[3]/button");
		EMAIL_ERR_MSG = By.xpath(".//*[@id='top']/div[1]/div/form/fieldset/div[1]/div/span");
		PASSWORD_ERR_MSG = By.xpath(".//*[@id='top']/div[1]/div/form/fieldset/div[2]/div/span");
	
	}

}