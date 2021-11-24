package net.diaowen.common.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;

public class ReadExcelUtil {
	public static HSSFWorkbook getWorkBook(String filePath){
		POIFSFileSystem fs;
		try {
			fs = new POIFSFileSystem(new FileInputStream(filePath));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		return wb;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, int index) {
		HSSFSheet sheet = wb.getSheetAt(0);
		return sheet;
	}
	public static String getValueByRowCol(HSSFRow sfrow,int col){
		HSSFCell cell = sfrow.getCell((short)col);
		if (cell == null)
			return "";
		String msg = getCellStringValue(cell);
		return msg;
	}
	public static String getValueByCol(HSSFCell sfCell){
		String msg = getCellStringValue(sfCell);
		return msg;
	}

	public static void reader(String filePath) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(3);
			HSSFCell cell = row.getCell((short) 0);
			CellType type = cell.getCellType();
			String msg = getCellStringValue(cell);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
			case STRING:
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0) {
				cellValue = " ";
			}
			break;
			case NUMERIC:
			// cellValue = String.valueOf(cell.getNumericCellValue());
			DecimalFormat formatter = new DecimalFormat("######");
			cellValue = formatter.format(cell.getNumericCellValue());
			break;
			case FORMULA:
			cell.setCellType(CellType.NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
			case BLANK:
			cellValue = " ";
			break;
			case BOOLEAN:
			break;
			case ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}
	public static int getRowSize(HSSFSheet sheet){
		return sheet.getLastRowNum();
	}
	public static int getCellSize(HSSFRow sfRow){
		return sfRow.getLastCellNum();
	}
}
