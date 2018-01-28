package com.automation.support;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class MyListener extends AbstractWebDriverEventListener {

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		List<WebElement> el = driver.findElements(by);
		for (WebElement elem : el) {
			// draw a border around the found element
			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver).executeScript("arguments[0].style.border='4px solid green'", elem);
			}
		}
	}

}
