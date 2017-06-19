package com.key.common.utils.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

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
			int type = cell.getCellType();
			String msg = getCellStringValue(cell);
			System.out.println(type + ":" + msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0) {
				cellValue = " ";
			}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			// cellValue = String.valueOf(cell.getNumericCellValue());
			DecimalFormat formatter = new DecimalFormat("######");
			cellValue = formatter.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
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
	public static void main(String[] args) {
		reader("F://terchers.xls");
	}
}
