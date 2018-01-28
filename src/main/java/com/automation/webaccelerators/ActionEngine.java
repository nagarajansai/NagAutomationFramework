
package com.automation.webaccelerators;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

public class ActionEngine extends TestEngineWeb {
	private final Logger logger = Logger.getLogger(ActionEngine.class);

	private final String msgClickSuccess = "Successfully clicked on ";
	private final String msgClickFailure = "Unable to click on ";
	private final String msgTypeSuccess = "Successfully typed on ";
	private final String msgTypeFailure = "Unable to type on ";
	private final String msgIsElementFoundSuccess = "Successfully found element ";
	private final String msgIsElementFoundFailure = "Unable to find element ";
	private final String msgArrStrSuccess = "List values are exactly matched";
	private final String msgArrStrFailure = "List values are NOT matched";
	public int sleep = 3000;
	private Boolean status=false;

	public boolean selectByIndex(By locator, int index, String locatorName) throws Throwable {
		try {
			Select s = new Select(Driver.findElement(locator));
			s.selectByIndex(index);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean assertTrue(boolean conditon, String message) throws Throwable {
		try {
			if (conditon)
				return true;
			else
				return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean assertElementPresent(By by, String locatorName) throws Throwable {

		try {
			Assert.assertTrue(isElementPresent(by, locatorName, true));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	public boolean mouseHoverByJavaScript(By locator, String locatorName) throws Throwable {
		try {
			WebElement mo = Driver.findElement(locator);
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript(javaScript, mo);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean waitForVisibilityOfElement(By by, String locator) throws Throwable {
		WebDriverWait wait = new WebDriverWait(Driver, 15);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean clickUsingJavascriptExecutor(By locator, String locatorName) throws Throwable {
		try {

			WebElement element = Driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) Driver;
			executor.executeScript("arguments[0].click();", element);
			Waittime();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean selectByValue(By locator, String value, String locatorName) throws Throwable {
		try {
			Select s = new Select(Driver.findElement(locator));
			s.selectByValue(value);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean isVisible(By locator, String locatorName) throws Throwable {

		try {
			Driver.findElement(locator).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;

		}
	}

	public int getRowCount(By locator) throws Exception {
		WebElement table = Driver.findElement(locator);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int a = rows.size() - 1;
		return a;
	}

	public boolean assertTextMatching(By by, String text, String locatorName) throws Throwable {

		try {
			Waittime();
			String ActualText = getText(by, text).trim();
			System.out.println("ActualText is " + ActualText);
			if (ActualText.contains(text.trim())) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}

	}

	public boolean assertTextMatchingWithAttribute(By by, String text, String locatorName) throws Throwable {
		try {
			String ActualText = getAttributeByValue(by, text).trim();
			System.out.println("Actual value for " + locatorName + " is : " + ActualText);
			if (ActualText.contains(text.trim())) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {

			return false;
		}

	}

	public boolean assertTextStringMatching(String acttext, String expText) throws Throwable {
		try {

			String ActualText = acttext.trim();
			System.out.println("Actual value - " + ActualText);
			System.out.println("Expected value - " + expText);
			if (ActualText.contains(expText.trim())) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean assertStringArrayMatching(Object[] o1, Object[] o2) throws Throwable {

		try {
			Assert.assertEquals(o1, o2);
			test.log(LogStatus.PASS, msgArrStrSuccess);
			status=true;
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, msgArrStrFailure);			
			return false;
		} finally {
			if(status==false)
			{
			test.log(LogStatus.FAIL, msgArrStrFailure);
			}
		}

	}

	public boolean click(By locator, String locatorName) throws Throwable {
		try {
			WebDriverWait wait = new WebDriverWait(Driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Driver.findElement(locator).click();
			test.log(LogStatus.PASS, msgClickSuccess + locatorName);
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, msgClickFailure + locatorName);
			test.log(LogStatus.FAIL, test.addScreenCapture(CaptureScreen("C:/Nagarajan/TestReport")));
			return false;
		}

	}

	public boolean isElementPresent(By by, String locatorName, boolean expected) throws Throwable {

		try {

			Waittime();
			Driver.findElement(by);
			test.log(LogStatus.PASS, locatorName + " is present in the page");
			return true;
		} catch (Exception e) {
			test.log(LogStatus.FAIL, locatorName + " is not present in the page");
			return false;

		}
	}

	public boolean isElementPresent(By by, String locatorName) throws Throwable {
		boolean status = false;
		try {
			// Waittime();
			Driver.findElement(by);
			test.log(LogStatus.PASS, locatorName + " is present on the page");
			return true;
		} catch (Exception e) {
			test.log(LogStatus.PASS, locatorName + " is not present on the page");
			return false;
		}
	}

	public boolean scroll(By by, String locatorName) throws Throwable {

		try {
			WebElement element = this.Driver.findElement(by);
			Actions actions = new Actions(this.Driver);
			actions.moveToElement(element);
			actions.build().perform();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean VerifyElementPresent(By by, String locatorName, boolean expected) throws Throwable {
		try {
			if (this.Driver.findElement(by).isDisplayed()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;

		}
	}

	public boolean Waittime() throws Throwable {
		boolean status = true;
		String time = "5";
		long timevalue = Long.parseLong(time);
		Thread.sleep(timevalue * 1000);
		return status;

	}

	public boolean typeWithAssert(By locator, String testdata, String locatorName) throws Throwable {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(Driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			Driver.findElement(locator).click();
			Driver.findElement(locator).clear();
			Driver.findElement(locator).sendKeys(testdata);
			String strToBeDisplayed = Driver.findElement(locator).getAttribute("value");
			assertTextStringMatching(strToBeDisplayed, testdata);
			test.log(LogStatus.PASS, "Enter text in " + locatorName + ": " + msgTypeSuccess + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			test.log(LogStatus.FAIL, "Enter text in " + locatorName + ": " + msgTypeFailure + locatorName);
			test.log(LogStatus.FAIL, test.addScreenCapture(CaptureScreen("C:/Nagarajan/TestReport")));

		}
		return status;
	}

	public boolean typeWithAssertNew(WebElement locator, String testdata, String locatorName) throws Throwable {
		boolean status = false;
		try {
			// WebDriverWait wait = new WebDriverWait(Driver, 10);
			// wait.until(ExpectedConditions.elementToBeClickable(locator));
			locator.click();
			locator.clear();
			locator.sendKeys(testdata);
			String strToBeDisplayed = locator.getAttribute("value");
			assertTextStringMatching(strToBeDisplayed, testdata);
			test.log(LogStatus.PASS, "Enter text in " + locatorName + ": " + msgTypeSuccess + locatorName);
			status = true;
		} catch (Exception e) {
			status = false;
			test.log(LogStatus.FAIL, "Enter text in " + locatorName + ": " + msgTypeFailure + locatorName);
			test.log(LogStatus.FAIL, test.addScreenCapture(CaptureScreen("C:/Nagarajan/TestReport")));

		}
		return status;
	}

	public String CaptureScreen(String ImagesPath) {
		TakesScreenshot oScn = (TakesScreenshot) Driver;
		File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
		File oDest = new File(ImagesPath + ".jpg");
		try {
			FileUtils.copyFile(oScnShot, oDest);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return ImagesPath + ".jpg";
	}

	public boolean typeUsingJavaScriptExecutor(By locator, String testdata, String locatorName) throws Throwable {
		try {

			WebElement searchbox = Driver.findElement(locator);
			JavascriptExecutor myExecutor = ((JavascriptExecutor) Driver);
			myExecutor.executeScript("arguments[0].value='" + testdata + "';", searchbox);
			String strToBeDisplayed = searchbox.getAttribute("value");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean waitForTitlePresent(By locator) throws Throwable {
		try {
			for (int i = 0; i < 200; i++) {
				if (Driver.findElements(locator).size() > 0) {
					break;
				} else {
					Driver.wait(50);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public String getTitle() throws Throwable {

		String text = Driver.getTitle();
		return text;
	}

	public boolean assertText(By by, String text) throws Throwable {
		try {
			Assert.assertEquals(getText(by, text).trim(), text.trim());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean asserTitle(String title) throws Throwable {
		try {
			By windowTitle = By.xpath("//title[contains(text(),'" + title + "')]");
			if (waitForTitlePresent(windowTitle)) {
				Assert.assertEquals(getTitle(), title);
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}

	}

	public String getText(By locator, String locatorName) throws Throwable {
		String text = "";
		try {

			Waittime();
			if (isElementPresent(locator, locatorName, true)) {
				text = Driver.findElement(locator).getText();
				return text;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public String getAttributeByValue(By locator, String locatorName) throws Throwable {
		String text = "";

		try {
			Waittime();
			if (isElementPresent(locator, locatorName, true)) {
				text = Driver.findElement(locator).getAttribute("value");
				return text;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public boolean mouseover(By locator, String locatorName) throws Throwable {

		try {
			WebElement mo = this.Driver.findElement(locator);
			new Actions(this.Driver).moveToElement(mo).build().perform();
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public boolean JSClick(By locator, String locatorName) throws Throwable {
		try {

			WebElement element = this.Driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) this.Driver;
			executor.executeScript("arguments[0].click();", element);
			// driver.executeAsyncScript("arguments[0].click();", element);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public boolean selectByVisibleText(By locator, String visibletext, String locatorName) throws Throwable {
		try {
			Waittime();
			Select s = new Select(Driver.findElement(locator));
			s.selectByVisibleText(visibletext.trim());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean JSmousehover(By locator, String locatorName) throws Throwable {
		try {
			WebElement HoverElement = this.Driver.findElement(locator);
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
			((JavascriptExecutor) this.Driver).executeScript(mouseOverScript, HoverElement);
			return true;

		} catch (Exception e) {

		}
		return false;
	}

	public void sleep(int time) throws InterruptedException {
		Thread.sleep(time);
	}

	public boolean verify(String act, String exp, String value) throws Throwable {
		if (act.contains(exp)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean waitForElementPresent(By by, String locator, int secs) throws Throwable {

		try {
			WebDriverWait wait = new WebDriverWait(this.Driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(by));

			for (int i = 0; i < secs / 2; i++) {
				List<WebElement> elements = this.Driver.findElements(by);
				if (elements.size() > 0) {
					return true;

				} else {
					this.Driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
				}
			}

		} catch (Exception e) {

			return false;
		}
		return false;

	}

	public float appLoadingTime(By locator) throws Throwable {
		float timeTaken = 0;

		try {
			long start = System.currentTimeMillis();
			System.out.println("start " + start);
			WebDriverWait wait = new WebDriverWait(Driver, 2400);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			// wait.until(ExpectedConditions.elementToBeClickable(locator));
			long stop = System.currentTimeMillis();
			System.out.println("stop " + stop);
			timeTaken = (stop - start);
			System.out.println("The time taken for the page to load is " + timeTaken + " milli seconds");
			return timeTaken;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeTaken;
	}

	public void appLoadingTimeByElementVisible(By locator) throws Throwable {
		float timeTaken = 0;

		try {
			long start = System.currentTimeMillis();
			WebDriverWait wait = new WebDriverWait(Driver, 100);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			long stop = System.currentTimeMillis();
			timeTaken = (stop - start) / 1000;

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void createExceptionTraceTitle(String functionTitle) throws Throwable {

		try {

			int length = functionTitle.length() + 6;
			String starRow = "";

			for (int i = 0; i < length; i++) {
				starRow += "*";
			}

			System.out.println(starRow);
			System.out.println("*  " + functionTitle + "  *");
			System.out.println(starRow);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDropDownSelectedText(By locator, String locatorName) throws Throwable {
		String text = "";
		try {

			Waittime();
			if (isElementPresent(locator, locatorName, true)) {
				Select s = new Select(Driver.findElement(locator));
				text = s.getFirstSelectedOption().getText();
				return text;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	public void waitUntilLoadingCompletes() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		try {
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.xpath("//*[contains(@class,'hb-progress-indicator')]/div/img")));

		} catch (Exception e) {
			System.out.println("Loading Throbber Element not found");
		}
	}

	public void waitUntilWidgetLoadingCompletes() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		try {
			wait.until(
					ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@class='hb-progress-indicator']")));

		} catch (Exception e) {
			System.out.println("Loading Throbber Element not found");
		}
	}

	public boolean type(By locator, String testdata, String locatorName) throws Throwable {

		try {
			Waittime();
			Driver.findElement(locator).click();
			Driver.findElement(locator).clear();
			Driver.findElement(locator).sendKeys(testdata);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean verifyTextInCurrentPage(String text) throws Throwable {
		try {
			sleep(5000);
			By elementPath = By.xpath("//*[text()='" + text + "']");
			WebElement element = Driver.findElement(elementPath);
			if (element != null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(
					"Exception when trying to check whether the text " + text + " is present in the current page");
			System.out.println(e);
		}
		return false;
	}

	public void verifyTextInCurrentPageWithAssertAndCount(String text, int occurrenceCount, String message)
			throws Throwable {
		boolean result = false;
		try {
			By elementPath = By.xpath("//*[text()='" + text + "']");
			result = Driver.findElements(elementPath).size() == occurrenceCount;
		} catch (Throwable t) {
			System.out.println(
					"Exception when trying to check whether the text " + text + " is present in the current page");
			System.out.println(t);

		}
	}

	public boolean currentPageContainsText(String text) {
		try {
			sleep(5000);
			By elementPath = By.xpath("//*[contains(text(),'" + text + "')]");
			WebElement element = Driver.findElement(elementPath);
			if (element != null) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(
					"Exception when trying to check whether the text " + text + " is present in the current page");
			System.out.println(e);
		}
		return false;

	}

	public boolean isElementPresentAssert(By by, String locatorName) throws Throwable {
		try {
			Driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

}
