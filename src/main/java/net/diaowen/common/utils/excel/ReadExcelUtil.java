package net.diaowen.common.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ReadExcelUtil {
	public static Workbook getWorkBook(String filePath){
		POIFSFileSystem fs;
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(filePath);
		} catch (Exception e) {
			try {
				fs = new POIFSFileSystem(new FileInputStream(filePath));
				wb = new HSSFWorkbook(fs);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return wb;
	}


	public static HSSFSheet getHSSFSheet(HSSFWorkbook wb, int index) {
		HSSFSheet sheet = wb.getSheetAt(0);
		return sheet;
	}
	public static String getValueByRowCol(Row sfrow, int col){
		Cell cell = sfrow.getCell((short)col);
		if (cell == null)
			return "";
		String msg = getCellStringValue(cell);
		return msg;
	}
	public static String getValueByCol(Cell sfCell){
		String msg = getCellStringValue(sfCell);
		return msg;
	}

	public static void reader(String filePath) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = sheet.getRow(3);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCellStringValue(Cell cell) {
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
	public static int getRowSize(Sheet sheet){
		return sheet.getLastRowNum();
	}
	public static int getCellSize(HSSFRow sfRow){
		return sfRow.getLastCellNum();
	}
	public static void main(String[] args) {
		reader("F://terchers.xls");
	}
}
