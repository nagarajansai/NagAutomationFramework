package com.automation.webaccelerators;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.automation.report.ReporterConstants;
import com.automation.support.ExcelUtility;
import com.automation.support.MyListener;
import com.automation.utilities.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class TestEngineWeb {
	public final Logger LOG = Logger.getLogger(TestEngineWeb.class);

	protected static WebDriver wdriver = null;
	protected static WebDriver RemoteDriver = null;
	protected static EventFiringWebDriver Driver = null;
	public static ExtentReports extent;
	public ExtentTest test;

	/* cloud platform */
	public String browser = null;
	public String version = null;
	public String platform = null;
	public String environment = null;
	public String userName = null;
	public String localBaseUrl = null;

	public String executedFrom = null;
	public String executionType = null;
	public String suiteExecution = null;
	public String suiteStartTime = null;
	public static long startTime;
	int randNo;
	public File downloadFilePath;

	
	public static String loginData = null;

	public static Xls_Reader testData = null;

	public void testEngineWeb() {

		try {
			
			testData = new Xls_Reader(ExcelUtility.getExcelUrlBasedOnEnv().get("stage_testdata"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Parameters({ "executionType", "suiteExecuted", "automationName", "browser", "browserVersion", "environment",
			"platformName" })
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext ctx, String type, String suite, String automationName, String browser,
			String browserVersion, String environment, String platformName) {
		try {

			testEngineWeb();

			this.browser = browser;
			this.version = browserVersion;
			this.platform = platformName;
			this.environment = environment;
			this.localBaseUrl = ReporterConstants.APP_BASE_URL;

			executionType = type;
			suiteExecution = suite;
			PropertyConfigurator.configure(System.getProperty("user.dir") + "/Log.properties");
			startTime = System.currentTimeMillis();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_yyyy hh mm ss SSS");
			String formattedDate = sdf.format(date);
			suiteStartTime = formattedDate.replace(":", "_").replace(" ", "_");
			System.out.println("Suite time ==============>" + suiteStartTime);
			extent = new ExtentReports("C:/Nagarajan/TestReport/TestResults.html", true);
			extent.loadConfig(new File(
					"C:/Nagarajan/MyEclipseWorkspace/FredMeyerFramework/src/main/resources/extent-config.xml"));
			openBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setWebDriverForLocal(String browser) throws IOException, InterruptedException {

		try {

			switch (browser) {
			case "firefox":
				wdriver = new FirefoxDriver();
				break;

			case "ie":
				System.out.println("iam in case IE");
				DesiredCapabilities capab = DesiredCapabilities.internetExplorer();
				capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				DesiredCapabilities.internetExplorer().setCapability("ignoreProtectedModeSettings", true);
				File file = new File("Drivers\\IEDriverServer.exe");
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				capab.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capab.setJavascriptEnabled(true);
				capab.setCapability("requireWindowFocus", true);
				capab.setCapability("enablePersistentHover", false);

				wdriver = new InternetExplorerDriver(capab);
				break;
			case "chrome":
				System.out.println("In the case Chrome");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				System.setProperty("webdriver.chrome.driver",
						"C:\\Nagarajan\\Software\\chromedriver_win32 (1)\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("start-maximized");
				options.addArguments("test-type");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				Map<String, Object> preferences = new Hashtable<String, Object>();
				preferences.put("profile.default_content_settings.popups", 0);
				preferences.put("download.prompt_for_download", "false");

				// disable flash and the PDF viewer
				preferences.put("plugins.plugins_disabled", new String[] { "Adobe Flash Player", "Chrome PDF Viewer" });

				options.setExperimentalOption("prefs", preferences);

				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				wdriver = new ChromeDriver(capabilities);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openBrowser() {
		try {

			if (environment.equalsIgnoreCase("local")) {
				setWebDriverForLocal(browser);
			}
			Driver = new EventFiringWebDriver(wdriver);
			MyListener myListener = new MyListener();
			Driver.register(myListener);
			Driver.get(ReporterConstants.APP_BASE_URL);
			Driver.manage().window().maximize();
			Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void closeBrowser() {
		extent.endTest(test);
		extent.flush();
		Driver.quit();
	}

}
