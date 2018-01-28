package com.automation.utilities;

import java.util.Hashtable;

public class TestUtil {

	public static Object[][] getData(String testCase, Xls_Reader xls, String sheetName) {

		System.out.println("******getData*******: " + testCase);

		if (xls.getRowCount(sheetName) == 0) {
			System.out.println("Sheetname is wrong: " + sheetName);
		} 

		int testCaseStartRowNum = 0;
		int file=0;
		Object[][] data = null;
		for (int rNum = 0; rNum <= xls.getRowCount(sheetName); rNum++) {
			if (testCase.equals(xls.getCellData(sheetName, 0, rNum))) {
				testCaseStartRowNum = rNum;
				System.out.println("testCase name is Valid: " + testCase);
				System.out.println("testCaseStartRowNum: " + testCaseStartRowNum);
				file=1;
			}
		}
		if (file==0)
		{
			System.out.println("testCase name is invalid: " + testCase);
		}
		else{

		System.out.println("Test Starts from row -> " + testCaseStartRowNum);
		int colStartRowNum = testCaseStartRowNum + 1;
		int cols = 0;
		// Get the total number of columns for which test data is present
		while (!xls.getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}

		System.out.println("Total cols in test -> " + cols);
		// rows
		int rowStartRowNum = testCaseStartRowNum + 2;
		int rows = 0;
		// Get the total number of rows for which test data is present
		while (!xls.getCellData(sheetName, 0, (rowStartRowNum + rows)).equals("")) {
			rows++;
		}

		System.out.println("Total rows in test -> " + rows);
		data = new Object[rows][1];
		Hashtable<String, String> table = null;
		// print the test data
		for (int rNum = rowStartRowNum; rNum < (rows + rowStartRowNum); rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				table.put(xls.getCellData(sheetName, cNum, colStartRowNum), xls.getCellData(sheetName, cNum, rNum));
				System.out.print(xls.getCellData(sheetName, cNum, colStartRowNum) + " : ");
				System.out.print(xls.getCellData(sheetName, cNum, rNum) + " | ");
			}
			data[rNum - rowStartRowNum][0] = table;
			System.out.println();
		}
	}
		return data;
	}

}
