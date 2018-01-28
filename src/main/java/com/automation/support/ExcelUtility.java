package com.automation.support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.automation.report.ConfigFileReadWrite;
import com.automation.report.ReporterConstants;
import com.automation.utilities.Xls_Reader;

public class ExcelUtility {

	static FileWriter fos;
	String configReporterFile = "resources/gallopReporter.properties";
	static String APP_BASE_URL = ConfigFileReadWrite.read(ReporterConstants.configReporterFile, "baseUrl");
	public static Pattern rxquote = Pattern.compile("\"");

	private static Xls_Reader xls = null;

	static final String browserConfigSheetName = "browserConfiguration";
	static final String allTestsSheetName = "allTests";
	static Map<String, String> map = new HashMap<>();

	public static void modifyFile(String filePath, String oldString, String newString) {
		File fileToBeModified = new File(filePath);
		String oldContent = "";
		BufferedReader reader = null;
		FileWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			String line = reader.readLine();
			while (line != null) {
				oldContent = oldContent + line + System.lineSeparator();
				line = reader.readLine();
			}

			String newContent = oldContent.replaceAll(oldString, newString);
			writer = new FileWriter(fileToBeModified);
			writer.write(newContent);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void copyFileFolders(String source, String dest) {
		try {
			File fsource = new File(source);
			File fdest = new File(dest);
			try {
				if (fdest.exists()) {
					fdest.delete();
					FileUtils.copyDirectory(fsource, fdest);
				}
				System.out.println("Files are copied successfully");
			} catch (IOException e) {
				System.out.println("Files are not copied successfully");
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String encodeValue(String value) {
		boolean needQuotes = false;
		if (value.indexOf(',') != -1 || value.indexOf('"') != -1 || value.indexOf('\n') != -1
				|| value.indexOf('\r') != -1)
			needQuotes = true;
		Matcher m = rxquote.matcher(value);
		if (m.find())
			needQuotes = true;
		value = m.replaceAll("\"\"");
		if (needQuotes)
			return "\"" + value + "\"";
		else
			return value;
	}

	public static Map<String, String> getExcelUrlBasedOnEnv() {
		// map.put("stage_logindata", System.getProperty("user.dir") +
		// "/TestData/LoginData.xlsx");
		map.put("stage_testdata", System.getProperty("user.dir") + "/TestData/TestDataNew.xlsx");
		// map.put("qa_testdata", System.getProperty("user.dir") +
		// "/TestData/TestData_QA.xlsx");
		return map;
	}

//	synchronized public static void createXml(String sheetName) throws IOException {
//
//		try {
//
//			if (APP_BASE_URL.contains("hb-cchh-stage")) {
//				xls = new Xls_Reader(getExcelUrlBasedOnEnv().get("cchh_testdata"));
//			} else if (APP_BASE_URL.contains("qa")) {
//				xls = new Xls_Reader(getExcelUrlBasedOnEnv().get("qa_testdata"));
//			} else {
//				xls = new Xls_Reader(getExcelUrlBasedOnEnv().get("stage_testdata"));
//			}
//
//			/*
//			 * new Xls_Reader( System.getProperty("user.dir") + File.separator +
//			 * "TestData" + File.separator + "TestData.xlsx");
//			 */
//
//			System.out.println("Test Data Sheet " + xls.path + xls.filename + System.getProperty("user.dir"));
//			String outputFileName = System.getProperty("user.dir") + File.separator + "testng.xml";
//
//			if (new File(outputFileName).exists()) {
//				System.out.println("Test suite exists in" + outputFileName);
//				new File(outputFileName).delete();
//			}
//
//			fos = new FileWriter(outputFileName);
//			fos.write("<?xml version='1.0' encoding='UTF-8'?>\n");
//			fos.write("<!DOCTYPE suite SYSTEM 'http://testng.org/testng-1.0.dtd'>\n");
//			fos.write(
//					"<suite name='Mumms Automation test suite' parallel='tests' thread-count='5' preserve-order='true'>\n\n");
//
//			fos.write("<parameter name='suiteExecuted' value='Regression' />\n");
//			fos.write("<parameter name='executionType' value='Sequential' />\n");
//
//			fos.write("<listeners>\n");
//
//			fos.write("\t<listener class-name=\"org.uncommons.reportng.HTMLReporter\" />\n");
//			fos.write("\t<listener class-name=\"org.uncommons.reportng.JUnitXMLReporter\" />\n");
//			fos.write("\t<listener class-name=\"com.automation.utilities.AssignTestPriorityTransformer\" />\n");
//
//			fos.write("</listeners>\n");
//
//			int startRow = 2;
//
//			for (int rNum = startRow; rNum <= xls.getRowCount(sheetName); rNum++) {
//
//				String browser = xls.getCellData(sheetName, "Browser", rNum);
//				if ((!browser.isEmpty()) && (xls.getCellData(sheetName, "RunMode", rNum).equalsIgnoreCase("Y"))) {
//
//					fos.write("\t<test name=\"" + browser + "\" preserve-order='true'>\n");
//
//					HashMap<String, String> browseConfigData = getBrowserDetails(browserConfigSheetName, browser);
//					fos.write("\t\t\t<parameter name=\"browser\" value=\"" + browseConfigData.get("browser")
//							+ "\"></parameter>\n");
//					fos.write("\t\t\t<parameter name=\"automationName\" value=\""
//							+ browseConfigData.get("automationName") + "\"></parameter>\n");
//					fos.write("\t\t\t<parameter name=\"browserVersion\" value=\""
//							+ browseConfigData.get("browserVersion") + "\"></parameter>\n");
//					fos.write("\t\t\t<parameter name=\"platformName\" value=\"" + browseConfigData.get("platformName")
//							+ "\"></parameter>\n");
//					fos.write("\t\t\t<parameter name=\"environment\" value=\"" + browseConfigData.get("environment")
//							+ "\"></parameter>\n");
//
//					fos.write("\t\t <classes>\n");
//
//					List<String> allClasses = getClasses(allTestsSheetName, browser);
//
//					for (int k = 0; k < allClasses.size(); k++) {
//
//						System.out.println("class ->" + allClasses.get(k));
//						fos.write("\t\t\t<class name=\"" + allClasses.get(k) + "\">\n");
//						List<String> allMethods = getMethods(allTestsSheetName, allClasses.get(k));
//						fos.write("\t\t\t\t<methods>\n");
//						String userrole = getuserrole(allTestsSheetName, allClasses.get(k));
//						if (!userrole.isEmpty())
//							fos.write("\t\t\t<parameter name=\"userrole\" value=\"" + userrole + "\"></parameter>\n");
//						for (int l = 0; l < allMethods.size(); l++) {
//							System.out.println("\t method ->" + allMethods.get(l));
//							fos.write("\t\t\t\t\t <include name=\"" + allMethods.get(l) + "\" />\n");
//						}
//						fos.write("\t\t\t\t</methods>\n");
//
//						fos.write("\t\t\t </class>\n");
//					}
//					fos.write("\t\t </classes>\n");
//					fos.write("\t</test>\n");
//					fos.write("\n");
//
//				}
//
//			}
//
//			fos.write("</suite>");
//			fos.flush();
//
//			if (new File(outputFileName).exists()) {
//				System.out.println("Test suite successfully generated in" + outputFileName);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//	}

	public static List<String> getClasses(String allTestsSheetName, String browser) {
		List<String> classes = new ArrayList<String>();

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "browserToExecute", i).contains(browser))
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y"))) {

				String classname = xls.getCellData(allTestsSheetName, "classname", i);
				classes.add(classname);
			}

		}

		return classes;
	}

