package com.automation.support;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxtoCSV  extends ExcelUtility{

	public static Logger logger = Logger.getLogger(XlsxtoCSV.class);

	public static void xlsx(File inputFile, File outputFile) {
		// For storing data into CSV files
		StringBuffer data = new StringBuffer();

		try {

			FileOutputStream fos = new FileOutputStream(outputFile);
			// Get the workbook object for XLSX file
			XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile));
			// Get first sheet from the workbook
			XSSFSheet sheet = wBook.getSheetAt(0);
			Row row;
			Cell cell;
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			// int colCount = sheet.getRow(0).getLastCellNum();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				// For each row, iterate through each columns

				for (int i = 0; i <= row.getLastCellNum(); i++) {
					cell = row.getCell(i);

					if (cell == null) {
						data.append("" + ",");
						continue;
					}
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						data.append(cell.getBooleanCellValue() + ",");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						data.append(cell.getNumericCellValue() + ",");
						break;
					case Cell.CELL_TYPE_STRING:
						data.append(encodeValue(cell.getStringCellValue()) + ",");
						break;
					case Cell.CELL_TYPE_BLANK:
						data.append("" + ",");
						break;
					default:
						data.append(cell + ",");
						break;
					}
				}
				data.append("\n");
			}
			fos.write(data.toString().getBytes());
			System.out.println("xls file has been converted to csv1 file successfully");
			logger.info("xls file has been converted to csv1 file successfully");
			wBook.close();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception:", e);
		}
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		File inputFile = new File("C:\\Nagarajan\\ExcelAutDoc\\LoginData.xlsx");
		File outputFile = new File("C:\\Nagarajan\\ExcelAutDoc\\LoginDataNew.csv");	
		xlsx(inputFile, outputFile);
	}

}
