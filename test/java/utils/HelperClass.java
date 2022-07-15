package utils;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

public class HelperClass {
	private Workbook workbook;
	private Sheet sheet;
	private Row oRow;
	public  String readFromExcel(String fPath, String sheetName, int rowNum, int colNum) {
		String cellValue = null;
		try {
			FileInputStream iFile = new FileInputStream(new File(fPath));
			DataFormatter df = new DataFormatter();
			String fileExtension = FilenameUtils.getExtension(fPath);
			
			if(fileExtension.equalsIgnoreCase("xls"))
				workbook = new HSSFWorkbook(iFile);
			else if(fileExtension.equalsIgnoreCase("xlsx"))
				workbook = new XSSFWorkbook(iFile);
			else 
				Assert.fail("FAIL | Valid file extesions are xls or xlsx. However, the extensin of file was " +fileExtension);
			
			sheet = workbook.getSheet(sheetName);
			oRow = sheet.getRow(rowNum-1);
			cellValue = df.formatCellValue(oRow.getCell(colNum-1));
			workbook.close();
		}catch(Exception ex) {
			ex.printStackTrace();
			Assert.fail("FAIL | Check excel file for location, sheetname, row and column correctness.");
		}
		return cellValue;
	}
}
