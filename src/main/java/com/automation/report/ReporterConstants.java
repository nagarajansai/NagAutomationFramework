package com.automation.report;

public interface ReporterConstants {
	String configReporterFile = "resources/datafile.properties";

	String TEST_CASE_STATUS_PASS = "PASS";

	String TEST_CASE_STATUS_FAIL = "FAIL";

	String CLIENT_NAME = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "clientName");

	String DEVICE_NAME = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "deviceName");

	String ITERAION_MODE = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "iterationMode");

	String SUITE_NAME = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "suiteName");

	String ON_ERROR_ACTION = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "onErrorAction");

	String APP_UNDER_TEST = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "AUT");

	String APP_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "baseUrl");

	String APP_BASEURL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "baseUrl2");
	String Timeout = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "Waittime");

	String BROWSER_NAME = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "browserName");

	String BROWSER_VERSION = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "version");

	String BROWSER_PLATFORM = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "platform");

	String REPORT_FORMAT = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "reportFormat");

	String LOCATION_RESULT = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "locationResult");

	String FOLDER_SCREENRSHOTS = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "folderScreenShot");

	Boolean BOOLEAN_ONSUCCESS_SCREENSHOT = Boolean
			.parseBoolean(ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "onSuccessScreenshot"));

	Boolean UPDATE_TEST_RESULTS = Boolean
			.parseBoolean(ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "updateJira"));

}
