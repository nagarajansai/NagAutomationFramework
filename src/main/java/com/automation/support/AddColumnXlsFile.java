package com.automation.support;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.Test;

public class AddColumnXlsFile {

	public static Logger logger = Logger.getLogger(AddColumnXlsFile.class);

	String fileLoc = "C:\\Nagarajan\\ExcelAutDoc\\";
	String fileName = "test.xls";
	
	String fileNameNew = "testaddcolumn.xls";
	String sheetName = "login";
	String sheetNameNew = "loginnew";
	String newColumnVal = "code991";

	@Test
	public void executeSheet() throws IOException {
		try
		{
		System.out.println(System.getProperty("user.dir"));
		writeExcel(fileLoc, fileName, sheetName, newColumnVal);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public void writeExcel(String fileLoc, String fileName, String sheetName, String string) {

		try {
			File file = new File(fileLoc + "//" + fileName);
			File fileNew = new File(fileLoc + "//" + fileNameNew);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(fileNew);

			Workbook getWb = new HSSFWorkbook(fis);
			Workbook writeWb = new HSSFWorkbook();
			HSSFSheet sheet = (HSSFSheet) getWb.getSheet(sheetName);
			HSSFSheet newSheet = (HSSFSheet) writeWb.createSheet(sheetNameNew);
			
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

			for (int r = 0; r <= rowCount; r++) {
				Row row = sheet.getRow(r);
				Row setRow = newSheet.createRow(r);
			
				for (int col = 0; col < row.getLastCellNum(); col++) {
					//writeWb.getSheetAt(0).autoSizeColumn(col); dangerous code 
					Cell cell = setRow.createCell(0);
					if (r == 0) {
						cell.setCellValue("MergeCode");
					} else {
						cell.setCellValue(newColumnVal);
					}

					Cell setCell = setRow.createCell(col + 1);
					Cell c=row.getCell(col);
					
					if (c == null) {
						setCell.setCellValue("");
						//System.out.println("testing");
						continue;
					}					

					switch (row.getCell(col).getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN: {
						setCell.setCellValue(row.getCell(col).getBooleanCellValue());
						System.out.print(row.getCell(col).getBooleanCellValue() + "|| ");
						break;
					}
					case Cell.CELL_TYPE_NUMERIC: {
						if (DateUtil.isCellDateFormatted(row.getCell(col))) {
							CellStyle cellStyle = writeWb.createCellStyle();
							CreationHelper createHelper = writeWb.getCreationHelper();
							cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yyyy"));
							setCell.setCellStyle(cellStyle);
							setCell.setCellValue(row.getCell(col).getDateCellValue());
							System.out.print(row.getCell(col).getDateCellValue() + "|| ");
						} else {
							setCell.setCellValue(row.getCell(col).getNumericCellValue());
							System.out.print(row.getCell(col).getNumericCellValue() + "|| ");
						}
						break;
					}
					case Cell.CELL_TYPE_STRING: {
						setCell.setCellValue(row.getCell(col).getStringCellValue());
						System.out.print(row.getCell(col).getStringCellValue() + "|| ");
						break;
					}
					case Cell.CELL_TYPE_ERROR: {
						setCell.setCellValue(row.getCell(col).getErrorCellValue());
						System.out.print(row.getCell(col).getErrorCellValue() + "|| ");
						break;
					}
					case Cell.CELL_TYPE_FORMULA: {
						setCell.setCellValue(row.getCell(col).getCellFormula());
						System.out.print(row.getCell(col).getCellFormula() + "|| ");
						break;
					}
					default:
						setCell.setCellValue("" + ",");
						System.out.println("test");
						break;
					}
				}
			}
			logger.info("test");
			writeWb.write(fos);

			fis.close();
			fos.close();

		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();

		}
	}
}
