package com.automation.page;

import org.openqa.selenium.By;

import com.automation.webaccelerators.ActionEngine;

public class HomePage extends ActionEngine {

	public static By MENU_LIST;
	static {
		MENU_LIST = By.xpath(".//*[@class='AspNet-Menu-WithChildren']/a");
	}

}