package com.key.common.utils.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 此类采用 poi包 实现了向 .xls 文件写入的功能， 目前为止 FastExcel 还没有提供向 .xls 文件写入的功能
 *
 * @author hellojim
 * @company cxtech
 */
public class POIWriteToExcel {

	private String URL; // 要写入的 .xls 文件的目录
	private String index; // 要写入的 .xls 文件的那个 Sheet 的名字， 默认为第0个(Sheet1)
	private int beginRow = 0; // 要写入的 Sheet 的开始行， 默认为第0行
	private int beginCol = 0; // 要写入的 Sheet 的开始列， 默认为第0列
	private List<String[]> content = new ArrayList<String[]>(); // 要写入到 Sheet
																// 中的内容
	private HSSFWorkbook workbook1 = new HSSFWorkbook();
	/**
	 * URL 要写入的那个 .xls 文件的地址 content 要写入到 Sheet 中的内容
	 */
	public POIWriteToExcel(String URL, List<String[]> content) {
		// 这里简单的做了个判断
		if (URL == null || URL.trim().equals("")) {
			System.out.println("文件不能为空!");
		} else if (URL.trim().indexOf(".xls") == -1
				&& URL.trim().indexOf(".XLS") == -1) {
			System.out.println("文件格式不正确!");
		} else {
			this.URL = URL;
			this.content = content;
		}
	}

	public POIWriteToExcel(String URL) {
		// 这里简单的做了个判断
		if (URL == null || URL.trim().equals("")) {
			System.out.println("文件不能为空!");
		} else if (URL.trim().indexOf(".xls") == -1
				&& URL.trim().indexOf(".XLS") == -1) {
			System.out.println("文件格式不正确!");
		} else {
			this.URL = URL;
			this.workbook1 = workbook1;
		}
	}

	/**
	 * URL 要写入的那个 .xls 文件的地址 content 要写入到 Sheet 中的内容 index 要写入的那个 Sheet ，
	 * 默认为第一个(Sheet1)
	 */
	public POIWriteToExcel(String URL, List<String[]> content, String index) {
		this(URL, content);
		this.index = index;
	}

	/**
	 * URL 要写入的那个 .xls 文件的地址 content 要写入到 Sheet 中的内容 index 要写入的那个 Sheet ，
	 * 默认为第一个(Sheet1) beginRow 要写入的 Sheet 的开始行 beginCol 要写入的 Sheet 的开始列
	 */
	public POIWriteToExcel(String URL, List<String[]> content, String index,
			int beginRow, int beginCol) {
		this(URL, content, index);
		this.beginRow = beginRow;
		this.beginCol = beginCol;
	}

	public int getBeginCol() {
		return beginCol;
	}

	public void setBeginCol(int beginCol) {
		this.beginCol = beginCol;
	}

	public int getBeginRow() {
		return beginRow;
	}

	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public List<String[]> getContent() {
		return content;
	}

	public void setContent(List<String[]> content) {
		this.content = content;
	}

	@SuppressWarnings("deprecation")
	public void write() throws FileNotFoundException, IOException {

		// 创建新的 Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
		HSSFSheet sheet = workbook.createSheet(String.valueOf(index));
		// 创建字体
		HSSFFont font = workbook.createFont();
		// 把字体颜色设置为红色
		font.setColor(HSSFFont.COLOR_NORMAL);
		// 把字体设置为粗体
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		// 创建格式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		// 把创建的字体付加于格式
		cellStyle.setFont(font);

		int beginRow = this.beginRow;
		int beginCol = this.beginCol;

		for (int i = 0; i < content.size(); i++) {
			// 在工作表中创建一行
			HSSFRow row = sheet.createRow(beginRow++);
			beginCol = this.beginCol;
			for (int j = 0; j < content.get(i).length; j++) {
				// 在一行中创建一个表格
				HSSFCell cell = row.createCell((short) beginCol++);

				if (i == 0) {
					// 把上面的格式付加于一个单元格
					cell.setCellStyle(cellStyle);
				}

				// 设置此单元格中存入的是字符串
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 设置编码 这个是用来处理中文问题的
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// 向此单元格中放入值
				cell.setCellValue(content.get(i)[j]);
			}
		}

		FileOutputStream fileOutputStream = null;
		fileOutputStream = new FileOutputStream(this.URL);
		workbook.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();

	}

	@SuppressWarnings("deprecation")
	public void write1(List<String[]> content1, String index1)
			throws FileNotFoundException, IOException {

		// // 创建新的 Excel 工作簿
		// HSSFWorkbook workbook = new HSSFWorkbook();
		// 在 Excel 工作簿中建创一个工作表,其名为缺省值 sheet1
		HSSFSheet sheet = workbook1.createSheet(String.valueOf(index1));
		// 创建字体
		HSSFFont font = workbook1.createFont();
		// 把字体颜色设置为红色
		font.setColor(HSSFFont.COLOR_NORMAL);
		// 把字体设置为粗体
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setBold(true);
		// 创建格式
		HSSFCellStyle cellStyle = workbook1.createCellStyle();
		// 把创建的字体付加于格式
		cellStyle.setFont(font);

		int beginRow = this.beginRow;
		int beginCol = this.beginCol;

		for (int i = 0; i < content1.size(); i++) {
			// 在工作表中创建一行
			HSSFRow row = sheet.createRow(beginRow++);
			beginCol = this.beginCol;
			for (int j = 0; j < content1.get(i).length; j++) {
				// 在一行中创建一个表格
				HSSFCell cell = row.createCell((short) beginCol++);

				if (i == 0) {
					// 把上面的格式付加于一个单元格
					cell.setCellStyle(cellStyle);
				}

				// 设置此单元格中存入的是字符串
//				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellType(CellType.STRING);
				// 设置编码 这个是用来处理中文问题的
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				// 向此单元格中放入值
				cell.setCellValue(content1.get(i)[j]);
			}
		}

		FileOutputStream fileOutputStream = null;
		fileOutputStream = new FileOutputStream(this.URL);
		workbook1.write(fileOutputStream);
		fileOutputStream.flush();
		fileOutputStream.close();

	}
}