	public static List<String> getMethods(String allTestsSheetName, String className) {
		List<String> methods = new ArrayList<String>();

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className))
					&& (xls.getCellData(allTestsSheetName, "Runmode", i).equalsIgnoreCase("Y"))) {
				int methodNum = i + 1;
				do {
					String methodName = xls.getCellData(allTestsSheetName, "testmethod", methodNum);
					if (!(methodName.isEmpty())
							&& (xls.getCellData(allTestsSheetName, "Runmode", methodNum).equalsIgnoreCase("Y"))) {

						methods.add(methodName);
					}
					methodNum++;

				} while (!(xls.getCellData(allTestsSheetName, "testmethod", methodNum).isEmpty()));

			}

		}

		return methods;
	}

	public static String getuserrole(String allTestsSheetName, String className) {

		String userrole = "";

		int startRow = 2;
		for (int i = startRow; i < xls.getRowCount(allTestsSheetName); i++) {

			if ((xls.getCellData(allTestsSheetName, "classname", i).equalsIgnoreCase(className))) {
				userrole = xls.getCellData(allTestsSheetName, "userrole", i);
			}

		}

		return userrole;
	}

	public static HashMap<String, String> getBrowserDetails(String browserConfigSheetName, String browser) {
		HashMap<String, String> browserDetails = new HashMap<String, String>();

		int startRow = 1;
		for (int i = startRow; i < xls.getRowCount(browserConfigSheetName); i++) {

			String browserShown = xls.getCellData(browserConfigSheetName, 0, i);
			if ((!(browserShown.isEmpty()) && browserShown.equalsIgnoreCase(browser))) {

				int colCount = xls.getColumnCount(browserConfigSheetName, i);
				for (int j = 0; j < colCount; j++) {

					String key = xls.getCellData(browserConfigSheetName, j, i + 1);
					// System.out.println("key -" + key);
					String value = xls.getCellData(browserConfigSheetName, j, i + 2);
					// System.out.println("value -" + value);
					browserDetails.put(key, value);
				}

			}

		}

		return browserDetails;
	}

	public static String getBrowser() {

		String browser = null;
		try {

			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("browser".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}

	public static String getglobalUsername() {
		String browser = null;
		try {
			String sheetName = "config";
			for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
				if ("userrole".equals(xls.getCellData(sheetName, 0, rNum))) {
					browser = xls.getCellData(sheetName, 1, rNum);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return browser;
	}

	public static boolean isClassExecutable(String sheetName, int rNum) {
		boolean flag = false;

		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;

	}

	public static boolean isMethodExecutable(String sheetName, int rNum) {
		boolean flag = false;
		try {
			if (xls.getCellData(sheetName, "Runmode", rNum).equalsIgnoreCase("Y"))
				flag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

}
